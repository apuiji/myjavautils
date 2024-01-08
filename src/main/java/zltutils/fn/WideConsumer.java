package zltutils.fn;

import java.util.function.Consumer;

@FunctionalInterface
public interface WideConsumer<T> {
	static <T> WideConsumer<T> from(WideConsumer<T> c) {
		return c;
	}

	static <T> Consumer<T> narrow(Throwable[] outThrown, WideConsumer<T> c) {
		return t -> c.accept(outThrown, t);
	}

	static <T> Consumer<T> narrow(WideConsumer<T> c) {
		return c::tryAccept;
	}

	void accept(T t) throws Throwable;

	default void accept(Throwable[] outThrown, T t) {
		try {
			accept(t);
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
		}
	}

	default void tryAccept(T t) {
		try {
			accept(t);
		} catch (RuntimeException re) {
			throw re;
		} catch (Throwable thrown) {
			throw new RuntimeException(thrown);
		}
	}
}
