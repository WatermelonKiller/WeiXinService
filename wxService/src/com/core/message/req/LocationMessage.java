/**  
 * @Title: LocationMessage.java
 * @Package com.hz.core.message.req
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午2:38:42
 * @version V1.0  
 */
package com.core.message.req;

public class LocationMessage extends BaseMessage {
	private String Location_X;
	private String Location_y;
	private String Scale;
	private String Lable;

	public String getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}

	public String getLocation_y() {
		return Location_y;
	}

	public void setLocation_y(String location_y) {
		Location_y = location_y;
	}

	public String getScale() {
		return Scale;
	}

	public void setScale(String scale) {
		Scale = scale;
	}

	public String getLable() {
		return Lable;
	}

	public void setLable(String lable) {
		Lable = lable;
	}

}
