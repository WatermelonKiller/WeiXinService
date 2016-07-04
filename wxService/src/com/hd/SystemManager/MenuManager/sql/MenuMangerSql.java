package com.hd.SystemManager.MenuManager.sql;

import com.hd.SystemInterface.SystemInterFace;

public class MenuMangerSql implements SystemInterFace {
	
	public static final String  TABLE_SEL = "select  *  from "+TAB_COLUMN;
	
	public static final String  DEPT_SEL = "select  *  from "+TAB_DEPT;
	
	public static final String  SYSTEM_TABLE_SEL = "select  *  from "+TAB_SYSTEM;
	
	public static final String ROLE_USER_SAVE=" insert into "+TAB_ROLE_USER+" (column_id,role_id) values (?,?) ";
	
	public static final String DEL_MENU_STATE ="delete from " +TAB_COLUMN ;
	
	public static final String DEL_ROLE_DATA= "delete from "+TAB_ROLE_USER;

}
