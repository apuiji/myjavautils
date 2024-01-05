package zltutils.fn;

import java.util.function.BiPredicate;

@FunctionalInterface
public interface WideBiPredicate<T, U> {
	static <T, U> WideBiPredicate<T, U> from(WideBiPredicate<T, U> p) {
		return p;
	}

	static <T, U> BiPredicate<T, U> narrow(Throwable[] outThrown, WideBiPredicate<T, U> p) {
		return (t, u) -> p.test(outThrown, t, u);
	}

	boolean test(T t, U u) throws Throwable;

	default boolean test(Throwable[] outThrown, T t, U u) {
		try {
			return test(t, u);
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
			return false;
		}
	}
}
