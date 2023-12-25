package zltutils;

import lombok.AllArgsConstructor;

import java.util.Comparator;

@AllArgsConstructor
public enum Between {
	/**
	 * [min, max]
	 */
	CC(true, true),
	/**
	 * [min, max)
	 */
	CO(true, false),
	/**
	 * (min, max]
	 */
	OC(false, true),
	/**
	 * (min, max)
	 */
	OO(false, false);

	private final boolean gteq;
	private final boolean lteq;

	private boolean test(long diffMin, long diffMax) {
		boolean b = gteq ? diffMin >= 0 : diffMin > 0;
		boolean c = lteq ? diffMax <= 0 : diffMax < 0;
		return b && c;
	}

	private boolean test(double diffMin, double diffMax) {
		boolean b = gteq ? diffMin >= 0 : diffMin > 0;
		boolean c = lteq ? diffMax <= 0 : diffMax < 0;
		return b && c;
	}

	public <T> boolean test(T value, T min, T max, Comparator<T> cmp) {
		return test(cmp.compare(value, min), cmp.compare(value, max));
	}

	public <T extends Comparable<T>> boolean test(T value, T min, T max) {
		return test(value, min, max, Comparable::compareTo);
	}

	public boolean test(long value, long min, long max) {
		return test(min - value, max - value);
	}

	public boolean test(double value, double min, double max) {
		return test(min - value, max - value);
	}
}
