/**  
 * @Title: Event.java
 * @Package com.hz.wxBiz.menu.bean
 * @Description: TODO
 * @author 李继�?
 * @date 2015-6-27 上午9:16:19
 * @version V1.0  
 */
package com.hd.wxBiz.menu.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_core_event")
public class Event implements Serializable {
	@Id
	private String id;
	private String event_name;
	private String event_key;
	private String event_type;
	private String request_mapping;
	private String description;
	private String create_time;
	private String create_user;
	private Integer click_num;
	private String released;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public String getEvent_key() {
		return event_key;
	}

	public void setEvent_key(String event_key) {
		this.event_key = event_key;
	}

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	public String getRequest_mapping() {
		return request_mapping;
	}

	public void setRequest_mapping(String request_mapping) {
		this.request_mapping = request_mapping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}

}
