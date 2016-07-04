package com.core.pojo.card;

public class GiftCard extends BaseCard {
	private static final String card_type = "GIFT";
	private String gift;

	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	public static String getCardType() {
		return card_type;
	}

}
