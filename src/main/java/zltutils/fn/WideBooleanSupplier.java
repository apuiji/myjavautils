package zltutils.fn;

import java.util.function.BooleanSupplier;

@FunctionalInterface
public interface WideBooleanSupplier {
	static boolean getAsBoolean(Throwable[] outThrown, WideBooleanSupplier sup) {
		try {
			return sup.getAsBoolean();
		} catch (Throwable thrown) {
			if (outThrown != null) {
				outThrown[0] = thrown;
			}
			return false;
		}
	}

	static BooleanSupplier narrow(Throwable[] outThrown, WideBooleanSupplier sup) {
		return () -> getAsBoolean(outThrown, sup);
	}

	boolean getAsBoolean() throws Throwable;
}
