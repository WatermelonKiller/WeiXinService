/**  
 * @Title: BaseMessage.java 接收消息基类
 * @Package com.hz.core.message.req
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午2:31:00
 * @version V1.0  
 */
package com.core.message.req;

public class BaseMessage {
	// 开发者微信号
	private String ToUserName;
	// 发送方账号
	private String FromUserName;
	private long CreateTime;
	private String MsgType;
	private Long MsgId;

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

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
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
