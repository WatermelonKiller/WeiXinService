package com.hd.util.ipage;


import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hd.util.ipage.Page;
import com.hd.util.ipage.ReflectionUtils;

/**
 * 
 * @date 2012-5-14 下午2:33:39
 */
public class PageUtil {

	private static final Logger log = LoggerFactory.getLogger(PageUtil.class);

	private static final ConcurrentHashMap<String, SimpleDateFormat> dateFormaterCache = new ConcurrentHashMap<String, SimpleDateFormat>();

	private static final ConcurrentHashMap<Class<?>, String> paramClassCache = new ConcurrentHashMap<Class<?>, String>();

	public static final String DEFAULTDATEFMT;

	public static final String DEFAULTNUMFMT;

	public static final String DEFAULTROWSPE = "_r_";
	public static final String DEFAULTCOLSPE = "_c_";

	public static final String DEFAULTPAGECSS = "ipage";

	public static final String DEFAULTSIZEGROUP;

	private static final String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
	private static final String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

	private static final Pattern p_script;
	private static final Pattern p_style;
	private static final Pattern p_html;

	public static int DEFAULTDISPLAY;// 显示页数
	public static int DEFAULTSIZE;
	private static String noDataFound;

	static {
		DEFAULTDATEFMT = getPropertyValue("mybatis/ipage", "default.dateformat");
		DEFAULTNUMFMT = getPropertyValue("mybatis/ipage", "default.numformat");
		DEFAULTSIZEGROUP = getPropertyValue("mybatis/ipage", "default.sizegroup");
		try {
			DEFAULTDISPLAY = Integer.valueOf(getPropertyValue("mybatis/ipage", "default.display"));
		} catch (NumberFormatException e) {
			DEFAULTDISPLAY = 5;
		}
		try {
			DEFAULTSIZE = Integer.valueOf(getPropertyValue("mybatis/ipage", "default.size"));
		} catch (NumberFormatException e) {
			DEFAULTSIZE = 20;
		}
		noDataFound = getPropertyValue("mybatis/ipage", "total.NoDataFound");
		noDataFound = noDataFound.equals("") ? "No Data Found" : noDataFound;
		p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
	}

	public static String getDateString(String format, Date date) {
		SimpleDateFormat sdf = dateFormaterCache.get(format);
		if (sdf == null) {
			sdf = new SimpleDateFormat(format);
			dateFormaterCache.put(format, sdf);
		}
		return sdf.format(date);
	}

	public static String Html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		try {
			htmlStr = p_script.matcher(htmlStr).replaceAll(""); // 过滤script标签
			htmlStr = p_style.matcher(htmlStr).replaceAll(""); // 过滤style标签
			htmlStr = p_html.matcher(htmlStr).replaceAll(""); // 过滤html标签
		} catch (Exception e) {
			log.error("Html2Text: ", e);
		}
		return htmlStr;// 返回文本字符串
	}

	public static String getFieldNameImplPage(Class<?> clazz) {
		String fieldname = paramClassCache.get(clazz);
		if (fieldname == null) {
			fieldname = ReflectionUtils.getFieldNameByType(clazz, Page.class);
			if (fieldname != null)
				paramClassCache.put(clazz, fieldname);
		}
		return fieldname;
	}

	/**
	 * 
	 * @param baseName
	 *            文件名（不带扩展名）
	 * @param key
	 * @param values
	 *            替换变量用的值
	 * @return
	 * @date 2011-9-26 下午03:24:37
	 */
	public static String getPropertyValue(String baseName, String key, Object... values) {
		if (baseName == null || baseName.equals("") || key == null || key.equals(""))
			return "";
		ResourceBundle bundle = PropertyResourceBundle.getBundle(baseName);
		if (bundle.containsKey(key))
			return (values != null && values.length > 0) ? MessageFormat.format(bundle.getString(key), values) : bundle
					.getString(key);
		else
			return "";
	}

	public static String getNodatafound() {
		return noDataFound;
	}

}
