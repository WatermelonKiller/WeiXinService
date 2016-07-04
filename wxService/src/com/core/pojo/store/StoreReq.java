package com.core.pojo.store;

public class StoreReq extends Store {
	private Integer available_state;
	private Integer update_statues;

	public Integer getAvailable_state() {
		return available_state;
	}

	public void setAvailable_state(Integer available_state) {
		this.available_state = available_state;
	}

	public Integer getUpdate_statues() {
		return update_statues;
	}

	public void setUpdate_statues(Integer update_statues) {
		this.update_statues = update_statues;
	}

}
