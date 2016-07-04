package com.hd.SystemManager.MenuManager.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_column")
public class SysMenu implements Serializable {

	private static final long serialVersionUID = -275088530777197550L;

	@Id
	private String column_id;
	private String parent_id;
	private String route;
	private String node_name;
	private String link;
	private String flag;
	private String system_id;
	private Long sequence;
	private String type;
	private String explain;
	private String levels;
	private String icon_id;
	private String icon_uu_name;
	private String dept_id;

	public String getColumn_id() {
		return column_id;
	}

	public void setColumn_id(String column_id) {
		this.column_id = column_id;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getNode_name() {
		return node_name;
	}

	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getSystem_id() {
		return system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public String getIcon_id() {
		return icon_id;
	}

	public void setIcon_id(String icon_id) {
		this.icon_id = icon_id;
	}

	public String getIcon_uu_name() {
		return icon_uu_name;
	}

	public void setIcon_uu_name(String icon_uu_name) {
		this.icon_uu_name = icon_uu_name;
	}

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

}
