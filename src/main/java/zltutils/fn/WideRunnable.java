package zltutils.fn;

@FunctionalInterface
public interface WideRunnable {
	static Runnable narrow(Throwable[] outThrown, WideRunnable r) {
		return () -> r.run(outThrown);
	}

	static Runnable narrow(WideRunnable r) {
		return r::tryRun;
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

	default void tryRun() {
		try {
			run();
		} catch (RuntimeException re) {
			throw re;
		} catch (Throwable thrown) {
			throw new RuntimeException(thrown);
		}
	}
}
