package com.hd.wxBiz.store.sql;

public class StoreSql {
	// 查询图片链接
	public static final String SELPHOTOURL = "select photo_url from tab_store_photo where 1=1 ";
	// 查询所有门店
	public static final String SELSTORE = "select sid,poi_id,available_state,business_name,branch_name,province,city,district,address from tab_storeInfo where 1=1 ";
	// 删除门店类型
	public static final String DELSTORETYPE = "delete from tab_store_type";
}
