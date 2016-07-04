package com.hd.fileattach.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 文件上传Bean
 * 
 */

@Entity
@Table(name = "tab_archive_attach")
public class ArchiveAttachBean implements Serializable {
	@Id
	private String file_id;
	private String file_name;
	private String file_real_name;
	private String file_type;
	private String create_time;
	private String file_path;
	private String file_archive_id;
	private String ftp_server_id;
	private String file_archive_type;
	private String file_title;
	private String file_addr;

	public ArchiveAttachBean() {
		super();
	}

	public ArchiveAttachBean(String file_id, String file_name,
			String file_real_name, String file_type, String create_time,
			String file_path, String file_archive_id, String ftp_server_id,
			String file_archive_type) {
		super();
		this.file_id = file_id;
		this.file_name = file_name;
		this.file_real_name = file_real_name;
		this.file_type = file_type;
		this.create_time = create_time;
		this.file_path = file_path;
		this.file_archive_id = file_archive_id;
		this.ftp_server_id = ftp_server_id;
		this.file_archive_type = file_archive_type;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_real_name() {
		return file_real_name;
	}

	public void setFile_real_name(String file_real_name) {
		this.file_real_name = file_real_name;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_archive_id() {
		return file_archive_id;
	}

	public void setFile_archive_id(String file_archive_id) {
		this.file_archive_id = file_archive_id;
	}

	public String getFtp_server_id() {
		return ftp_server_id;
	}

	public void setFtp_server_id(String ftp_server_id) {
		this.ftp_server_id = ftp_server_id;
	}

	public String getFile_archive_type() {
		return file_archive_type;
	}

	public void setFile_archive_type(String file_archive_type) {
		this.file_archive_type = file_archive_type;
	}

	public String getFile_title() {
		return file_title;
	}

	public void setFile_title(String file_title) {
		this.file_title = file_title;
	}

	public String getFile_addr() {
		return file_addr;
	}

	public void setFile_addr(String file_addr) {
		this.file_addr = file_addr;
	}

	@Override
	public String toString() {
		return "ArchiveAttachBean [file_id=" + file_id + ", file_name="
				+ file_name + ", file_real_name=" + file_real_name
				+ ", file_type=" + file_type + ", create_time=" + create_time
				+ ", file_path=" + file_path + ", file_archive_id="
				+ file_archive_id + ", ftp_server_id=" + ftp_server_id
				+ ", file_archive_type=" + file_archive_type + "]";
	}

}
