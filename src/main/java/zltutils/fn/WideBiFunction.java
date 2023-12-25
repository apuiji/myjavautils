package zltutils.fn;

import java.util.function.BiFunction;

@FunctionalInterface
public interface WideBiFunction<T, U, R> {
	static <T, U, R> R apply(Throwable[] outThrown, WideBiFunction<T, U, R> f, T t, U u) {
		try {
			return f.apply(t, u);
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
			return null;
		}
	}

	static <T, U, R> BiFunction<T, U, R> narrow(Throwable[] outThrown, WideBiFunction<T, U, R> f) {
		return (t, u) -> apply(outThrown, f, t, u);
	}

	R apply(T t, U u) throws Throwable;
}
