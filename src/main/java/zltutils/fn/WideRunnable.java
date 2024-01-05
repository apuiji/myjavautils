package zltutils.fn;

@FunctionalInterface
public interface WideRunnable {
	static <T> Runnable narrow(Throwable[] outThrown, WideRunnable r) {
		return () -> r.run(outThrown);
	}

	static void doNothing() {
	}

	void run() throws Throwable;

	default void run(Throwable[] outThrown) {
		try {
			run();
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
		}
	}
}
