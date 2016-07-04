package com.hd.login.sql;

import com.hd.SystemInterface.SystemInterFace;

/*******************************************************************************
 * 
 * All rights reserved.
 * 
 ******************************************************************************* 
 * File Name : com.yangjy.login.sql.LoginSql.java Description(说锟斤拷) :
 * -----------------------------------------------------------------------------
 * No. Date Revised by Description 0 2014-11-14 Edwin Created
 ******************************************************************************/

public class LoginSql implements SystemInterFace {
	
	public static final String GZ_CAE_SEL = "select * from tab_case where state like 'gz' order by editdate desc";
	
	public static final String BK_CASE_SEL = "select * from tab_case where state like 'bk' order by editdate desc";

	public static final String YJS_CASE_SEL = "select * from tab_case where state like 'yjs' order by editdate desc";

	public static final String SCHOOL_SEL = "select * from tab_schoolinfo ";

	public static final String SEL_ID_ANSWER = "select id from tab_user where 1=1 ";

	public static final String SEL_QUESTION = "select question from tab_user where 1=1";
	public static final String SEL_IDBYUSERNAME = "select id from tab_user where 1=1";
	
	public static final String FIELD_COLUMN = "c.column_id as id,c.parent_id as pId," +
			"c.node_name as name,c.type,c.explain," +
			"c.levels,c.sequence,c.link,c.flag,c.system_id,c.route,c.icon_id ,c.icon_uu_name ";
	
	
	public static final String SEL_COLUMN_BY_SYSTEM_ROLE="select "+FIELD_COLUMN+" from "+TAB_COLUMN+" c where c.system_id=? and c.column_id in(select rc.column_id from "+TAB_ROLE_COLUMN_VIEW+" as rc where rc.role_id =? ) and flag=1 order by c.sequence";
}
