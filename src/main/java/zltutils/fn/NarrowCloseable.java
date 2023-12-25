package zltutils.fn;

@FunctionalInterface
public interface NarrowCloseable extends AutoCloseable {
	void close();
}
