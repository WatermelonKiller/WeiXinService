/**  
 * @Title: ToolUtil.java
 * @Package com.core.util
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-26 下午5:14:46
 * @version V1.0  
 */
package com.core.util;

import java.util.Date;
import java.util.UUID;

import org.dom4j.Document;

import com.hd.util.MD5Encoder;

public class ToolUtil {
	/**
	 * 
	 * @description: 生成6位随机字符串
	 * @time 2015-7-22
	 * @return
	 * @author Jc-Li
	 */
	public static String getSixBitWords() {
		Date date = new Date();
		String nowTime = DateFormatUtil.formatTimeFromWx(date.getTime() + "");
		String RadomStr = UUID.randomUUID().toString().toUpperCase();
		return MD5Encoder.encode(nowTime + RadomStr + Math.random())
				.substring(0, 6).toUpperCase();
	}

	/**
	 * 
	 * @Description:生成随机字符串
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-3
	 */
	public static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 
	 * @Description:生成时间戳
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-3
	 */
	public static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * org.w3c.dom.Document -> org.dom4j.Document
	 * 
	 * @param doc
	 *            Document(org.w3c.dom.Document)
	 * @return Document
	 */
	public static Document parse(org.w3c.dom.Document doc) throws Exception {
		if (doc == null) {
			return (null);
		}
		org.dom4j.io.DOMReader xmlReader = new org.dom4j.io.DOMReader();
		return (xmlReader.read(doc));
	}

	/**
	 * org.dom4j.Document -> org.w3c.dom.Document
	 * 
	 * @param doc
	 *            Document(org.dom4j.Document)
	 * @throws Exception
	 * @return Document
	 */
	public static org.w3c.dom.Document parse(Document doc) throws Exception {
		if (doc == null) {
			return (null);
		}
		java.io.StringReader reader = new java.io.StringReader(doc.asXML());
		org.xml.sax.InputSource source = new org.xml.sax.InputSource(reader);
		javax.xml.parsers.DocumentBuilderFactory documentBuilderFactory = javax.xml.parsers.DocumentBuilderFactory
				.newInstance();
		javax.xml.parsers.DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		return (documentBuilder.parse(source));
	}

}
