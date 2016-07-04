/**  
 * @Title: CustomUtil.java
 * @Package com.core.util
 * @Description: TODO
 * @author 李继超
 * @date 2015-7-27 下午2:32:56
 * @version V1.0  
 */
package com.core.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.thread.TokenThread;

public class CustomUtil {
	private static Logger log = LoggerFactory.getLogger(CustomUtil.class);
	private static String send_msg_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	public boolean sendMessageToUser(String openId, String type, Map<String, String> content) {
		String url = send_msg_url.replaceFirst("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("touser", openId);
		map.put("msgtype", type);
		map.put(type, content);
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		String result = WeiXinUtil.httpRequest(url, "POST", jsonObject.toString());
		JSONObject resultJson = JSONObject.fromObject(result);
		if (resultJson.get("errcode").toString().equals("0")) {
			return true;
		} else {
			return false;
		}
	}
}
