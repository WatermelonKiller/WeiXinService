package com.hd.wxBiz.Message.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*******************************************************************************
 *
 *               Copyright(c) 2010 by 哈尔滨华泽数码科�?��限公�?
 *                       All rights reserved.
 *
 *******************************************************************************
 *     File Name         :  com.core.message.req.MainMessageDetail.java
 *     Description(说明)	:
 * -----------------------------------------------------------------------------
 *     No.        Date              Revised by           Description
 *     0          2015-6-23           Edwin              Created
 ******************************************************************************/
@Entity
@Table(name="tab_core_message_detail")
public class MainMessageDetail {
	
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="main_id")
	private String mainId;
	
	@Column(name="contents")
	private String content;
	
	@Column(name="media_id")
	private String mediaId;
	
	@Column(name="pic_url")
	private String picUrl; //图片链接 
	
	@Column(name="format")
	private String format; //语音格式，如amr，speex�?
	
	@Column(name="thumb_media_id")
	private String thumbMediaId; //视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据�?
	
	@Column(name="location_x")
	private String locationX;//地理位置维度 
	
	@Column(name="location_y")
	private String locationY;//地理位置经度 
	
	@Column(name="scale")
	private String scale;//地图缩放大小 
	
	@Column(name="label")
	private String label;//地理位置信息 
	
	@Column(name="title")
	private String title ;//标题
	
	@Column(name="description")
	private String description;//描述
	
	@Column(name="url")
	private String url; //URL
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getLocationX() {
		return locationX;
	}

	public void setLocationX(String locationX) {
		this.locationX = locationX;
	}

	public String getLocationY() {
		return locationY;
	}

	public void setLocationY(String locationY) {
		this.locationY = locationY;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
