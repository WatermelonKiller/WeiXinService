/**  
 * @Title: UserService.java
 * @Package com.hz.wxBiz.user.service
 * @Description: TODO
 * @author 李继�?
 * @date 2015-6-17 下午4:44:53
 * @version V1.0  
 */
package com.hd.wxBiz.user.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.util.UserUtil;
import com.hd.util.dao.CommonDao;
import com.hd.wxBiz.user.bean.UserInfo;
import com.hd.wxBiz.user.sql.UserSql;

@Service
@Transactional
public class UserService {
	@Autowired
	private CommonDao cd;

	/**
	 * 
	 * @description: 处理关注事件
	 * @time 2015-6-17
	 * @param openid
	 * @return
	 * @author Jc-Li
	 */
	public boolean subscribeAccount(String openid) {
		String sql = UserSql.get_userid_by_openid + " and openid='" + openid
				+ "' ";
		String result = cd.getOneValue(sql);
		UserInfo ui = (UserInfo) UserUtil.getUserDetailInfo(openid);
		boolean flag = false;
		if (ui != null) {
			if (result != null && !"".equals(result)) {
				ui.setId(UUID.randomUUID().toString().toUpperCase());
				flag = cd.insertinfor(ui);
			} else {
				ui.setId(result);
				flag = cd.updateinfor(ui);
			}
		}
		return flag;
	}

	/**
	 * 
	 * @description: 处理取消关注
	 * @time 2015-6-18
	 * @param openid
	 * @return
	 * @author Jc-Li
	 */
	public boolean desubscribeAccount(String openid) {
		String sql = UserSql.get_userid_by_openid + " and openid='" + openid
				+ "' ";
		String result = cd.getOneValue(sql);
		UserInfo ui = (UserInfo) cd.hibernateGet(UserInfo.class, result);
		ui.setSubscribe(0);
		return cd.updateinfor(ui);
	}

}
