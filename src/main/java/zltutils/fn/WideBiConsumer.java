package zltutils.fn;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface WideBiConsumer<T, U> {
	static <T, U> void accept(Throwable[] outThrown, WideBiConsumer<T, U> c, T t, U u) {
		try {
			c.accept(t, u);
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
		}
	}

	static <T, U> BiConsumer<T, U> narrow(Throwable[] outThrown, WideBiConsumer<T, U> c) {
		return (t, u) -> accept(outThrown, c, t, u);
	}

	void accept(T t, U u) throws Throwable;
}
