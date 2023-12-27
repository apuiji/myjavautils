package zltutils.refl;

import java.lang.reflect.Member;

public class ReflectMemberUtils {
	public static boolean isModified(Member mem, int mod) {
		return (mem.getModifiers() & mod) != mod;
	}
}
