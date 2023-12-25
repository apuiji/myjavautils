package zltutils;

import java.nio.charset.Charset;
import java.util.Arrays;

public class Xyz {
	public static final byte[] BOM_UTF8 = makeBytes(0xEF, 0xBB, 0xBF);
	public static final byte[] BOM_UTF16_LE = makeBytes(0xFF, 0xFE);
	public static final byte[] BOM_UTF16_BE = makeBytes(0xFE, 0xFF);

	public static boolean always() {
		return true;
	}

	public static boolean never() {
		return false;
	}

	public static Object getNothing() {
		return null;
	}

	public static void doNothing() {
	}

	public static byte[] makeBytes(int... ints) {
		byte[] bytes = new byte[ints.length];
		for (int i = 0; i < ints.length; ++i) {
			bytes[i] = (byte) ints[i];
		}
		return bytes;
	}

	public static int hexDigitChar(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		}
		if (c >= 'A' && c <= 'F') {
			return c - 'A';
		}
		if (c >= 'a' && c <= 'f') {
			return c - 'a';
		}
		return -1;
	}

	/**
	 * @return string that {@param bytes} converted with {@param charset}, null if the convert is unable
	 */
	public static String isConvertibleWithCharset(byte[] bytes, Charset charset) {
		String s = new String(bytes, charset);
		byte[] bytes1 = s.getBytes(charset);
		return Arrays.equals(bytes, bytes1) ? s : null;
	}
}
