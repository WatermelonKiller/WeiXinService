/**  
 * @Title: MenuUtil.java
 * @Package com.hz.core.util
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-15 下午1:59:36
 * @version V1.0  
 */
package com.core.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.pojo.menu.Button;
import com.core.pojo.menu.CommonButton;
import com.core.pojo.menu.ComplexButton;
import com.core.pojo.menu.Menu;
import com.core.pojo.menu.ViewButton;
import com.core.thread.TokenThread;

public class MenuUtil {
	private static Logger log = LoggerFactory.getLogger(MenuUtil.class);
	// 获取菜单URL
	public static String get_menu_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	// 创建菜单Url
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 删除菜单url
	public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	// 获取菜单配置接口
	public static String get_menu_current_url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN";

	/**
	 * 
	 * @description: 从服务器获取自定义菜单配置信息
	 * @time 2015-7-22
	 * @return
	 * @author Jc-Li
	 */
	public static Menu getMenu_info() {
		String url = get_menu_url.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(url, "GET", null));
		// 定义整个菜单
		Menu menu = new Menu();
		List<Button> buttonList = new ArrayList<Button>();
		// 获取菜单状态
		// 获取菜单内容json
		JSONObject menuinfo_json = jsonObject.getJSONObject("menu");
		// 获取分菜单json数组
		if (menuinfo_json != null) {
			JSONArray father_json = menuinfo_json.getJSONArray("button");
			// 循环添加一级菜单
			for (int i = 0; i < father_json.size(); i++) {
				// 判断是否有子集
				if (!father_json.getJSONObject(i).containsKey("sub_button")) {
					// 无子集情况(为无二级菜单的一级菜单)
					// 判断二级菜单按钮类型
					if (father_json.getJSONObject(i).getString("type") != null
							&& father_json.getJSONObject(i).getString("type").equals("click")) {
						// 普通按钮
						CommonButton commonButton = new CommonButton();
						commonButton.setType(father_json.getJSONObject(i).getString("type"));
						commonButton.setName(father_json.getJSONObject(i).getString("name"));
						commonButton.setKey(father_json.getJSONObject(i).getString("key"));
						buttonList.add(commonButton);
					} else if (father_json.getJSONObject(i).getString("type") != null
							&& father_json.getJSONObject(i).getString("type").equals("view")) {
						// 链接按钮
						ViewButton viewButton = new ViewButton();
						viewButton.setType(father_json.getJSONObject(i).getString("type"));
						viewButton.setName(father_json.getJSONObject(i).getString("name"));
						viewButton.setUrl(father_json.getJSONObject(i).getString("url"));
						buttonList.add(viewButton);
					}
				} else {
					// 带有二级菜单的一级菜单
					JSONObject sub_button_json = father_json.getJSONObject(i);
					// 一级菜单按钮
					ComplexButton complexButton = new ComplexButton();
					complexButton.setName(sub_button_json.getString("name"));
					// 二级菜单列表
					List<Button> sub_button = new ArrayList<Button>();
					// 二级菜单json数组
					JSONArray sub_list = sub_button_json.getJSONArray("sub_button");
					// 解析二级菜单
					for (int j = 0; j < sub_list.size(); j++) {
						// 判断二级菜单按钮类型
						if (sub_list.getJSONObject(j).getString("type") != null
								&& sub_list.getJSONObject(j).getString("type").equals("click")) {
							// 普通按钮
							CommonButton commonButton = new CommonButton();
							commonButton.setType(sub_list.getJSONObject(j).getString("type"));
							commonButton.setName(sub_list.getJSONObject(j).getString("name"));
							commonButton.setKey(sub_list.getJSONObject(j).getString("key"));
							sub_button.add(commonButton);
						} else if (sub_list.getJSONObject(j).getString("type") != null
								&& sub_list.getJSONObject(j).getString("type").equals("view")) {
							// 链接按钮
							ViewButton viewButton = new ViewButton();
							viewButton.setType(sub_list.getJSONObject(j).getString("type"));
							viewButton.setName(sub_list.getJSONObject(j).getString("name"));
							viewButton.setUrl(sub_list.getJSONObject(j).getString("url"));
							sub_button.add(viewButton);
						}
					}
					// 将二级菜单列表作为数组传入一级菜单按钮
					final int sub_size = sub_button.size();
					Button[] sub_buttons = sub_button.toArray(new Button[sub_size]);
					complexButton.setSub_button(sub_buttons);
					buttonList.add(complexButton);
				}
			}
		}
		final int size = buttonList.size();
		Button[] buttons = buttonList.toArray(new Button[size]);
		menu.setButton(buttons);
		return menu;
	}

	/**
	 * 
	 * @description: 创建菜单
	 * @time 2015-6-6
	 * @param menu
	 * @param accessToken
	 * @return 0为成功，其他为失败
	 * @author Jc-Li
	 */
	public static int createMenu(Menu menu) {
		int result = 0;
		String url = menu_create_url.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		String jsonMenu = JSONObject.fromObject(menu).toString();
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(url, "POST", jsonMenu));
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return result;
	}

	/**
	 * 
	 * @description: 删除自定义菜单
	 * @time 2015-6-15
	 * @param menu
	 * @param accessToken
	 * @return
	 * @author Jc-Li
	 */
	public static int deleteMenu() {
		int result = 0;
		String url = menu_delete_url.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(url, "GET", null));
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("删除菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return result;
	}
}
