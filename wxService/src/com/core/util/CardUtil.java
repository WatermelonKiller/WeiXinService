package com.core.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.core.pojo.card.LandingPage;
import com.core.thread.TokenThread;
import com.hd.util.InputStreamUtil;

public class CardUtil {

	/************************************************* 创建卡券 **************************************************************/
	/**
	 * 
	 * @Description:创建卡券
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-17
	 */
	public static String createCard(JSONObject cardInfoJson) {
		String access_token = TokenThread.accessToken.getToken();
		String url = "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", access_token);

		String result = WeiXinUtil.httpRequest(url, "POST",
				cardInfoJson.toString());
		JSONObject resultJson = JSONObject.fromObject(result);
		if (resultJson.containsKey("errcode")) {
			if (resultJson.getInt("errcode") == 0) {
				return resultJson.getString("card_id");
			} else {
				System.out.println(resultJson.getString("errmsg"));
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @Description:设置某一卡券是否支持买单
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-17
	 */
	public static boolean cardpPaySet(String card_id, boolean is_open) {
		String url = "https://api.weixin.qq.com/card/paycell/set?access_token=TOKEN";
		url = url.replace("TOKEN", TokenThread.accessToken.getToken());
		JSONObject json = new JSONObject();
		json.put("card_id", card_id);
		json.put("is_open", is_open);
		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"POST", json.toString()));
		if (result.getInt("errcode") == 0) {
			return true;
		} else {
			System.out.println(result.toString());
			return false;
		}
	}

	/*************************************************** 投放卡券 ********************************************************************/
	/**
	 * 
	 * @Description:获取一次投放多种不同卡种（每种一张）的二维码 返回json形式字符串
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-17
	 */
	public static InputStream postCardQrCodeCreate(
			List<Map<String, String>> list) {
		String url = "https://api.weixin.qq.com/card/qrcode/create?access_token=TOKEN";
		url = url.replace("TOKEN", TokenThread.accessToken.getToken());
		if (list.size() > 1) {
			JSONObject json = new JSONObject();
			json.put("action_name", "QR_MULTIPLE_CARD");
			JSONArray card_list = new JSONArray();
			JSONObject action_info = new JSONObject();
			JSONObject multiple_card = new JSONObject();
			for (Map<String, String> map : list) {
				JSONObject card = new JSONObject();
				card.put("card_id", map.get("card_id"));
				card.put("code", map.get("code"));
				card_list.add(card);
			}
			multiple_card.put("card_list", card_list);
			action_info.put("multiple_card", multiple_card);
			json.put("action_info", action_info);
			JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(
					url, "POST", json.toString()));
			if (result.getInt("errcode") == 0) {
				String pic_url = result.getString("show_qrcode_url");
				return InputStreamUtil.getInputStream(pic_url);
			} else {
				System.out.println(result.toString());
				return null;
			}
		} else {
			return postCardQrCodeCreate(list.get(0));
		}
	}

	/**
	 * 
	 * @Description:获取一次一种的二维码 返回json形式字符串
	 * @Author:Jc-Li
	 * @param:map中包含card_id,code,is_unique_code,outer_id
	 * @Date:2015-12-18
	 */
	public static InputStream postCardQrCodeCreate(Map<String, String> map) {
		String url = "https://api.weixin.qq.com/card/qrcode/create?access_token=TOKEN";
		url = url.replace("TOKEN", TokenThread.accessToken.getToken());
		JSONObject json = new JSONObject();
		json.put("action_name", "QR_CARD");
		json.put("expire_seconds", 1800);
		JSONObject action_info = new JSONObject();
		JSONObject card = new JSONObject();
		card.put("card_id", map.get("card_id"));
		card.put("code", map.get("code"));
		card.put("openid", map.get("openid"));
		// 指定下发二维码，生成的二维码随机分配一个code，领取后不可再次扫描。填写true或false。默认false，注意填写该字段时，卡券须通过审核且库存不为0。
		card.put("is_unique_code", map.get("is_unique_code"));
		// 领取场景值，用于领取渠道的数据统计，默认值为0，字段类型为整型，长度限制为60位数字。用户领取卡券后触发的事件推送中会带上此自定义场景值。
		card.put("outer_id", map.get("outer_id"));
		action_info.put("card", card);
		json.put("action_info", action_info);
		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"POST", json.toString()));
		if (result.getInt("errcode") == 0) {
			String pic_url = result.getString("show_qrcode_url");
			return InputStreamUtil.getInputStream(pic_url);
		} else {
			System.out.println(result.toString());
			return null;
		}
	}

	/**
	 * 
	 * @Description:创建卡券货架 map中包含url和page_id
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-21
	 */
	public Map<String, String> createLandingPage(LandingPage landingPage) {
		String url = "https://api.weixin.qq.com/card/landingpage/create?access_token=TOKEN";
		url = url.replace("TOKEN", TokenThread.accessToken.getToken());
		JSONObject json = JSONObject.fromObject(landingPage);
		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"POST", json.toString()));
		if (result.getInt("errcode") == 0) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("url", result.getString("url"));
			map.put("page_id", result.getInt("page_id") + "");
			return map;
		} else {
			System.out.println(result.toString());
			return null;
		}
	}

	/**
	 * 
	 * @Description:给自定义code卡券添加code,每次最多不能超过100个；40109：code数量超过100个
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-21
	 */
	public Boolean addCustomCode(String card_id, List<String> codes) {
		String url = "https://api.weixin.qq.com/card/code/deposit?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		JSONObject json = new JSONObject();
		json.put("card_id", card_id);
		json.put("code", codes);
		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"POST", json.toString()));
		if (result.getInt("errcode") == 0) {
			return true;
		} else {
			System.out.println(result.toString());
			return false;
		}
	}

	/**
	 * 
	 * @Description:查询自定义Code卡券code数量，失败返回-1；
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-21
	 */
	public int getCustomCodeNum(String card_id) {
		String url = "https://api.weixin.qq.com/card/code/getdepositcount?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		JSONObject json = new JSONObject();
		json.put("card_id", card_id);
		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"POST", json.toString()));
		if (result.getInt("errcode") == 0) {
			return result.getInt("count");
		} else {
			return -1;
		}
	}

	/**
	 * 
	 * @Description:核查code接口,查询codeList中哪些有效，哪些无效
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-21
	 */
	public Map<String, List<String>> checkCustomCode(String card_id,
			List<String> codes) {
		String url = "https://api.weixin.qq.com/card/code/checkcode?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		JSONObject json = new JSONObject();
		json.put("card_id", card_id);
		json.put("code", codes);
		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"POST", json.toString()));
		if (result.getInt("errcode") == 0) {
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			JSONArray exist_code = result.getJSONArray("exist_code");
			JSONArray not_exist_code = result.getJSONArray("not_exist_code");
			List<String> exist_list = JSONArray.toList(exist_code);
			List<String> not_exist_list = JSONArray.toList(not_exist_code);
			map.put("exist_code", exist_list);
			map.put("not_exist_code", not_exist_list);
			return map;
		} else {
			System.out.println(result.toString());
			return null;
		}
	}

	/**
	 * 
	 * @Description:获取一段html代码，用于图文消息群发卡券（嵌入）
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-21
	 */
	public String getPostCardHTMLToNews(String card_id) {
		String url = "https://api.weixin.qq.com/card/mpnews/gethtml?access_token=TOKEN";
		url = url.replace("TOKEN", TokenThread.accessToken.getToken());
		JSONObject json = new JSONObject();
		json.put("card_id", card_id);
		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"POST", json.toString()));
		if (result.getInt("errcode") == 0) {
			return result.getString("content");
		} else {
			System.out.println(result.toString());
			return null;
		}
	}

	public static String decryptCode(String encytpt_code) {
		String url = "https://api.weixin.qq.com/card/code/decrypt?access_token=TOKEN";
		url = url.replace("TOKEN", TokenThread.accessToken.getToken());
		JSONObject json = new JSONObject();
		json.put("encrypt_code", encytpt_code);
		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"POST", json.toString()));
		if (result.getInt("errcode") == 0) {
			return result.getString("code");
		} else {
			System.out.println(result.toString());
			return null;
		}
	}
}
