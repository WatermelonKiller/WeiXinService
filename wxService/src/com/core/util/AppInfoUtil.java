package com.core.util;

public class AppInfoUtil {
	private String getPropties(String key) {
		PropertiesUtil prop = new PropertiesUtil();
		return prop.getProperties(key);
	}

	public static String getAppid() {
		AppInfoUtil aif = new AppInfoUtil();
		String value = aif.getPropties("appId");
		aif = null;
		return value;
	}

	public static String getAppsercret() {
		AppInfoUtil aif = new AppInfoUtil();
		String value = aif.getPropties("appSecret");
		aif = null;
		return value;
	}

	public static String getToken() {
		AppInfoUtil aif = new AppInfoUtil();
		String value = aif.getPropties("token");
		aif = null;
		return value;
	}

	public static String getEncodingAESKey() {
		AppInfoUtil aif = new AppInfoUtil();
		String value = aif.getPropties("EncodingAESKey");
		aif = null;
		return value;
	}

	public static String getEncrypt_type() {
		AppInfoUtil aif = new AppInfoUtil();
		String value = aif.getPropties("encrypt_type");
		aif = null;
		return value;
	}
}
