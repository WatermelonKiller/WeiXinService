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
 *     File Name         :  com.core.message.req.MainMessage.java
 *     Description(说明)	:
 * -----------------------------------------------------------------------------
 *     No.        Date              Revised by           Description
 *     0          2015-6-23           Edwin              Created
 ******************************************************************************/
@Entity
@Table(name="tab_core_message_main")
public class MainMessage {
	
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="to_user_name")
	private String ToUserName;
	
	@Column(name="from_user_name")
	private String FromUserName;
	
	@Column(name="create_time")
	private String CreateTime;
	
	@Column(name="msg_type")
	private String MsgType;
	
	@Column(name="msg_id")
	private Long MsgId;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public Long getMsgId() {
		return MsgId;
	}

	public void setMsgId(Long msgId) {
		MsgId = msgId;
	}


	
}
