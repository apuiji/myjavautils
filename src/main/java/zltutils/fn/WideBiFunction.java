package zltutils.fn;

import java.util.function.BiFunction;

@FunctionalInterface
public interface WideBiFunction<T, U, R> {
	static <T, U, R> WideBiFunction<T, U, R> from(WideBiFunction<T, U, R> f) {
		return f;
	}

	static <T, U, R> BiFunction<T, U, R> narrow(Throwable[] outThrown, WideBiFunction<T, U, R> f) {
		return (t, u) -> f.apply(outThrown, t, u);
	}

	R apply(T t, U u) throws Throwable;

	default R apply(Throwable[] outThrown, T t, U u) {
		try {
			return apply(t, u);
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
			return null;
		}
	}
}
