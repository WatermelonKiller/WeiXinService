package com.hd.shareSubscribe.sql;

public class ShareSubscribeSql {
	// �����ά������
	public static final String SAVEQRCODESQL = "insert into tab_qr_code (id,open_id,code_path,create_time) values (?,?,?,?)";
	// ����ɨ���ά������
	public static final String SAVESHAREQRCODE = "insert into tab_share_subscribe (id,from_open_id,to_open_id,create_time) values (?,?,?,?)";
	// ��ѯ��ά��ftp��ַ
	public static final String SELQRCODEPATH = "select code_path from tab_qr_code where 1=1 ";
}
