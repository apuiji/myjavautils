package zltutils.fn;

import java.util.function.Consumer;

@FunctionalInterface
public interface WideConsumer<T> {
	static <T> void accept(Throwable[] outThrown, WideConsumer<T> c, T t) {
		try {
			c.accept(t);
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
		}
	}

	static <T> Consumer<T> narrow(Throwable[] outThrown, WideConsumer<T> c) {
		return t -> accept(outThrown, c, t);
	}

	void accept(T t) throws Throwable;
}
