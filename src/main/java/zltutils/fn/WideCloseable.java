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

	static NarrowCloseable narrow(Throwable[] outThrown, WideCloseable c) {
		return () -> close(outThrown, c);
	}

	void close() throws Throwable;
}
