/**  
 * @Title: WxUser.java
 * @Package com.hz.wxBiz.user.bean
 * @Description: TODO
 * @author 李继�?
 * @date 2015-6-17 下午4:40:22
 * @version V1.0  
 */
package com.hd.wxBiz.user.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_core_userinfo")
public class UserInfo extends com.core.user.UserInfo {
	@Id
	private String id;

	/**
	 * @param id
	 */
	public UserInfo(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
