package com.core.pojo.card;

public class CouponCard extends BaseCard {
	private static final String card_type = "GENERAL_COUPON";
	private String default_detail;

	public String getDefault_detail() {
		return default_detail;
	}

	public void setDefault_detail(String default_detail) {
		this.default_detail = default_detail;
	}

	public static String getCardType() {
		return card_type;
	}

}
