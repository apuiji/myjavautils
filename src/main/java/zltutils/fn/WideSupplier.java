package zltutils.fn;

import java.util.function.Supplier;

@FunctionalInterface
public interface WideSupplier<T> {
	static <T> T get(Throwable[] outThrown, WideSupplier<T> sup) {
		try {
			return sup.get();
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
			return null;
		}
	}

	static <T> Supplier<T> narrow(Throwable[] outThrown, WideSupplier<T> sup) {
		return () -> get(outThrown, sup);
	}

	T get() throws Throwable;
}
