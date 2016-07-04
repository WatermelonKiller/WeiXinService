package com.hd.wxBiz.card.bean;

public class UserPayFromCardInfo {
	private String id;
	private String fromUserName;
	private String createTime;
	private String card_id;
	private String userCardCode;
	private String transld;
	private String locationName;
	private Integer fee;
	private Integer originalFee;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getUserCardCode() {
		return userCardCode;
	}

	public void setUserCardCode(String userCardCode) {
		this.userCardCode = userCardCode;
	}

	public String getTransld() {
		return transld;
	}

	public void setTransld(String transld) {
		this.transld = transld;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public Integer getOriginalFee() {
		return originalFee;
	}

	public void setOriginalFee(Integer originalFee) {
		this.originalFee = originalFee;
	}

}
