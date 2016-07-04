/**  
 * @Title: AccessToken.java
 * @Package com.hz.core
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-6 上午9:22:03
 * @version V1.0  
 */
package com.core.pojo;

public class AccessToken {
	private String token;
	private int expiresIn;

	public String getToken() {
		System.out.println("token:" + token);
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

}
