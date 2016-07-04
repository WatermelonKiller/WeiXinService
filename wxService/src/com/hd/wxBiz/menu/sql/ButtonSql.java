/**  
 * @Title: ButtonSql.java
 * @Package com.hz.wxBiz.menu.sql
 * @Description: TODO
 * @author 李继�?
 * @date 2015-6-18 下午2:41:36
 * @version V1.0  
 */
package com.hd.wxBiz.menu.sql;

public class ButtonSql {
	public static final String SEL_ALL = "  select * from tab_core_menuButton where 1=1 ";

	public static final String count_nums = "select count(*) FROM tab_core_menuButton where 1=1 ";

	public static final String sel_miss_button = "select top 1 id from tab_core_menuButton where key_link is null or key_link='' and type <> 'father' ";
}
