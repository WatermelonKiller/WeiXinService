/**  
 * @Title: MessageUtil.java
 * @Package com.hz.core.util
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午2:54:11
 * @version V1.0  
 */
package com.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.core.message.resp.Article;
import com.core.message.resp.MusicMessage;
import com.core.message.resp.NewsMessage;
import com.core.message.resp.TextMessage;
import com.core.message.resp.VideoMessage;
import com.core.message.resp.VoiceMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";

	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	public static final String REQ_MESSAGE_TYPE_SHORT_VIDEO = "shortvideo";
	// 推送
	public static final String REQ_MESSAGE_TYPE__EVENT = "event";
	// 订阅
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	// 取消订阅
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 
	 * @description: 将服务器发回的xml字符串进行Map装载
	 * @time 2015-7-22
	 * @param str
	 * @return
	 * @throws Exception
	 * @author Jc-Li
	 */
	public static Map<String, String> parseXml(String str) throws Exception {
		System.out.println(str
				+ "---------------------------------------------");
		Map<String, String> map = new HashMap<String, String>();
		Document document = null;
		try {
			document = DocumentHelper.parseText(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Element root = document.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

	/**
	 * 
	 * @description:解析服务器请求信息，用map装载，如果加密 则解密
	 * @time 2015-7-22
	 * @param request
	 * @return
	 * @throws Exception
	 * @author Jc-Li
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		String encrypt_type = request.getParameter("encrypt_type");
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();
		// 判断是否为加密模式
		if (encrypt_type != null && "aes".equals(encrypt_type)) {
			// 获取解密参数
			String msg_signature = request.getParameter("msg_signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			// 循环寻找加密内容
			for (Element e : elementList) {
				if (e.getName().equals("Encrypt")) {
					// 解析加密内容并将结果拼装至map中
					String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
					String fromXML = String.format(format, e.getText());
					String desText = SecretUtil.desEncryptMsg(fromXML,
							msg_signature, timestamp, nonce);
					Document desDocument = DocumentHelper.parseText(desText
							.trim());
					Element desRoot = desDocument.getRootElement();
					@SuppressWarnings("unchecked")
					List<Element> desElementList = desRoot.elements();
					for (Element el : desElementList) {
						map.put(el.getName(), el.getText());
					}
					break;
				}
			}
		} else {
			for (Element e : elementList) {
				map.put(e.getName(), e.getText());
			}
		}
		inputStream.close();
		inputStream = null;
		return map;
	}

	// 将消息转为XML格式
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	public static String videoMessageToXml(VideoMessage videoMessage) {
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}

	public static String voiceMessageToXml(VoiceMessage voiceMessage) {
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}

	// 修改XStream解析方式(别动！！！)
	private static XStream xstream = new XStream(new XppDriver() {
		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean cdata = true;

				@Override
				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				@Override
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	public static String replaceXmlLable(String xml) {
		return xml.replace("<xml>", "").replace("</xml>", "");
	}
}
