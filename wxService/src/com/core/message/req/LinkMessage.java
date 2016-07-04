/**  
 * @Title: LinkMessage.java
 * @Package com.hz.core.message.req
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午2:39:51
 * @version V1.0  
 */
package com.core.message.req;

public class LinkMessage extends BaseMessage {
	private String Title;
	private String Descrpition;
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescrpition() {
		return Descrpition;
	}

	public void setDescrpition(String descrpition) {
		Descrpition = descrpition;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

}
