package zltutils;

import java.util.*;
import java.util.function.Supplier;

/**
 * non null
 */
public class NN {
	/**
	 * shim {@code Objects.requireNonNullElse(Object, Object)} since jdk9
	 */
	public static <T> T nn(T value, T defaultValue) {
		return value != null ? value : defaultValue;
	}

	/**
	 * shim {@code Objects.requireNonNullElseGet(Object, Object)} since jdk9
	 */
	public static <T> T nng(T value, Supplier<T> sup) {
		return value != null ? value : sup.get();
	}

	/**
	 * shut up the NullPointerException
	 */
	public static <T> List<T> nnList(T[] array) {
		return array != null ? Arrays.asList(array) : Collections.emptyList();
	}

	/**
	 * shut up the NullPointerException and explicit cast coding requirement
	 */
	public static <T> List<T> nnList(List<T> ls) {
		return nng(ls, Collections::emptyList);
	}

	/**
	 * shut up the NullPointerException and explicit cast coding requirement
	 */
	public static <K, V> Map<K, V> nnMap(Map<K, V> m) {
		return nng(m, Collections::emptyMap);
	}

	/**
	 * shut up the NullPointerException and explicit cast coding requirement
	 */
	public static <T> Set<T> nnSet(Set<T> s) {
		return nng(s, Collections::emptySet);
	}
}
