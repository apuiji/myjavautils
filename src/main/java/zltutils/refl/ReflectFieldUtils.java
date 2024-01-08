package zltutils.refl;

import zltutils.fn.WideFunction;

import java.lang.reflect.Field;

public class ReflectFieldUtils {
	/**
	 * shut up the NoSuchFieldException
	 *
	 * @param getField lambda clazz::getField, clazz::getDeclaredField
	 */
	public static Field getField(WideFunction<String, Field> getField, String name) {
		return getField.apply(null, name);
	}
}
