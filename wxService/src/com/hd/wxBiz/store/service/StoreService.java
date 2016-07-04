package com.hd.wxBiz.store.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.pojo.Photo;
import com.core.pojo.store.Store;
import com.core.pojo.store.StoreReq;
import com.core.util.StoreUtil;
import com.hd.util.HttpRequest;
import com.hd.util.SqlParameter;
import com.hd.util.dao.CommonDao;
import com.hd.wxBiz.store.bean.StoreInfo;
import com.hd.wxBiz.store.bean.StorePhoto;
import com.hd.wxBiz.store.bean.StoreType;
import com.hd.wxBiz.store.sql.StoreSql;

@Service
@Transactional
public class StoreService {
	@Autowired
	private CommonDao cd;

	/**
	 * 
	 * @Description:门店列表sql
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-10
	 */
	public String selStoreSql(String parameter, String available_state) {
		StringBuffer sql = new StringBuffer();
		sql.append(StoreSql.SELSTORE);
		if (parameter != null && !"".equals(parameter)) {
			sql.append(" and branch_name like '%" + parameter
					+ "%' or business_name like '%" + parameter
					+ "%' or (province+city+district+address) like '%"
					+ parameter + "%'");
		}
		if (available_state != null && !"".equals(available_state)) {
			sql.append(" and available_state=" + available_state);
		}
		return sql.toString();
	}

	public String getStoreType(String types, String level) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select type_level" + level + " as type ,count(type_level"
				+ level + ") as num from tab_store_type where 1=1 ");
		if (types != null && !"".equals(types)) {
			sql.append(" and type_level" + (Integer.valueOf(level) - 1) + " ='"
					+ types + "'");
		}
		sql.append(" group by  type_level" + level);
		sql.append(" order by num desc ");
		List<Map<String, String>> list = cd.queryListByPS(sql.toString(), null);
		if (list.size() == 1 && list.get(0).get("type") == null) {
			return null;
		} else {
			JSONArray json = JSONArray.fromObject(list);
			return json.toString();
		}

	}

	/**
	 * 
	 * @Description:刷新门店类型
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-11
	 */
	public boolean updateStoreType(List<String> list) {
		List<StoreType> stList = new ArrayList<StoreType>();
		int i = 1;
		for (String str : list) {
			StoreType st = new StoreType();
			st.setId(UUID.randomUUID().toString().toUpperCase());
			st.setSequence(i);
			String[] types = str.split(",");
			st.setType_level1(types[0]);
			if (types.length > 1) {
				st.setType_level2(types[1]);
			}
			if (types.length > 2) {
				st.setType_level3(types[2]);
			}
			stList.add(st);
			i++;
		}
		try {
			String num = cd.getOneValue("select count(*) from tab_store_type");
			boolean flag = false;
			if (Integer.valueOf(num) > 0) {
				flag = cd.execute(StoreSql.DELSTORETYPE);
			} else {
				flag = true;
			}
			if (flag) {
				return cd.saveList(stList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @Description:门店添加申请
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-7
	 */
	public boolean addStore(StoreInfo storeInfo) {
		List<Map<String, String>> photo_urls = getPic_url(storeInfo.getSid());
		Store store = new Store();
		Photo[] photo = new Photo[photo_urls.size()];
		if (photo_urls.size() > 0) {
			for (int i = 0; i < photo_urls.size(); i++) {
				Photo photoBean = new Photo();
				photoBean.setPhoto_url(photo_urls.get(i).get("photo_url"));
				photo[i] = photoBean;
			}
		}
		// 生成pojo对象并封装属性
		try {
			BeanUtils.copyProperties(store, storeInfo);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		store.setPhoto_list(photo);
		// 如果申请成功则保存至数据库
		if (StoreUtil.auditingStore(store)) {
			storeInfo.setAvailable_state(2);
			storeInfo.setUpdate_statues(0);
			return cd.insertinfor(storeInfo);
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Description:门店审核结果消息接收
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-11
	 */
	public boolean auditingStore(String sid, String poi_id, String result) {
		StoreInfo si = (StoreInfo) cd.hibernateGet(StoreInfo.class, sid);
		if (result.equals("succ")) {
			si.setPoi_id(poi_id);
			si.setAvailable_state(3);
		} else {
			si.setAvailable_state(4);
		}
		return cd.updateinfor(si);
	}

	// 解密百度坐标成为火星坐标
	public String bd_decrypt(double bd_lat, double bd_lon) {
		double lat = 31.22997;
		double lon = 121.640756;
		double x_pi = lat * lon / 180.0;
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		double gg_lon = z * Math.cos(theta);
		double gg_lat = z * Math.sin(theta);
		JSONObject json = new JSONObject();
		json.put("lat", String.format("%.6f", gg_lat));
		json.put("lon", String.format("%.6f", gg_lon));
		return json.toString();
	}

	/**
	 * 
	 * @Description:重置门店列表
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-17
	 */
	public boolean refreshStore() throws Exception {
		List<StoreReq> stores = StoreUtil.getStoreList();
		if (stores.size() > 0) {
			List<StorePhoto> storePhotos = new ArrayList<StorePhoto>();
			List<StoreInfo> storeInfos = new ArrayList<StoreInfo>();
			for (int i = 0; i < stores.size(); i++) {
				StoreInfo si = new StoreInfo();
				BeanUtils.copyProperties(si, stores.get(i));
				int j = 1;
				if (stores.get(i).getPhoto_list() != null) {
					for (Photo photo : stores.get(i).getPhoto_list()) {
						StorePhoto sp = new StorePhoto();
						sp.setId(UUID.randomUUID().toString().toUpperCase());
						sp.setPhoto_url(photo.getPhoto_url());
						sp.setSequence(j);
						j++;
						sp.setStore_id(si.getSid());
						storePhotos.add(sp);
						cd.saveList(storePhotos);
					}
				}
				if (si.getSid() == null || si.getSid().equals("")) {
					si.setSid(UUID.randomUUID().toString().toUpperCase());
				}
				storeInfos.add(si);
			}
			if (cd.executeSQL(
					"delete from tab_storeInfo    delete from tab_store_photo",
					null)) {
				return cd.saveList(storeInfos);
			}
		}
		return false;
	}

	/**
	 * 
	 * @Description:删除门店
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-17
	 */
	public boolean delStore(String poi_id, String sid) {
		if (StoreUtil.delStore(poi_id)) {
			return cd.DeleteSql("tab_storeInfo", "sid", sid)
					& cd.DeleteSql("tab_store_photo", "store_id", sid);
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Description:修改门店信息
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-17
	 */
	public boolean updateStore(StoreInfo si) {
		List<Map<String, String>> photo_urls = getPic_url(si.getSid());
		Store store = new Store();
		try {
			BeanUtils.copyProperties(store, si);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		Photo[] photo = new Photo[photo_urls.size()];
		if (photo_urls.size() > 0) {
			for (int i = 0; i < photo_urls.size(); i++) {
				photo[i].setPhoto_url(photo_urls.get(i).get("photo_url"));
			}
		}
		store.setPhoto_list(photo);
		if (StoreUtil.updateStore(store)) {
			si.setUpdate_statues(1);
			return cd.updateinfor(si);
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Description:获取门店信息
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2016-2-26
	 */
	public StoreInfo getOneStore(String sid) {
		return (StoreInfo) cd.hibernateGet(StoreInfo.class, sid);
	}

	/**
	 * 
	 * @Description:获取门店图片地址
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2016-2-26
	 */
	public List<Map<String, String>> getPic_url(String sid) {
		return cd.queryListByPS(StoreSql.SELPHOTOURL + " and store_id =?",
				new SqlParameter(sid));
	}

	/**
	 * 
	 * @Description:上传门店介绍图片地址
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2016-2-26
	 */
	public boolean addPicUrl(StorePhoto storePhoto) {
		return cd.insertinfor(storePhoto);
	}

	/**
	 * 
	 * @Description:百度Api 根据百度坐标获取地址
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2016-2-26
	 */
	public String getAddressByPoint(String lon, String lat) {
		String url = "http://api.map.baidu.com/geocoder/v2/?ak=vp7OkjLDil4DUAFYxchkpETT&location=LAT,LON&output=json&pois=1";
		url = url.replace("LON", lon).replace("LAT", lat);
		JSONObject jsonObject = JSONObject.fromObject(HttpRequest.sendGet(url,
				null));
		JSONObject resultObject = jsonObject.getJSONObject("result")
				.getJSONObject("addressComponent");
		JSONObject sendJson = new JSONObject();
		sendJson.put("province", resultObject.getString("province"));
		sendJson.put("city", resultObject.getString("city"));
		sendJson.put("district", resultObject.getString("district"));
		sendJson.put(
				"address",
				resultObject.getString("street")
						+ resultObject.getString("street_number"));
		return sendJson.toString();
	}

}
