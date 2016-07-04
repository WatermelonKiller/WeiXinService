package com.core.pojo.card;

public class CashCard extends BaseCard {
	// 花费单位为分
	private static final String card_type = "CASH";
	private Integer least_cost;
	private Integer reduce_cost;

	public Integer getLeast_cost() {
		return least_cost;
	}

	public void setLeast_cost(Integer least_cost) {
		this.least_cost = least_cost;
	}

	public Integer getReduce_cost() {
		return reduce_cost;
	}

	public void setReduce_cost(Integer reduce_cost) {
		this.reduce_cost = reduce_cost;
	}

	public static String getCardType() {
		return card_type;
	}

}
