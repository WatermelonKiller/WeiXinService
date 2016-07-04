package com.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.core.pojo.Photo;
import com.core.pojo.store.Store;
import com.core.pojo.store.StoreReq;
import com.core.thread.TokenThread;

/**
 * 门店微信接口类
 * 
 * @author Administrator
 * 
 */
public class StoreUtil {
	// 申请门店url
	public static final String AuditingStoreUrl = "https://api.weixin.qq.com/cgi-bin/poi/addpoi?access_token=TOKEN";
	// 获取类目url
	public static final String GetStoreTypesUrl = "https://api.weixin.qq.com/cgi-bin/poi/getwxcategory?access_token=TOKEN";
	// 获取门店信息
	public static final String GetStoreInfoUrl = "https://api.weixin.qq.com/cgi-bin/poi/getpoi?access_token=TOKEN";
	// 查询门店列表
	public static final String SelStoreListUrl = "https://api.weixin.qq.com/cgi-bin/poi/getpoilist?access_token=TOKEN";
	// 修改门店信息
	public static final String updateStoreUrl = "https://api.weixin.qq.com/cgi-bin/poi/updatepoi?access_token=TOKEN";
	// 删除门店
	public static final String delStoreUrl = "https://api.weixin.qq.com/cgi-bin/poi/delpoi?access_token=TOKEN";

	/**
	 * 
	 * @Description:添加门店申请
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-7
	 */
	public static boolean auditingStore(Store store) {
		String url = AuditingStoreUrl.replace("TOKEN",
				TokenThread.accessToken.getToken());
		// 拼装json字符串
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("base_info", store);
		JSONObject json = new JSONObject();
		json.put("business", map);
		// 收到结果

		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"POST", json.toString().replace("\"poi_id\":\"\",", "")));
		System.out.println(json.toString().replace("\"poi_id\":\"\",", ""));
		if (result.getInt("errcode") == 0) {
			return true;
		} else {
			System.out.println("errormsg:" + result.getInt("errcode") + ","
					+ result.getString("errmsg"));
			return false;
		}
	}

	/**
	 * 
	 * @Description:获取门店类型接口
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-10
	 */
	public static List<String> getStoreTypeList() {
		String url = GetStoreTypesUrl.replace("TOKEN",
				TokenThread.accessToken.getToken());
		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"GET", null));
		if (result.containsKey("category_list")) {
			JSONArray typeArray = result.getJSONArray("category_list");
			return JSONArray.toList(typeArray);
		} else {
			System.out.println(result.get("errmsg") + "......"
					+ result.get("errcode"));
			return null;
		}
	}

	/**
	 * 
	 * @Description:通过poi_id获取门店信息
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-16
	 */
	public static Store getStoreInfo(String poi_id) {
		String url = GetStoreInfoUrl.replace("TOKEN",
				TokenThread.accessToken.getToken());
		JSONObject json = new JSONObject();
		json.put("poi_id", poi_id);
		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"POST", poi_id));
		Store store = null;
		if (result.getInt("errcode") == 0) {
			JSONObject business = result.getJSONObject("business");
			JSONObject base_info = business.getJSONObject("base_info");
			store = (Store) JSONObject.toBean(base_info, Store.class);
		} else {
			System.out.println(result.getString("errmsg"));
		}
		return store;
	}

	/**
	 * 
	 * @Description:获取门店列表
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-17
	 */
	public static List<StoreReq> getStoreList() {
		String url = SelStoreListUrl.replace("TOKEN",
				TokenThread.accessToken.getToken());
		List<StoreReq> stores = new ArrayList<StoreReq>();
		int begin = 0;
		int limit = 20;
		int total = 0;
		do {
			JSONObject json = new JSONObject();
			json.put("begin", begin);
			json.put("limit", limit);
			JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(
					url, "POST", json.toString()));
			if (result.getInt("errcode") == 0) {
				JSONArray base_infos = result.getJSONArray("business_list");
				total = result.getInt("total_count");
				for (int i = 0; i < base_infos.size(); i++) {
					JSONObject storeJson = base_infos.getJSONObject(i)
							.getJSONObject("base_info");
					StoreReq store = new StoreReq();
					store.setAddress(storeJson.getString("address"));
					store.setAvg_price(storeJson.getInt("avg_price"));
					store.setBranch_name(storeJson.getString("branch_name"));
					store.setBusiness_name(storeJson.getString("business_name"));
					store.setCategories(storeJson.getJSONArray("categories")
							.getString(0));
					store.setCity(storeJson.getString("city"));
					store.setDistrict(storeJson.getString("district"));
					store.setIntroduction(storeJson.getString("introduction"));
					store.setLatitude(Float.valueOf(storeJson.get("latitude")
							.toString()));
					store.setLongitude(Float.valueOf(storeJson.get("longitude")
							.toString()));
					store.setOffset_type(storeJson.getInt("offset_type"));
					store.setOpen_time(storeJson.getString("open_time"));
					JSONArray photos_json = storeJson
							.getJSONArray("photo_list");
					Photo[] photos = new Photo[photos_json.size()];
					for (int j = 0; i < photos_json.size(); j++) {
						photos[j].setPhoto_url(photos_json.getString(j));
					}
					store.setPhoto_list(photos);
					store.setPoi_id(storeJson.getString("poi_id"));
					store.setProvince(storeJson.getString("province"));
					store.setRecommend(storeJson.getString("recommend"));
					store.setSid(storeJson.getString("sid"));
					store.setSpecial(storeJson.getString("special"));
					store.setTelephone(storeJson.getString("telephone"));
					store.setAvailable_state(storeJson
							.getInt("available_state"));
					store.setUpdate_statues(storeJson.getInt("update_status"));
					stores.add(store);
				}
			} else {
				stores.clear();
				System.out.println(result.getString("errmsg"));
				break;
			}
			begin += limit;
			limit += 20;
			// 当总数与list长度不等时再次查询
		} while (total < stores.size());
		return stores;
	}

	/**
	 * 
	 * @Description:修改门店信息
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-17
	 */
	public static boolean updateStore(Store store) {
		JSONObject base_info = new JSONObject();
		base_info.put("poi_id", store.getPoi_id());
		base_info.put("telephone", store.getTelephone());
		JSONArray jsonArray = JSONArray.fromObject(store.getPhoto_list());
		base_info.put("photo_list", jsonArray);
		base_info.put("recommend", store.getRecommend());
		base_info.put("special", store.getSpecial());
		base_info.put("introduction", store.getIntroduction());
		base_info.put("open_time", store.getOpen_time());
		base_info.put("avg_price", store.getAvg_price());
		JSONObject json = new JSONObject();
		JSONObject business = new JSONObject();
		business.put("base_info", base_info);
		json.put("business", business);
		String url = updateStoreUrl.replace("TOKEN",
				TokenThread.accessToken.getToken());
		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"POST", json.toString()));
		if (result.getInt("errcode") == 0) {
			return true;
		} else {
			System.out.println(result.getString("errmsg"));
			return false;
		}
	}

	/**
	 * 
	 * @Description:删除门店
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-17
	 */
	public static boolean delStore(String poi_id) {
		String url = delStoreUrl.replace("TOKEN",
				TokenThread.accessToken.getToken());
		JSONObject json = new JSONObject();
		json.put("poi_id", poi_id);
		JSONObject result = JSONObject.fromObject(WeiXinUtil.httpRequest(url,
				"POST", json.toString()));
		if (result.getInt("errcode") == 0) {
			return true;
		} else {
			System.out.println(result.getString("errmsg"));
			return false;
		}
	}

}
