/**  
 * @Title: ButtonBean.java
 * @Package com.hz.wxBiz.menu.bean
 * @Description: TODO
 * @author 李继�?
 * @date 2015-6-18 上午11:18:24
 * @version V1.0  
 */
package com.hd.wxBiz.menu.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_core_menuButton")
public class ButtonBean implements Serializable {
	@Id
	private String id;
	private String name;
	private String type;
	private String key_link;
	private int sequence;
	private String father_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey_link() {
		return key_link;
	}

	public void setKey_link(String key_link) {
		this.key_link = key_link;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getFather_id() {
		return father_id;
	}

	public void setFather_id(String father_id) {
		this.father_id = father_id;
	}

}
