package zltutils.refl;

import zltutils.fn.WideFunction;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ReflectFieldUtils {
	public static Field getField(Class<?> clazz, String name) {
		return WideFunction.apply(null, clazz::getField, name);
	}

	public static Field getDeclaredField(Class<?> clazz, String name) {
		return WideFunction.apply(null, clazz::getDeclaredField, name);
	}

	private static Set<Field> findFields(Supplier<Field[]> getFields, Predicate<Field> filter) {
		return Arrays.stream(getFields.get()).filter(filter).collect(Collectors.toSet());
	}

	public static Set<Field> findFields(Class<?> clazz, Predicate<Field> filter) {
		return findFields(clazz::getFields, filter);
	}

	public static Set<Field> findDeclaredFields(Class<?> clazz, Predicate<Field> filter) {
		return findFields(clazz::getDeclaredFields, filter);
	}
}
