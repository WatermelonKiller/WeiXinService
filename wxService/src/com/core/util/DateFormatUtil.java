/**  
 * @Title: DateUtil.java
 * @Package com.hz.core.util
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午4:40:25
 * @version V1.0  
 */
package com.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
	/**
	 * 
	 * @description: 将服务器传回long时间参数（String）转为yyyy-MM-dd HH:mm:ss格式
	 * @time 2015-7-22
	 * @param createTime
	 * @return
	 * @author Jc-Li
	 */
	public static final String formatTimeFromWx(String createTime) {
		long msgCreateTime = Long.parseLong(createTime) * 1000L;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(msgCreateTime));
	}
}
