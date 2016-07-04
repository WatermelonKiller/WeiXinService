/**  
 * @Title: GroupUtil.java
 * @Package com.core.util
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-16 下午4:03:11
 * @version V1.0  
 */
package com.core.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.thread.TokenThread;
import com.core.user.Group;

public class GroupUtil {
	private static Logger log = LoggerFactory.getLogger(GroupUtil.class);
	// 添加分组
	public static String create_group_url = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
	// 获取分组信息
	public static String get_group_info_url = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	// 获取用户所在分组id
	public static String get_user_group_id = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
	// 修改分组名称
	public static String update_group_name_url = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
	// 移动用户至某分组
	public static String move_user_toGroup = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
	// 批量移动用户
	public static String move_many_user_toGroup = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=ACCESS_TOKEN";
	// 删除分组
	public static String delete_group_url = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";

	/**
	 * 
	 * @description: 添加分组
	 * @time 2015-6-16
	 * @param name
	 * @return
	 * @author Jc-Li
	 */
	public static Group createGroup(String name) {
		String url = create_group_url.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		String msg = "{\"group\":{\"name\":\"NAME\"}}";
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(url, "POST", msg.replace("NAME", name)));
		Group group = null;
		try {
			group = (Group) JSONObject.toBean(jsonObject.getJSONObject("group"), Group.class);
		} catch (Exception e) {
			log.error("添加分组失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
		}
		return group;
	}

	/**
	 * 
	 * @description: 获取分组
	 * @time 2015-6-16
	 * @return
	 * @author Jc-Li
	 */
	public static List<Group> getGroup() {
		String url = get_group_info_url.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		List<Group> groups = new ArrayList<Group>();
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(url, "GET", null));
		JSONArray group_json = jsonObject.getJSONArray("groups");
		try {
			groups = JSONArray.toList(group_json);
		} catch (Exception e) {
			log.error("获取分组信息失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
		}
		return groups;
	}

	/**
	 * 
	 * @description: 查询用户所在分组id
	 * @time 2015-6-17
	 * @param openid
	 * @return 失败为-1;
	 * @author Jc-Li
	 */
	public static int getUserGroupId(String openid) {
		String url = get_user_group_id.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		String parameter = "{\"openid\":\"" + openid + "\"}";
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(url, "POST", parameter));
		try {
			int id = jsonObject.getInt("groupid");
			return id;
		} catch (Exception e) {
			log.error("查询用户所在分组失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			return -1;
		}
	}

	/**
	 * 
	 * @description: 修改组名
	 * @time 2015-6-17
	 * @param groupid
	 * @param groupname
	 * @return 0为成功其他失败
	 * @author Jc-Li
	 */
	public static int updateGroupName(int groupid, String groupname) {
		int result = 0;
		String url = update_group_name_url.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		String parameter = "{\"group\":{\"id\":" + groupid + ",\"name\":\"" + groupname + "\"}}";
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(url, "POST", parameter));
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("修改组名失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return result;
	}

	/**
	 * 
	 * @description: 移动用户至其他分组
	 * @time 2015-6-17
	 * @param opendid
	 * @param to_groupid
	 * @return 0为成功 其他失败
	 * @author Jc-Li
	 */
	public static int move_user_to_group(String openid, int to_groupid) {
		int result = 0;
		String url = move_user_toGroup.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		String parameter = "{\"openid\":\"" + openid + "\",\"to_groupid\":" + to_groupid + "}";
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(url, "POST", parameter));
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("移动分组失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return result;

	}

	/**
	 * 
	 * @description: 批量移动用户
	 * @time 2015-6-17
	 * @param openid
	 * @param to_groupid
	 * @return 0为成功 其他失败
	 * @author Jc-Li
	 */
	public static int move_user_to_group(String[] openid, int to_groupid) {
		int result = 0;
		String url = move_many_user_toGroup.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		StringBuffer openids = new StringBuffer();
		for (String str : openid) {
			openids.append("\"" + str + "\",");
		}
		String parameter = "{\"openid_list\":\"[" + openids.toString().substring(0, openids.length() - 1)
				+ "\"],\"to_groupid\":" + to_groupid + "}";
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(url, "POST", parameter));
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("移动分组失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return result;
	}

	/**
	 * 
	 * @description: 删除分组，分组内人员变为默认分组
	 * @time 2015-6-17
	 * @param groupid
	 * @return 0成功 其他为失败
	 * @author Jc-Li
	 */
	public static int deleteGroup(int groupid) {
		int result = 0;
		String url = delete_group_url.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		String parameter = "{\"group\":{\"id\":" + groupid + "}}";
		JSONObject jsonObject = JSONObject.fromObject(WeiXinUtil.httpRequest(url, "POST", parameter));
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("删除分组失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return result;
	}
}
