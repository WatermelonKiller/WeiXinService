/**  
 * @Title: ResultMessage.java
 * @Package com.hz.core.message.req
 * @Description: 微信服务器消息返回基类
 * @author 李继超
 * @date 2015-6-10 下午3:27:26
 * @version V1.0  
 */
package com.core.message.req;

public class ResultMessage {
	private int errcode;
	private String errmsg;

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
