package zltutils.fn;

import java.util.function.Predicate;

@FunctionalInterface
public interface WidePredicate<T> {
	static <T> WidePredicate<T> from(WidePredicate<T> p) {
		return p;
	}

	static <T> Predicate<T> narrow(Throwable[] outThrown, WidePredicate<T> p) {
		return t -> p.test(outThrown, t);
	}

	static <T> Predicate<T> narrow(WidePredicate<T> p) {
		return p::tryTest;
	}

	boolean test(T t) throws Throwable;

	default boolean test(Throwable[] outThrown, T t) {
		try {
			return test(t);
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
			return false;
		}
	}

	default boolean tryTest(T t) {
		try {
			return test(t);
		} catch (RuntimeException re) {
			throw re;
		} catch (Throwable thrown) {
			throw new RuntimeException(thrown);
		}
	}
}
