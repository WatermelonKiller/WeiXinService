/**  
 * @Title: ComplexButton.java
 * @Package com.hz.core.pojo
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-6 上午9:26:43
 * @version V1.0  
 */
package com.core.pojo.menu;

public class ComplexButton extends Button {
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}

}
