package zltutils.fn;

import java.util.function.Predicate;

@FunctionalInterface
public interface WidePredicate<T> {
	static <T> boolean test(Throwable[] outThrown, WidePredicate<T> p, T t) {
		try {
			return p.test(t);
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
			return false;
		}
	}

	static <T> Predicate<T> narrow(Throwable[] outThrown, WidePredicate<T> p) {
		return t -> test(outThrown, p, t);
	}

	boolean test(T t) throws Throwable;
}
