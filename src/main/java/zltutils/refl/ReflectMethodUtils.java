package zltutils.refl;

import zltutils.IterateUtils;
import zltutils.fn.WideBiFunction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectMethodUtils {
	/**
	 * shut up the {@link NoSuchMethodException}
	 *
	 * @param getMethod example {@code getMethod(clazz::getDeclaredMethod, "methodName", int.class)}
	 */
	public static Method getMethod(WideBiFunction<String, Class<?>[], Method> getMethod, String name, Class<?>... paramTypes) {
		return WideBiFunction.apply(null, getMethod, name, paramTypes);
	}

	public static Method getMethod(
			WideBiFunction<String, Class<?>[], Method> getMethod, String name, Iterator<Class<?>> paramTypeIt) {
		return getMethod(getMethod, name, IterateUtils.asStream(paramTypeIt));
	}

	public static Method getMethod(
			WideBiFunction<String, Class<?>[], Method> getMethod, String name, Collection<Class<?>> paramTypes) {
		return getMethod(getMethod, name, paramTypes.toArray(new Class[0]));
	}

	public static Method getMethod(
			WideBiFunction<String, Class<?>[], Method> getMethod, String name, Stream<Class<?>> paramTypes) {
		return getMethod(getMethod, name, paramTypes.toArray(Class[]::new));
	}

	/**
	 * shut up the {@link NoSuchMethodException}
	 *
	 * @param getMethods example {@code findMethods(clazz::getDeclaredMethods, () -> true)}
	 */
	public static Set<Method> findMethods(Supplier<Method[]> getMethods, Predicate<Method> filter) {
		return Arrays.stream(getMethods.get()).filter(filter).collect(Collectors.toSet());
	}

	public static Set<Method> findMethods(Supplier<Method[]> getMethods, String name) {
		return findMethods(getMethods, m -> m.getName().equals(name));
	}

	public static Set<Method> findMethods(Supplier<Method[]> getMethods, String name, int paramCount) {
		return findMethods(getMethods, m -> m.getName().equals(name) && m.getParameterCount() == paramCount);
	}

	public static <T> T invoke(Throwable[] outThrown, Method method, Object thiz, Object[] args) {
		try {
			@SuppressWarnings("unchecked")
			T t = (T) method.invoke(thiz, args);
			return t;
		} catch (InvocationTargetException e) {
			if (outThrown != null) {
				outThrown[0] = e.getTargetException();
			}
			return null;
		} catch (IllegalAccessException e) {
			if (outThrown != null) {
				outThrown[0] = e;
			}
			return null;
		}
	}

	/**
	 * @param i unused, just prevent conflicted method definition
	 */
	public static <T> T invoke(Throwable[] outThrown, Method method, Object thiz, int i, Object... args) {
		return invoke(outThrown, method, thiz, args);
	}

	public static <T> T invoke(Throwable[] outThrown, Method method, Object thiz, Supplier<Object[]> args) {
		return invoke(outThrown, method, thiz, args.get());
	}

	public static <T> T invoke(Throwable[] outThrown, Method method, Object thiz, Iterator<?> argIt) {
		return invoke(outThrown, method, thiz, IterateUtils.asStream(argIt).toArray());
	}
}
