package com.hd.util.ipage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 鍙嶅皠宸ュ叿绫�
 * 
 * 鎻愪緵璁块棶绉佹湁鍙�?��,鑾峰彇娉涘�?绫诲瀷Class, 鎻愬彇闆嗗悎涓厓绱犵殑灞炴�? 杞崲�?楃涓插埌�?硅薄绛塙til鍑芥�?

 */
public class ReflectionUtils {

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

	private ReflectionUtils() {

	}

	static {
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
		ConvertUtils.register(dc, Date.class);
	}

	/**
	 * 璋冪敤Getter鏂规�?
	 */
	public static Object invokeGetterMethod(Object obj, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(obj, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 璋冪敤Setter鏂规�?浣跨敤value鐨凜lass鏉ユ煡鎵維etter鏂规�?
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
		invokeSetterMethod(obj, propertyName, value, null);
	}

	/**
	 * 璋冪敤Setter鏂规�?
	 * 
	 * @param propertyType
	 *            鐢ㄤ簬鏌ユ壘Setter鏂规�?涓虹┖鏃朵娇鐢╲alue鐨凜lass鏇夸�?
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(obj, setterMethodName, new Class[] { type }, new Object[] { value });
	}

	/**
	 * 鐩存帴璇诲彇瀵硅薄灞炴�鍊� 鏃犺private/protected淇グ绗�?涓嶇粡杩噂etter鍑芥�?
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("涓嶅彲鑳芥姏鍑虹殑寮傚父{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 鐩存帴璁剧疆瀵硅薄灞炴�鍊� 鏃犺private/protected淇グ绗�?涓嶇粡杩噑etter鍑芥�?
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			logger.error("涓嶅彲鑳芥姏鍑虹殑寮傚父:{}", e.getMessage());
		}
	}

	/**
	 * 寰幆鍚戜笂杞�? 鑾峰彇�?硅薄鐨凞eclaredField, 骞跺己鍒惰缃负鍙闂�
	 * 
	 * 濡傚悜涓婅浆鍨嬪埌Object浠嶆棤娉曟壘鍒� 杩斿洖null.
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		Assert.notNull(obj, "object涓嶈兘涓虹┖");
		Assert.hasText(fieldName, "fieldName");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {// NOSONAR
				// Field涓嶅湪褰撳墠绫诲畾涔�缁х画鍚戜笂杞�?
			}
		}
		return null;
	}

	/**
	 * 鐩存帴璋冪敤瀵硅薄鏂规硶, 鏃犺private/protected淇グ绗�?鐢ㄤ簬涓�鎬ц皟鐢ㄧ殑鎯呭喌.
	 */
	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 寰幆鍚戜笂杞�? 鑾峰彇�?硅薄鐨凞eclaredMethod,骞跺己鍒惰缃负鍙闂� 濡傚悜涓婅浆鍨嬪埌Object浠嶆棤娉曟壘鍒� 杩斿洖null.
	 * 
	 * 鐢ㄤ簬鏂规硶闇�琚娆¤皟鐢ㄧ殑鎯呭喌. 鍏堜娇鐢ㄦ湰鍑芥暟鍏堝彇寰桵ethod,鐒跺悗璋冪敤Method.invoke(Object obj, Object... args)
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {
		Assert.notNull(obj, "object涓嶈兘涓虹┖");

		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

				method.setAccessible(true);

				return method;

			} catch (NoSuchMethodException e) {// NOSONAR
				// Method涓嶅湪褰撳墠绫诲畾涔�缁х画鍚戜笂杞�?
			}
		}
		return null;
	}

	/**
	 * 閫氳繃鍙嶅皠, 鑾峰緱Class瀹氫箟涓０鏄庣殑鐖剁被鐨勬硾鍨嬪弬鏁扮殑绫诲�?. 濡傛棤娉曟壘鍒� 杩斿洖Object.class. eg. public UserDao extends HibernateDao<User>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 閫氳繃鍙嶅皠, 鑾峰緱Class瀹氫箟涓０鏄庣殑鐖剁被鐨勬硾鍨嬪弬鏁扮殑绫诲�?. 濡傛棤娉曟壘鍒� 杩斿洖Object.class.
	 * 
	 * 濡俻ublic UserDao extends HibernateDao<User,Long>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * 鎻愬彇闆嗗悎涓殑�?硅薄鐨勫睘鎬�閫氳繃getter鍑芥�?, 缁勫悎鎴怢ist.
	 * 
	 * @param collection
	 *            鏉ユ簮闆嗗悎.
	 * @param propertyName
	 *            瑕佹彁鍙栫殑灞炴�鍚�?
	 */
	@SuppressWarnings("unchecked")
	public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
		List list = new ArrayList();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 鎻愬彇闆嗗悎涓殑�?硅薄鐨勫睘鎬�閫氳繃getter鍑芥�?, 缁勫悎鎴愮敱鍒嗗壊绗�?��闅旂殑�?楃涓�
	 * 
	 * @param collection
	 *            鏉ユ簮闆嗗悎.
	 * @param propertyName
	 *            瑕佹彁鍙栫殑灞炴�鍚�?
	 * @param separator
	 *            鍒嗛殧绗�?
	 */
	@SuppressWarnings("unchecked")
	public static String convertElementPropertyToString(final Collection collection, final String propertyName,
			final String separator) {
		List list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 杞崲�?楃涓插埌鐩稿簲绫诲�?
	 * 
	 * @param value
	 *            寰呰浆鎹㈢殑瀛楃涓�?	 * @param toType
	 *            杞崲鐩爣绫诲�?
	 */
	public static Object convertStringToObject(String value, Class<?> toType) {
		try {
			return ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 灏嗗弽灏勬椂鐨刢hecked exception杞崲涓簎nchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}

	/**
	 * 鏍规嵁浼犲叆classType鎵惧埌闆嗗悎涓绫荤殑灞炴�鍚�?	 * 
	 * @param superClass
	 * @param type
	 * @return
	 */
	public static String getFieldNameByType(Class<?> superClass, Class<?> type) {
		if (superClass == Object.class)
			return null;
		Field[] fields = superClass.getDeclaredFields();
		for (Field field : fields) {
			if (isTypeImplType(field.getType(), type))
				return field.getName();
		}
		return getFieldNameByType(superClass.getSuperclass(), type);
	}

	/**
	 * 浼犲叆闆嗗悎涓腑鏄惁鏈夊尮閰嶇殑绫诲瀷灞炴�
	 * 
	 * @param clazz
	 * @param type
	 * @return
	 */
	public static boolean isTypeImplType(Class<?> clazz, Class<?> type) {
		Class<?>[] types = clazz.getInterfaces();
		for (Class<?> item : types) {
			if (item == type || isTypeImplType(item, type))
				return true;
		}
		return false;
	}
}
