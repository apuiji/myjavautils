package zltutils.fn;

@FunctionalInterface
public interface WideCloseable {
	static NarrowCloseable narrow(Throwable[] outThrown, WideCloseable c) {
		return () -> c.close(outThrown);
	}

	static NarrowCloseable narrow(Throwable[] outThrown, AutoCloseable c, int i) {
		return narrow(outThrown, c::close);
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
}
