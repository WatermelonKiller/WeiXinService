/**  
 * @Title: ArticleMessage.java
 * @Package com.hz.core.message.resp
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午2:48:54
 * @version V1.0  
 */
package com.core.message.resp;

import java.util.List;

public class NewsMessage extends BaseMessage {
	private int ArticleCount;
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}

}
