/**  
 * @Title: VoiceMessage.java
 * @Package com.hz.core.message.resp
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午3:42:41
 * @version V1.0  
 */
package com.core.message.resp;

public class VoiceMessage extends BaseMessage {
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}

}
