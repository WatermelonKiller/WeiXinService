/**  
 * @Title: InitServlet.java
 * @Package com.hz.core.service
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-6 上午10:24:13
 * @version V1.0  
 */
package com.core.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.thread.TokenThread;
import com.core.util.PropertiesUtil;
import com.core.util.WeiXinUtil;

public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 3985288971965824309L;
	private static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);

	// 在web.xml配置启动获取ACCESSTOKEN
	@Override
	public void init() throws ServletException {
		PropertiesUtil prop = new PropertiesUtil();
		TokenThread.appid = prop.getProperties("appId");
		TokenThread.appsecret = prop.getProperties("appSecret");
		log.info("weixin api appid:{}", TokenThread.appid);
		log.info("weixin api appsercret", TokenThread.appsecret);
		if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {
			log.error("appid and appsercret configuration error");
		} else {
			// 开启获取accessToken线程
			new Thread(new TokenThread()).start();
		}
	}
}
