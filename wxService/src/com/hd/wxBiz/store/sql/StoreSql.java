package com.hd.wxBiz.store.sql;

public class StoreSql {
	// ��ѯͼƬ����
	public static final String SELPHOTOURL = "select photo_url from tab_store_photo where 1=1 ";
	// ��ѯ�����ŵ�
	public static final String SELSTORE = "select sid,poi_id,available_state,business_name,branch_name,province,city,district,address from tab_storeInfo where 1=1 ";
	// ɾ���ŵ�����
	public static final String DELSTORETYPE = "delete from tab_store_type";
}
