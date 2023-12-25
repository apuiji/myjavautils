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
	 * reduce if null check coding before foreach
	 *
	 * @return nonnull but unmodified list
	 */
	public static <T> List<T> nnList(T[] array) {
		return array != null ? Arrays.asList(array) : Collections.emptyList();
	}

	/**
	 * reduce if null check coding before foreach
	 *
	 * @return nonnull but unmodified map
	 */
	public static <K, V> Map<K, V> nnMap(Map<K, V> map) {
		return map != null ? map : Collections.emptyMap();
	}

	/**
	 * reduce if null check coding before foreach
	 *
	 * @return nonnull but unmodified set
	 */
	public static <T> Set<T> nnSet(Set<T> set) {
		return set != null ? set : Collections.emptySet();
	}
}
