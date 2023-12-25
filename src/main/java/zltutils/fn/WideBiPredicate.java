package zltutils.fn;

import java.util.function.BiPredicate;

@FunctionalInterface
public interface WideBiPredicate<T, U> {
	static <T, U> boolean test(Throwable[] outThrown, WideBiPredicate<T, U> p, T t, U u) {
		try {
			return p.test(t, u);
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
			return false;
		}
	}

	static <T, U> BiPredicate<T, U> narrow(Throwable[] outThrown, WideBiPredicate<T, U> p) {
		return (t, u) -> test(outThrown, p, t, u);
	}

	boolean test(T t, U u) throws Throwable;
}
