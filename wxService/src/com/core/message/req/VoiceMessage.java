/**  
 * @Title: VoiceMessage.java
 * @Package com.hz.core.message.req
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午2:40:45
 * @version V1.0  
 */
package com.core.message.req;

public class VoiceMessage extends BaseMessage {
	private String MediaId;
	private String Format;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

}
