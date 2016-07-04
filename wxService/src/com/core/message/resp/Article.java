/**  
 * @Title: Article.java
 * @Package com.hz.core.message.resp
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午2:47:38
 * @version V1.0  
 */
package com.core.message.resp;

public class Article {
	private String Title;
	private String Description;
	private String PicUrl;
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

}
