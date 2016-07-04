/**  
 * @Title: KeyWords.java
 * @Package com.hz.wxBiz.keywordsManager.bean
 * @Description: TODO
 * @author 李继�?
 * @date 2015-7-22 下午3:44:23
 * @version V1.0  
 */
package com.hd.wxBiz.keywordsManager.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_core_keywords")
public class KeyWords implements Serializable {
	@Id
	private String id;
	// 关键字：事件的KEY或消息内�?
	private String keyword;
	private String type;
	private String request_mapping;
	private String in_use;
	private String create_time;
	private String create_user;
	private Integer click_num;
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRequest_mapping() {
		return request_mapping;
	}

	public void setRequest_mapping(String request_mapping) {
		this.request_mapping = request_mapping;
	}

	public String getIn_use() {
		return in_use;
	}

	public void setIn_use(String in_use) {
		this.in_use = in_use;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public Integer getClick_num() {
		return click_num;
	}

	public void setClick_num(Integer click_num) {
		this.click_num = click_num;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
