package com.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public String getProperties(String PropertiesName) {
		String value = "";
		Properties pts = new Properties();
		InputStream in = PropertiesUtil.class.getResourceAsStream("/jdbc.properties");
		try {
			pts.load(in);
			value = pts.getProperty(PropertiesName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public String getFtpOutIP() {
		return getProperties("ftp.outip");
	}

	public String getFtpOutPort() {
		return getProperties("ftp.outport");
	}

	public static void main(String[] args) {
		PropertiesUtil p = new PropertiesUtil();
	}

}
