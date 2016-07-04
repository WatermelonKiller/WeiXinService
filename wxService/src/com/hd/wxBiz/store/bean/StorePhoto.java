package com.hd.wxBiz.store.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_store_photo")
public class StorePhoto implements Serializable {
	@Id
	private String id;
	private String store_id;
	private String photo_url;
	private Integer sequence;
	private String ftp_url;
	private String ftp_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getFtp_url() {
		return ftp_url;
	}

	public void setFtp_url(String ftp_url) {
		this.ftp_url = ftp_url;
	}

	public String getFtp_name() {
		return ftp_name;
	}

	public void setFtp_name(String ftp_name) {
		this.ftp_name = ftp_name;
	}

}
