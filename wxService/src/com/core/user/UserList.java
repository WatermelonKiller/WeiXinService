/**  
 * @Title: UserList.java
 * @Package com.core.user
 * @Description: 每次拉取用户id列表缓存介质
 * @author 李继超
 * @date 2015-6-17 下午3:20:10
 * @version V1.0  
 */
package com.core.user;

public class UserList {
	private int total;
	private int count;
	private Data data;
	private String next_openid;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getNext_openid() {
		return next_openid;
	}

	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}

}
