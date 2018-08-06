package jp.msfblue1.theanonymous.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.Bukkit;

public class ReflectionUtil {

	static {
		try {
			Class.forName("org.bukkit.Bukkit");
			setObject(ReflectionUtil.class, null, "serverVersion", Bukkit.getServer().getClass().getPackage().getName()
					.substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf('.') + 1));
		} catch (Exception e) {
		}
	}


	public static Field getField(Class<?> clazz, String fname) throws Exception {
		Field f;
		try {
			f = clazz.getDeclaredField(fname);
		} catch (Exception e) {
			f = clazz.getField(fname);
		}
		setFieldAccessible(f);
		return f;
	}

	public static Object getObject(Object obj, String fname) throws Exception {
		return getField(obj.getClass(), fname).get(obj);
	}

	public static void setFieldAccessible(Field f) throws Exception {
		f.setAccessible(true);
		Field modifiers = Field.class.getDeclaredField("modifiers");
		modifiers.setAccessible(true);
		modifiers.setInt(f, f.getModifiers() & ~Modifier.FINAL);
	}

	public static void setObject(Class<?> clazz, Object obj, String fname, Object value) throws Exception {
		getField(clazz, fname).set(obj, value);
	}


}
