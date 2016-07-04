package com.hd.SystemBean;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class UserBean implements java.io.Serializable {

	// Fields

	private Long userid; //用户主键
	private String loginname;  //登录用户�?
	private String password;  //密码
	private String username;	//用户名称
	private String role;	//角色
	private String power; //权限
	private String depid; //部门id
	private String depname; //部门名称
	//private String depId;
	
	
	public String getDepid() {
		return depid;
	}
	public void setDepid(String depid) {
		this.depid = depid;
	}
	public String getDepname() {
		return depname;
	}
	public void setDepname(String depname) {
		this.depname = depname;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	
}