package com.hd.login.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_user")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8107540835283470319L;
	
	@Id
	private String id;
	private String username;
	private String password;
	private String cid;
	private String mail;
	private String system_id;
	private String tel;
	private String dept_id;
	  
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCid() {
		return cid;
	}
	
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	

	
	/**
	 * @return the system_id
	 */
	public String getSystem_id() {
		return system_id;
	}

	/**
	 * @param system_id the system_id to set
	 */
	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}


	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	

}
