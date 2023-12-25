package zltutils.fn;

import java.util.function.Function;

@FunctionalInterface
public interface WideFunction<T, R> {
	static <T, R> R apply(Throwable[] outThrown, WideFunction<T, R> f, T t) {
		try {
			return f.apply(t);
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
			return null;
		}
	}

	static <T, R> Function<T, R> narrow(Throwable[] outThrown, WideFunction<T, R> f) {
		return t -> apply(outThrown, f, t);
	}

	R apply(T t) throws Throwable;
}
