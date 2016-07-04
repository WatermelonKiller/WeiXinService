package com.hd.SystemManager.MenuManager.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_dept")
public class Department implements Serializable {

	private static final long serialVersionUID = -275088530777197550L;

	@Id
	private String dept_id;
	private String dept_name;
	private String dept_sequence;
	private String sys_id;
	/**
	 * @return the dept_id
	 */
	public String getDept_id() {
		return dept_id;
	}
	/**
	 * @param dept_id the dept_id to set
	 */
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	/**
	 * @return the dept_name
	 */
	public String getDept_name() {
		return dept_name;
	}
	/**
	 * @param dept_name the dept_name to set
	 */
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	/**
	 * @return the dept_sequence
	 */
	public String getDept_sequence() {
		return dept_sequence;
	}
	/**
	 * @param dept_sequence the dept_sequence to set
	 */
	public void setDept_sequence(String dept_sequence) {
		this.dept_sequence = dept_sequence;
	}
	/**
	 * @return the sys_id
	 */
	public String getSys_id() {
		return sys_id;
	}
	/**
	 * @param sys_id the sys_id to set
	 */
	public void setSys_id(String sys_id) {
		this.sys_id = sys_id;
	}
    
	

}
