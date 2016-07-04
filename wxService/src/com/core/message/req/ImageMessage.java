/**  
 * @Title: ImageMessage.java
 * @Package com.hz.core.message.req
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午2:37:40
 * @version V1.0  
 */
package com.core.message.req;

public class ImageMessage extends BaseMessage {
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

}
