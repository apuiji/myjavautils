package zltutils.refl;

import zltutils.IterateUtils;
import zltutils.fn.WideFunction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectConstructorUtils {
	/**
	 * shut up the {@link NoSuchMethodException}
	 *
	 * @param getCons example {@code getConstructor(clazz::getDeclaredConstructor, int.class)}
	 */
	public static <T> Constructor<T> getConstructor(WideFunction<Class<?>[], Constructor<T>> getCons, Class<?>... paramTypes) {
		return getCons.apply(null, paramTypes);
	}

	public static <T> Constructor<T> getConstructor(
			WideFunction<Class<?>[], Constructor<T>> getConstructor, Iterator<Class<?>> paramTypeIt) {
		return getConstructor(getConstructor, IterateUtils.asStream(paramTypeIt));
	}

	public static <T> Constructor<T> getConstructor(
			WideFunction<Class<?>[], Constructor<T>> getCons, Collection<Class<?>> paramTypes) {
		return getConstructor(getCons, paramTypes.toArray(new Class[0]));
	}

	public static <T> Constructor<T> getConstructor(
			WideFunction<Class<?>[], Constructor<T>> getCons, Stream<Class<?>> paramTypes) {
		return getConstructor(getCons, paramTypes.toArray(Class[]::new));
	}

	/**
	 * shut up the {@link NoSuchMethodException}
	 *
	 * @param getConstructors example {@code findConstructors(clazz::getDeclaredConstructors, () -> true)}
	 */
	public static <T> Set<Constructor<T>> findConstructors(
			Supplier<Constructor<T>[]> getConstructors, Predicate<Constructor<T>> predicate) {
		return Arrays.stream(getConstructors.get()).filter(predicate).collect(Collectors.toSet());
	}

	public static <T> Set<Constructor<T>> findConstructors(Supplier<Constructor<T>[]> getConstructors, int paramCount) {
		return findConstructors(getConstructors, c -> c.getParameterCount() == paramCount);
	}

	public static <T> T neo(Throwable[] outThrown, Constructor<T> cons, Object[] args) {
		try {
			return cons.newInstance(args);
		} catch (InvocationTargetException e) {
			if (outThrown != null) {
				outThrown[0] = e.getTargetException();
			}
			return null;
		} catch (InstantiationException | IllegalAccessException e) {
			if (outThrown != null) {
				outThrown[0] = e;
			}
			return null;
		}
	}

	/**
	 * @param i unused, just prevent conflicted method definition
	 */
	public static <T> T neo(Throwable[] outThrown, Constructor<T> cons, int i, Object... args) {
		return neo(outThrown, cons, args);
	}

	public static <T> T neo(Throwable[] outThrown, Constructor<T> cons, Supplier<Object[]> args) {
		return neo(outThrown, cons, args.get());
	}

	public static <T> T neo(Throwable[] outThrown, Constructor<T> cons, Iterator<?> argIt) {
		return neo(outThrown, cons, IterateUtils.asStream(argIt).toArray());
	}
}
