package zltutils.fn;

import java.util.function.Supplier;

@FunctionalInterface
public interface WideSupplier<T> {
	static <T> WideSupplier<T> from(WideSupplier<T> sup) {
		return sup;
	}

	static <T> Supplier<T> narrow(Throwable[] outThrown, WideSupplier<T> sup) {
		return () -> sup.get(outThrown);
	}

	static <T> Supplier<T> narrow(WideSupplier<T> sup) {
		return sup::tryGet;
	}

	static<T> T getNothing() {
		return null;
	}

	T get() throws Throwable;

	default T get(Throwable[] outThrown) {
		try {
			return get();
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
			return null;
		}
	}

	default T tryGet() {
		try {
			return get();
		} catch (Throwable thrown) {
			throw new RuntimeException(thrown);
		}
	}
}
