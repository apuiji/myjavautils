package zltutils;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IterateUtils {
	public static <T> Iterator<T> makeIterator(BooleanSupplier hasNext, Supplier<T> next) {
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return hasNext.getAsBoolean();
			}

			@Override
			public T next() {
				return next.get();
			}
		};
	}

	public static <T> Iterator<T> makeIterator(Supplier<T> next) {
		Object[] value = new Object[1];
		BooleanSupplier hasNext = () -> {
			value[0] = next.get();
			return value[0] != null;
		};
		@SuppressWarnings("unchecked")
		Supplier<T> next1 = () -> (T) value[0];
		return makeIterator(hasNext, next1);
	}

	public static <T> Iterator<T> asIterator(Enumeration<T> e) {
		return makeIterator(e::hasMoreElements, e::nextElement);
	}

	public static <T> Iterable<T> asIterable(Iterator<T> it) {
		return () -> it;
	}

	public static <T> Iterable<T> asIterable(Enumeration<T> e) {
		return asIterable(asIterator(e));
	}

	public static <T> Iterable<T> asIterable(Stream<T> s) {
		return s::iterator;
	}

	public static <T> Stream<T> asStream(Iterator<T> it) {
		Spliterator<T> split = Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED);
		return StreamSupport.stream(split, false);
	}

	public static <T> Stream<T> asStream(Enumeration<T> e) {
		return asStream(asIterator(e));
	}
}
