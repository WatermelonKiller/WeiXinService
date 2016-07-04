package com.hd.wxBiz.card.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_card_info")
public class CardInfo implements Serializable {
	@Id
	private String id;
	private String card_id;
	private String card_type;
	// base_info
	private String logo_url;
	private String code_type;
	private String brand_name;
	private String title;
	private String sub_title;
	private String color;
	private String notice;
	private String description;
	// sku
	private Integer quantity;
	// date_info
	private String type;
	private Integer begin_timestamp;
	private Integer end_timestamp;
	private Integer fixed_term;
	private Integer fixed_begin_term;
	// 非必填
	private Boolean use_custom_code;
	private Boolean bind_openid;
	private String service_phone;
	private String location_id_list;
	private String source;
	private String custom_url_name;
	private String center_title;
	private String center_sub_title;
	private String center_url;
	private String custom_url;
	private String custom_url_sub_title;
	private String promotion_url_name;
	private String promotion_url;
	private String promotion_url_sub_title;
	private Integer get_limit;
	private Boolean can_share;
	private Boolean can_give_friend;
	// 团购
	private String deal_detail;
	// 代金
	private Integer least_cost;
	private Integer reduce_cost;
	// 折扣
	private Integer discount;
	// 礼品
	private String gift;
	// 优惠
	private String default_detail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public String getCode_type() {
		return code_type;
	}

	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSub_title() {
		return sub_title;
	}

	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getBegin_timestamp() {
		return begin_timestamp;
	}

	public void setBegin_timestamp(Integer begin_timestamp) {
		this.begin_timestamp = begin_timestamp;
	}

	public Integer getEnd_timestamp() {
		return end_timestamp;
	}

	public void setEnd_timestamp(Integer end_timestamp) {
		this.end_timestamp = end_timestamp;
	}

	public Integer getFixed_term() {
		return fixed_term;
	}

	public void setFixed_term(Integer fixed_term) {
		this.fixed_term = fixed_term;
	}

	public Integer getFixed_begin_term() {
		return fixed_begin_term;
	}

	public void setFixed_begin_term(Integer fixed_begin_term) {
		this.fixed_begin_term = fixed_begin_term;
	}

	public Boolean getUse_custom_code() {
		return use_custom_code;
	}

	public void setUse_custom_code(Boolean use_custom_code) {
		this.use_custom_code = use_custom_code;
	}

	public Boolean getBind_openid() {
		return bind_openid;
	}

	public void setBind_openid(Boolean bind_openid) {
		this.bind_openid = bind_openid;
	}

	public String getService_phone() {
		return service_phone;
	}

	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
	}

	public String getLocation_id_list() {
		return location_id_list;
	}

	public void setLocation_id_list(String location_id_list) {
		this.location_id_list = location_id_list;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCustom_url_name() {
		return custom_url_name;
	}

	public void setCustom_url_name(String custom_url_name) {
		this.custom_url_name = custom_url_name;
	}

	public String getCenter_title() {
		return center_title;
	}

	public void setCenter_title(String center_title) {
		this.center_title = center_title;
	}

	public String getCenter_sub_title() {
		return center_sub_title;
	}

	public void setCenter_sub_title(String center_sub_title) {
		this.center_sub_title = center_sub_title;
	}

	public String getCenter_url() {
		return center_url;
	}

	public void setCenter_url(String center_url) {
		this.center_url = center_url;
	}

	public String getCustom_url() {
		return custom_url;
	}

	public void setCustom_url(String custom_url) {
		this.custom_url = custom_url;
	}

	public String getCustom_url_sub_title() {
		return custom_url_sub_title;
	}

	public void setCustom_url_sub_title(String custom_url_sub_title) {
		this.custom_url_sub_title = custom_url_sub_title;
	}

	public String getPromotion_url_name() {
		return promotion_url_name;
	}

	public void setPromotion_url_name(String promotion_url_name) {
		this.promotion_url_name = promotion_url_name;
	}

	public String getPromotion_url() {
		return promotion_url;
	}

	public void setPromotion_url(String promotion_url) {
		this.promotion_url = promotion_url;
	}

	public String getPromotion_url_sub_title() {
		return promotion_url_sub_title;
	}

	public void setPromotion_url_sub_title(String promotion_url_sub_title) {
		this.promotion_url_sub_title = promotion_url_sub_title;
	}

	public Integer getGet_limit() {
		return get_limit;
	}

	public void setGet_limit(Integer get_limit) {
		this.get_limit = get_limit;
	}

	public Boolean getCan_share() {
		return can_share;
	}

	public void setCan_share(Boolean can_share) {
		this.can_share = can_share;
	}

	public Boolean getCan_give_friend() {
		return can_give_friend;
	}

	public void setCan_give_friend(Boolean can_give_friend) {
		this.can_give_friend = can_give_friend;
	}

	public String getDeal_detail() {
		return deal_detail;
	}

	public void setDeal_detail(String deal_detail) {
		this.deal_detail = deal_detail;
	}

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

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	public String getDefault_detail() {
		return default_detail;
	}

	public void setDefault_detail(String default_detail) {
		this.default_detail = default_detail;
	}

}
