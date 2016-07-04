/**  
 * @Title: VideoMessage.java
 * @Package com.hz.core.message.req
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午3:24:49
 * @version V1.0  
 */
package com.core.message.req;

public class VideoMessage extends BaseMessage {
	private String MediaId;
	private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

}
