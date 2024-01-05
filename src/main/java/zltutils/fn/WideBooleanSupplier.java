package zltutils.fn;

import java.util.function.BooleanSupplier;

@FunctionalInterface
public interface WideBooleanSupplier {
	static BooleanSupplier narrow(Throwable[] outThrown, WideBooleanSupplier sup) {
		return () -> sup.getAsBoolean(outThrown);
	}

	static BooleanSupplier narrow(WideBooleanSupplier sup) {
		return sup::tryGetAsBoolean;
	}

	static boolean always() {
		return true;
	}

	static boolean never() {
		return false;
	}

	boolean getAsBoolean() throws Throwable;

	default boolean getAsBoolean(Throwable[] outThrown) {
		try {
			return getAsBoolean();
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
			return false;
		}
	}

	default boolean tryGetAsBoolean() {
		try {
			return getAsBoolean();
		} catch (Throwable thrown) {
			throw new RuntimeException(thrown);
		}
	}
}
