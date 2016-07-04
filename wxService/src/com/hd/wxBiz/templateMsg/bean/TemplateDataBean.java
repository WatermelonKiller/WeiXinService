/**  
 * @Title: TemplateData.java
 * @Package com.hz.wxBiz.templateMsg.bean
 * @Description: TODO
 * @author 李继�?
 * @date 2015-7-15 上午10:07:24
 * @version V1.0  
 */
package com.hd.wxBiz.templateMsg.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_core_template_data")
public class TemplateDataBean implements Serializable {
	@Id
	private String id;
	private String template_id;
	private String data_name;
	private String color;
	private Integer sequence;

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

	public String getData_name() {
		return data_name;
	}

	public void setData_name(String data_name) {
		this.data_name = data_name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

}
