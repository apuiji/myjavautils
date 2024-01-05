package zltutils.fn;

@FunctionalInterface
public interface WideCloseable {
	static NarrowCloseable narrow(Throwable[] outThrown, WideCloseable c) {
		return () -> c.close(outThrown);
	}

	static NarrowCloseable narrow(WideCloseable c) {
		return c::tryClose;
	}

	void close() throws Throwable;

	default void close(Throwable[] outThrown) {
		try {
			close();
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
		}
	}

	default void tryClose() {
		try {
			close();
		} catch (Throwable thrown) {
			throw new RuntimeException(thrown);
		}
	}
}
