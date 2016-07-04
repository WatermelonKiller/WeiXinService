package com.hd.shareSubscribe.sql;

public class ShareSubscribeSql {
	// 插入二维码数据
	public static final String SAVEQRCODESQL = "insert into tab_qr_code (id,open_id,code_path,create_time) values (?,?,?,?)";
	// 插入扫描二维码数据
	public static final String SAVESHAREQRCODE = "insert into tab_share_subscribe (id,from_open_id,to_open_id,create_time) values (?,?,?,?)";
	// 查询二维码ftp地址
	public static final String SELQRCODEPATH = "select code_path from tab_qr_code where 1=1 ";
}
