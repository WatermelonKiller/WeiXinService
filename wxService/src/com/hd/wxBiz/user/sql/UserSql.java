/**  
 * @Title: UserSql.java
 * @Package com.hz.wxBiz.user.sql
 * @Description: TODO
 * @author 李继�?
 * @date 2015-6-17 下午4:55:54
 * @version V1.0  
 */
package com.hd.wxBiz.user.sql;

public class UserSql {
	// �?��用户数量
	public static final String count_user_num = "select count(*) from tab_core_userinfo where 1=1 ";

	public static final String get_userid_by_openid = "select id from tab_core_userinfo where 1=1 ";
}
