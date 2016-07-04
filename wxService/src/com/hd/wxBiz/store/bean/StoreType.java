package com.hd.wxBiz.store.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_store_type")
public class StoreType implements Serializable {
	@Id
	private String id;
	private String type_level1;
	private String type_level2;
	private String type_level3;
	private String code;
	private Integer sequence;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType_level1() {
		return type_level1;
	}

	public void setType_level1(String type_level1) {
		this.type_level1 = type_level1;
	}

	public String getType_level2() {
		return type_level2;
	}

	public void setType_level2(String type_level2) {
		this.type_level2 = type_level2;
	}

	public String getType_level3() {
		return type_level3;
	}

	public void setType_level3(String type_level3) {
		this.type_level3 = type_level3;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

}
