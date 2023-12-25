package zltutils.fn;

@FunctionalInterface
public interface WideRunnable {
	static <T> void run(Throwable[] outThrown, WideRunnable r) {
		try {
			r.run();
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
		}
	}

	static <T> Runnable narrow(Throwable[] outThrown, WideRunnable r) {
		return () -> run(outThrown, r);
	}

	void run() throws Throwable;
}
