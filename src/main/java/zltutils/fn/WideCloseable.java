package zltutils.fn;

@FunctionalInterface
public interface WideCloseable {
	static void close(Throwable[] outThrown, WideCloseable c) {
		try {
			c.close();
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
		}
	}

	static void close(Throwable[] outThrown, AutoCloseable c, int i) {
		close(outThrown, c::close);
	}

	static NarrowCloseable narrow(Throwable[] outThrown, WideCloseable c) {
		return () -> close(outThrown, c);
	}

	static NarrowCloseable narrow(Throwable[] outThrown, AutoCloseable c, int i) {
		return () -> close(outThrown, c, 0);
	}

	void close() throws Throwable;
}
