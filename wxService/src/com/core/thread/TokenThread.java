/**  
 * @Title: TokenThread.java
 * @Package com.hz.core.thread
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-6 上午10:27:57
 * @version V1.0  
 */
package com.core.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.pojo.AccessToken;
import com.core.util.JsUtil;
import com.core.util.PropertiesUtil;
import com.core.util.WeiXinUtil;

public class TokenThread implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
	public static String appid = "";
	public static String appsecret = "";
	public static AccessToken accessToken = null;
	public static String JsApiTicket = "";

	public void run() {
		// 从配置文件获取appId和appsecret
		PropertiesUtil prop = new PropertiesUtil();
		appid = prop.getProperties("appId");
		appsecret = prop.getProperties("appSecret");
		// 设置死循环每两小时刷新accessToken
		while (true) {
			try {
				System.out
						.println("--------------------尝试连接获取accesstoken----------------------------");
				// 执行获取方法
				accessToken = WeiXinUtil.getAccessToken(appid, appsecret);
				JsApiTicket = JsUtil.getJsApiTicket(accessToken.getToken());
				if (null != accessToken) {
					log.info("获取access_token成功，有效时常{}秒 token:{}",
							accessToken.getExpiresIn(), accessToken.getToken());
					// 成功后休眠线程
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
					System.out.println("获取Token成功，Token线程休眠"
							+ (accessToken.getExpiresIn() - 200) + "秒");
				} else {
					System.out.println("Token获取为空，Token线程休眠60秒");
					Thread.sleep(60 * 1000);
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
					System.out.println("获取Token异常，Token线程休眠60秒");
				} catch (Exception e1) {
					log.error("{}", e1);
				}
				log.error("{}", e);
			}
		}

	}
}
