/**  
 * @Title: ByteSizeUtil.java
 * @Package com.hz.core.util
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午4:26:41
 * @version V1.0  
 */
package com.core.util;

public class ByteSizeUtil {
	/**
	 * 
	 * @description: 获取字符串UTF-8下字节数
	 * @time 2015-7-22
	 * @param content
	 * @return
	 * @author Jc-Li
	 */
	public static int getByteSize(String content) {
		int size = 0;
		if (null != content) {
			try {
				size = content.getBytes("utf-8").length;
			} catch (Exception e) {
				return -1;
			}
		}
		return size;
	}
}
