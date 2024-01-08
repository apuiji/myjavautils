package zltutils.fn;

import java.util.function.Function;

@FunctionalInterface
public interface WideFunction<T, R> {
	static <T, R> WideFunction<T, R> from(WideFunction<T, R> f) {
		return f;
	}

	static <T, R> Function<T, R> narrow(Throwable[] outThrown, WideFunction<T, R> f) {
		return t -> f.apply(outThrown, t);
	}

	R apply(T t) throws Throwable;

	default R apply(Throwable[] outThrown, T t) {
		try {
			return apply(t);
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
			return null;
		}
	}

	default R tryApply(T t) {
		try {
			return apply(t);
		} catch (RuntimeException re) {
			throw re;
		} catch (Throwable thrown) {
			throw new RuntimeException(thrown);
		}
	}
}
