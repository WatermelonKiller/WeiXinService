/**  
 * @Title: WeiXinUtil.java
 * @Package com.hz.core.util
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午4:59:50
 * @version V1.0  
 */
package com.core.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.message.resp.templateMsg.TemplateData;
import com.core.message.resp.templateMsg.TemplateMsg;
import com.core.pojo.AccessToken;
import com.core.pojo.menu.Menu;

public class WeiXinUtil {
	private static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);
	// 获取token地址
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 创建菜单Url
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 删除菜单url
	public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	// 根据short_id获取模板消息id Url
	public static String get_template_id = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
	// 发送模板消息
	public static String send_template_msg = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	/**
	 * 
	 * @description: 发起https请求 并获取结果
	 * @time 2015-6-6
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 * @author Jc-Li
	 */
	public static String httpRequest(String requestUrl, String requestMethod,
			String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			System.setProperty("https.protocols", "TLSv1");
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inpurStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferReader = new BufferedReader(inpurStreamReader);
			String str = null;
			while ((str = bufferReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferReader.close();
			inpurStreamReader.close();
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (ConnectException e) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return buffer.toString();
	}

	/**
	 * 
	 * @description: 获取token
	 * @time 2015-6-6
	 * @param appid
	 * @param appsecret
	 * @return
	 * @author Jc-Li
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = JSONObject.fromObject(httpRequest(requestUrl,
				"GET", null));
		if (null != jsonObject) {
			try {
				System.out.println(requestUrl);
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				System.out.println("获取token成功："
						+ jsonObject.getString("access_token"));
			} catch (Exception e) {
				accessToken = null;
				log.error("获取token失败 errorcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 
	 * @description: 创建菜单
	 * @time 2015-6-6
	 * @param menu
	 * @param accessToken
	 * @return 0为成功，其他为失败
	 * @author Jc-Li
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		String jsonMenu = JSONObject.fromObject(menu).toString();
		JSONObject jsonObject = JSONObject.fromObject(httpRequest(url, "POST",
				jsonMenu));
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return result;
	}

	/**
	 * 
	 * @description: 删除自定义菜单
	 * @time 2015-6-15
	 * @param menu
	 * @param accessToken
	 * @return
	 * @author Jc-Li
	 */
	public static int deleteMenu(Menu menu, String accessToken) {
		int result = 0;
		String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
		String jsonMenu = JSONObject.fromObject(menu).toString();
		JSONObject jsonObject = JSONObject.fromObject(httpRequest(url, "POST",
				jsonMenu));
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("删除菜单失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return result;
	}

	/**
	 * 
	 * @description: 获取Template_id 模板消息id
	 * @time 2015-6-11
	 * @param template_id_short
	 * @param accessToken
	 * @return 失败返回null
	 * @author Jc-Li
	 */
	public static String getTemplate_id(String template_id_short,
			String accessToken) {
		String template_id = "";
		String url = get_template_id.replace("ACCESS_TOKEN", accessToken);
		Map<String, String> short_id_map = new HashMap<String, String>();
		short_id_map.put("template_id_short", template_id_short);
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(short_id_map);
		JSONObject result = JSONObject.fromObject(httpRequest(url, "POST",
				jsonObject.toString()));
		if (null != result) {
			try {
				template_id = result.getString("template_id");
			} catch (Exception e) {
				log.error("创建菜单失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
				template_id = null;
			}
		} else {
			template_id = null;
		}
		return template_id;
	}

	/**
	 * 
	 * @description: 发送模板消息
	 * @time 2015-6-11
	 * @param tm
	 * @param accessToken
	 * @return
	 * @throws Exception
	 * @author Jc-Li
	 */
	public static String sendTemplateMsg(TemplateMsg tm, String accessToken)
			throws Exception {
		String url = send_template_msg.replace("ACCESS_TOKEN", accessToken);
		// 拼装map为指定格式
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("touser", tm.getTouser());
		map.put("template_id", tm.getTemplate_id());
		map.put("url", tm.getUrl());
		map.put("topcolor", tm.getTopcolor());
		Map<String, Object> template_data = new HashMap<String, Object>();
		for (TemplateData d : tm.getData()) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("value", d.getData().getValue());
			data.put("color", d.getData().getColor());
			template_data.put(d.getData_name(), data);
		}
		map.put("data", template_data);
		JSONObject jsonObject = new JSONObject();
		// 转为json
		jsonObject.putAll(map);
		System.out.println("json_________________:" + jsonObject.toString());
		JSONObject result = JSONObject.fromObject(httpRequest(url, "POST",
				jsonObject.toString()));
		System.out.println("_______模板消息结果_________:" + result.toString());
		if (result.getString("errcode").equals("0")) {
			return "success";
		} else {
			return "error";
		}

	}

}
