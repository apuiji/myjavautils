package zltutils.refl;

import zltutils.IterateUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

public class ReflectProxyUtils {
	public static <T> T createProxy(ClassLoader cl, InvocationHandler ih, Class<?>... interfaceClasses) {
		@SuppressWarnings("unchecked")
		T t = (T) Proxy.newProxyInstance(cl, interfaceClasses, ih);
		return t;
	}

	public static <T> T createProxy(ClassLoader cl, InvocationHandler ih, Iterator<Class<?>> interfaceClassIt) {
		return createProxy(cl, ih, IterateUtils.asStream(interfaceClassIt));
	}

	public static <T> T createProxy(ClassLoader cl, InvocationHandler ih, Collection<Class<?>> interfaceClasses) {
		return createProxy(cl, ih, interfaceClasses.toArray(new Class[0]));
	}

	public static <T> T createProxy(ClassLoader cl, InvocationHandler ih, Stream<Class<?>> interfaceClasses) {
		return createProxy(cl, ih, interfaceClasses.toArray(Class[]::new));
	}

	public static <T> T createProxy(InvocationHandler ih, Class<?>... interfaceClasses) {
		return createProxy(Thread.currentThread().getContextClassLoader(), ih, interfaceClasses);
	}

	public static <T> T createProxy(InvocationHandler ih, Iterator<Class<?>> interfaceClassIt) {
		return createProxy(ih, IterateUtils.asStream(interfaceClassIt));
	}

	public static <T> T createProxy(InvocationHandler ih, Collection<Class<?>> interfaceClasses) {
		return createProxy(ih, interfaceClasses.toArray(new Class[0]));
	}

	public static <T> T createProxy(InvocationHandler ih, Stream<Class<?>> interfaceClasses) {
		return createProxy(Thread.currentThread().getContextClassLoader(), ih, interfaceClasses.toArray(Class[]::new));
	}
}
