package com.core.util;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class JsUtil {

	/**
	 * 调用微信JS接口的临时票据
	 * 
	 * @param access_token
	 *            接口访问凭证
	 * @return
	 */
	public static String getJsApiTicket(String access_token) {
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		String requestUrl = url.replace("ACCESS_TOKEN", access_token);
		// 发起GET请求获取凭证
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(
				requestUrl, "GET", null));
		String ticket = null;
		if (null != jsonObject) {
			try {
				ticket = jsonObject.getString("ticket");
			} catch (JSONException e) {
				// 获取token失败
				System.out.println("获取token失败 errcode:{} errmsg:{}"
						+ jsonObject.getInt("errcode")
						+ jsonObject.getString("errmsg"));
			}
		}
		return ticket;
	}

	/**
	 * 
	 * @Description:获取页面accessToken
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-10-16
	 */

	public static String getPageAccessToken(HttpServletRequest request) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		String code = request.getParameter("code");
		if (code == null) {
			code = "";
		}
		String finalUrl = url.replace("APPID", AppInfoUtil.getAppid())
				.replace("SECRET", AppInfoUtil.getAppsercret())
				.replace("CODE", code);
		String result = WeiXinUtil.httpRequest(finalUrl, "POST", null);
		System.out.println(result);
		return result;
	}

}
