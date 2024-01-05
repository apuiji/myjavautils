package zltutils.fn;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface WideBiConsumer<T, U> {
	static <T, U> WideBiConsumer<T, U> from(WideBiConsumer<T, U> c) {
		return c;
	}

	static <T, U> BiConsumer<T, U> narrow(Throwable[] outThrown, WideBiConsumer<T, U> c) {
		return (t, u) -> c.accept(outThrown, t, u);
	}

	static <T, U> BiConsumer<T, U> narrow(WideBiConsumer<T, U> c) {
		return c::tryAccept;
	}

	void accept(T t, U u) throws Throwable;

	default void accept(Throwable[] outThrown, T t, U u) {
		try {
			accept(t, u);
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
		}
	}

	default void tryAccept(T t, U u) {
		try {
			accept(t, u);
		} catch (Throwable thrown) {
			throw new RuntimeException(thrown);
		}
	}
}
