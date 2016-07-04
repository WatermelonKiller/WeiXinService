/**  
 * @Title: MyX509TrustManager.java
 * @Package com.hz.core.util
 * @Description: TODO
 * @author 李继�?
 * @date 2015-6-5 下午4:58:59
 * @version V1.0  
 */
package com.hd.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

//网上找的 不知道干嘛的
public class MyX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {

	}

	public void checkServerTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {

	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}
