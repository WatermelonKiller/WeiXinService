package com.hd.shareSubscribe.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_share_subscribe")
public class ShareSubscribe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String from_open_id;
	private String to_open_id;
	private String create_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrom_open_id() {
		return from_open_id;
	}

	public void setFrom_open_id(String from_open_id) {
		this.from_open_id = from_open_id;
	}

	public String getTo_open_id() {
		return to_open_id;
	}

	public void setTo_open_id(String to_open_id) {
		this.to_open_id = to_open_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

}
