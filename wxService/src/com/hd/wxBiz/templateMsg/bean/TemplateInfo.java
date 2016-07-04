/**  
 * @Title: TemplateInfo.java
 * @Package com.hz.wxBiz.templateMsg.bean
 * @Description: TODO
 * @author 李继�?
 * @date 2015-7-15 上午10:03:51
 * @version V1.0  
 */
package com.hd.wxBiz.templateMsg.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_core_template_info")
public class TemplateInfo implements Serializable {
	@Id
	private String id;
	private String template_id;
	private String template_name;
	private String template_url;
	private String top_color;
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}

	public String getTemplate_url() {
		return template_url;
	}

	public void setTemplate_url(String template_url) {
		this.template_url = template_url;
	}

	public String getTop_color() {
		return top_color;
	}

	public void setTop_color(String top_color) {
		this.top_color = top_color;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
