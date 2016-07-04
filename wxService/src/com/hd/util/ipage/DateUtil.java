package com.hd.util.ipage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author weep
 * 
 */
public class DateUtil {

	/** 标准日期格式 **/
	public static final String NORM_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** 文件名格式日期 **/
	public static final String FILE_NAME_FORMAT = "yyyy-MM-dd";
	private DateUtil() {

	}

	/**
	 * @return return String 方法功能:按照格式取当前时间.
	 */
	public static String getDate(Date date, String formatString) {
		SimpleDateFormat format;
		String dateString = null;
		try {
			format = new SimpleDateFormat(formatString);
			dateString = format.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dateString;
	}

	/**
	 * @return return String 方法功能:按照格式取当前时间.
	 */
	public static Date toDate(String dateTime) {
		SimpleDateFormat format;
		Date resultDate = null;
		try {
			format = new SimpleDateFormat(NORM_DATE_FORMAT);
			resultDate = format.parse(dateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultDate;
	}

	public static Date loadDateNowTime() {
		return Calendar.getInstance().getTime();
	}

	public static Long longTime(Date timeDate) {
		return loadDateNowTime().getTime() - timeDate.getTime();
	}

	static class DHMBean {
		public Long dd;
		public Long hh;
		public Long mm;

		private DHMBean() {
		}

		public static DHMBean getInstance(Long longTime) {
			DHMBean dhmBean = new DHMBean();
			dhmBean.dd = longTime / 86400;
			dhmBean.hh = longTime / 3600;
			dhmBean.mm = longTime / 60 % 60;
			return dhmBean;
		}
	}
}
