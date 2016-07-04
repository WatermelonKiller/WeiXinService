/**  
 * @Title: OpenIdUtil.java
 * @Package com.hz.core.util
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-11 下午2:54:34
 * @version V1.0  
 */
package com.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.thread.TokenThread;
import com.core.user.UserInfo;
import com.core.user.UserList;

public class UserUtil {
	private static Logger log = LoggerFactory.getLogger(UserUtil.class);
	// 获取code Url
	public static String get_Code_Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	// 只获取openId的scope
	public static final String base_scope = "snsapi_base";
	// 拉取用户信息的scope
	public static final String userinfo_scope = "snsapi_userinfo";
	// 通过code获取网页授权access_token
	public static String get_access_token = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	// 获取用户详细信息
	public static String get_user_detail_info = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 修改用户备注名
	public static String update_remark = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";
	// 拉取用户列表
	public static String get_user_list_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";

	/**
	 * 
	 * @description: 获取code并自动重定向至URL
	 * @time 2015-6-12
	 * @param redirect_url
	 * @param state
	 * @author Jc-Li
	 */
	public static void getCodeAndRedirect(String redirect_url, String state) {
		try {
			redirect_url = URLEncoder.encode(redirect_url, "utf-8");
			String get_path = get_Code_Url.replace("APPID", TokenThread.appid)
					.replace("REDIRECT_URI", redirect_url)
					.replace("SCOPE", base_scope);
			if (state != null && !"".equals(state)) {
				get_path = get_path.replace("STATE", state);
			} else {
				get_path = get_path.replace("&state=STATE", "");
			}
			WeiXinUtil.httpRequest(get_path, "GET", null);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @description: 使用code获取用户openid
	 * @time 2015-6-12
	 * @param code
	 * @return
	 * @author Jc-Li
	 */
	public static Map<String, Object> get_user_id(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		String url = get_access_token.replace("APPID", TokenThread.appid)
				.replace("SECRET", TokenThread.appsecret).replace("CODE", code);
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(
				url, "get", null));
		if (jsonObject != null) {
			try {
				map.put("access_token", jsonObject.getString("access_token"));
				map.put("expires_id", jsonObject.getInt("expires_id"));
				map.put("refresh_token", jsonObject.getString("refresh_token"));
				map.put("openid", jsonObject.getString("openid"));
				map.put("scope", jsonObject.getString("scope"));
				map.put("unionid", jsonObject.getString("unionid"));
			} catch (Exception e) {
				log.error("获取openid失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return map;
	}

	/**
	 * 
	 * @description: 拉取用户详细信息
	 * @time 2015-6-16
	 * @param openid
	 * @return
	 * @author Jc-Li
	 */

	public static UserInfo getUserDetailInfo(String openid) {
		String url = get_user_detail_info.replace("ACCESS_TOKEN",
				TokenThread.accessToken.getToken()).replace("OPENID", openid);
		System.out.println("url:::::::" + url);
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(
				url, "GET", null));
		System.out.println(jsonObject.toString());
		UserInfo ui = new UserInfo();
		try {
			if (jsonObject.get("subscribe").equals(0)) {
				ui.setSubscribe(0);
				ui.setOpenid(openid);
			} else {
				ui = (UserInfo) JSONObject.toBean(jsonObject, UserInfo.class);
				ui.setSubscribe_time(DateFormatUtil.formatTimeFromWx(ui
						.getSubscribe_time()));
			}

		} catch (Exception e) {
			if (jsonObject.containsKey("errcode")) {
				log.error("获取用户信息失败 errcode:{} errmsg:{}",
						jsonObject.getString("errcode"),
						jsonObject.getString("errmsg"));
			}
			e.printStackTrace();
			return null;
		}
		return ui;
	}

	/**
	 * 
	 * @description: 修改备注
	 * @time 2015-6-16
	 * @param openid
	 * @param remark
	 * @return 0为成功，其他为失败
	 * @author Jc-Li
	 */
	public static int updateRemark(String openid, String remark) {
		String url = update_remark.replace("ACCESS_TOKEN",
				TokenThread.accessToken.getToken());
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid", openid);
		map.put("remark", remark);
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"POST", jsonObject.toString()));
		if (result.getInt("errcode") != 0) {
			log.error("获取openid失败 errorcode:{} errmsg:{}",
					jsonObject.getInt("errcode"),
					jsonObject.getString("errmsg"));
		}
		return result.getInt("errcode");
	}

	/**
	 * 
	 * @description:获取用户列表
	 * @time 2015-6-17
	 * @param next_openid
	 * @return
	 * @author Jc-Li
	 */
	public static UserList getUserList(String next_openid) {
		String url = get_user_list_url.replace("ACCESS_TOKEN",
				TokenThread.accessToken.getToken());
		UserList ul = new UserList();
		if (null == next_openid || "".equals(next_openid)) {
			url = url.replace("&next_openid=NEXT_OPENID", "");
		}
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(
				url, "GET", null));
		try {
			ul = (UserList) jsonObject.toBean(jsonObject, UserList.class);
		} catch (Exception e) {
			log.error("获取用户列表失败 errorcode:{} errmsg:{}",
					jsonObject.getInt("errcode"),
					jsonObject.getString("errmsg"));
			ul = null;
		}
		return ul;
	}

	/**
	 * 
	 * @Description:通过oauth2.0获取用户信息
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-2
	 */
	public static String getUserInfo(JSONObject jsonObject) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		String access_token = jsonObject.getString("access_token");
		String openid = jsonObject.getString("openid");
		String finalUrl = url.replace("ACCESS_TOKEN", access_token).replace(
				"OPENID", openid);
		return WeiXinUtil.httpRequest(finalUrl, "GET", null);
	}
}
