package com.core.pojo.card;

public class GroupOnCard extends BaseCard {
	private static final String card_type = "GROUPON";
	private String deal_detail;

	public String getCard_type() {
		return card_type;
	}

	public String getDeal_detail() {
		return deal_detail;
	}

	public void setDeal_detail(String deal_detail) {
		this.deal_detail = deal_detail;
	}

}
