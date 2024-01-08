package zltutils;

import java.util.Arrays;
import java.util.Collections;
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
	public static <T> Iterable<T> nnIterable(T[] array) {
		return array != null ? Arrays.asList(array) : Collections::emptyIterator;
	}
}
