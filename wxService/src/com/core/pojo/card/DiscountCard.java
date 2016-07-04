package com.core.pojo.card;

public class DiscountCard extends BaseCard {
	private static final String card_type = "DISCOUNT";
	private Integer discount;

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public static String getCardType() {
		return card_type;
	}

}
