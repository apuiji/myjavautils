package zltutils.refl;

import zltutils.IterateUtils;
import zltutils.fn.WideFunction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ReflectConstructorUtils {
	/**
	 * shut up the NoSuchMethodException
	 *
	 * @param getCons lambda clazz::getConstructor, clazz::getDeclaredConstructor
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
	 * shut up the NoSuchMethodException
	 *
	 * @param getConstructors lambda clazz::getConstructors, clazz::getDeclaredConstructors
	 */
	public static <T> void findConstructors(
			Collection<Constructor<T>> dest, Supplier<Constructor<T>[]> getConstructors, Predicate<Constructor<T>> predicate) {
		Arrays.stream(getConstructors.get()).filter(predicate).forEach(dest::add);
	}

	public static <T> void findConstructors(
			Collection<Constructor<T>> dest, Supplier<Constructor<T>[]> getConstructors, int paramCount) {
		findConstructors(dest, getConstructors, c -> c.getParameterCount() == paramCount);
	}

	public static <T> T neo(Throwable[] outThrown, Constructor<T> cons, Object[] args) {
		try {
			return cons.newInstance(args);
		} catch (InvocationTargetException e) {
			if (outThrown != null) {
				outThrown[0] = e.getTargetException();
			}
			return null;
		} catch (Exception e) {
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

	public static <T> T neo(Throwable[] outThrown, Constructor<T> cons, Iterator<?> argIt) {
		return neo(outThrown, cons, IterateUtils.asStream(argIt).toArray());
	}

	public static <T> T tryNeo(Constructor<T> cons, Object[] args) {
		try {
			return cons.newInstance(args);
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t instanceof RuntimeException) {
				throw (RuntimeException) t;
			} else {
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param i unused, just prevent conflicted method definition
	 */
	public static <T> T tryNeo(Constructor<T> cons, int i, Object... args) {
		return tryNeo(cons, args);
	}

	public static <T> T tryNeo(Constructor<T> cons, Iterator<?> argIt) {
		return tryNeo(cons, IterateUtils.asStream(argIt).toArray());
	}
}
