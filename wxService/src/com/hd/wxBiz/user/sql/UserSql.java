/**  
 * @Title: UserSql.java
 * @Package com.hz.wxBiz.user.sql
 * @Description: TODO
 * @author ζη»§θΆ?
 * @date 2015-6-17 δΈε4:55:54
 * @version V1.0  
 */
package com.hd.wxBiz.user.sql;

public class UserSql {
	// ζ?η¨ζ·ζ°ι
	public static final String count_user_num = "select count(*) from tab_core_userinfo where 1=1 ";

	public static final String get_userid_by_openid = "select id from tab_core_userinfo where 1=1 ";
}
