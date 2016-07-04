/**  
 * @Title: SignUtil.java
 * @Package com.hz.core.util
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午1:58:46
 * @version V1.0  
 */
package com.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {
	// 接口配置token
	/**
	 * 
	 * @description: 服务器地址验证方法（别动！！！！）
	 * @time 2015-7-22
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 * @author Jc-Li
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		PropertiesUtil prop = new PropertiesUtil();
		String token = prop.getProperties("token");
		String[] arr = new String[] { token, timestamp, nonce };
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		content = null;
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte) >>> 4 & 0X0F];
		tempArr[1] = Digit[mByte & 0X0f];
		String s = new String(tempArr);
		return s;
	}
}
