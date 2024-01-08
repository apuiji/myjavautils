package zltutils.refl;

import zltutils.IterateUtils;
import zltutils.fn.WideBiFunction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ReflectMethodUtils {
	/**
	 * shut up the NoSuchMethodException
	 *
	 * @param getMethod lambda clazz::getMethod, clazz::getDeclaredMethod
	 */
	public static Method getMethod(WideBiFunction<String, Class<?>[], Method> getMethod, String name, Class<?>... paramTypes) {
		return getMethod.apply(null, name, paramTypes);
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
	public static <T> T invoke(Throwable[] outThrown, Method method, Object thiz, int i, Object... args) {
		return invoke(outThrown, method, thiz, args);
	}

	public static <T> T invoke(Throwable[] outThrown, Method method, Object thiz, Iterator<?> argIt) {
		return invoke(outThrown, method, thiz, IterateUtils.asStream(argIt).toArray());
	}

	public static <T> T tryInvoke(Method method, Object thiz, Object[] args) {
		try {
			@SuppressWarnings("unchecked")
			T t = (T) method.invoke(thiz, args);
			return t;
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t instanceof RuntimeException) {
				throw (RuntimeException) t;
			} else {
				throw new RuntimeException(t);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T tryInvoke(Method method, Object thiz, int i, Object... args) {
		return tryInvoke(method, thiz, args);
	}

	public static <T> T tryInvoke(Method method, Object thiz, Iterator<?> argIt) {
		return tryInvoke(method, thiz, IterateUtils.asStream(argIt).toArray());
	}
}
