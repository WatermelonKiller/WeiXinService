package com.hd.wxBiz.store.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_storeInfo")
public class StoreInfo implements Serializable {
	@Id
	@Column(name = "sid")
	private String sid;
	private String poi_id;// 门店官方id
	private Integer update_statues;// 修改审核状态
	private Integer available_state;// 申请可用状态
	private String business_name;// 门店名称（仅为商户名，如：国美、麦当劳，不应包含地区、地址、分店名等信息，错误示例：北京国美）
	private String branch_name;// 分店名称（不应包含地区信息，不应与门店名有重复，错误示例：北京王府井店）
	private String province;
	private String city;
	private String district;// 门店所在地区
	private String address;
	private String telephone;// 门店的电话（纯数字，区号、分机号均由“-”隔开）
	private String categories;// 门店的类型（不同级分类用“,”隔开，如：美食，川菜，火锅。详细分类参见附件：微信门店类目表）
	private Integer offset_type;// 坐标类型，1 为火星坐标（目前只能选1）
	private Float longitude;// 门店所在地理位置的经度
	private Float latitude;// 门店所在地理位置的纬度（经纬度均为火星坐标，最好选用腾讯地图标记的坐标）
	private String recommend;// 推荐品，餐厅可为推荐菜；酒店为推荐套房；景点为推荐游玩景点等，针对自己行业的推荐内容
	private String special;// 特色服务，如免费wifi，免费停车，送货上门等商户能提供的特色功能或服务
	private String open_time;// 营业时间，24 小时制表示，用“-”连接，如 8:00-20:00
	private Integer avg_price;// 人均价格，大于0 的整数
	private String introduction;// 商户简介，主要介绍商户信息等
	private String signature;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getPoi_id() {
		return poi_id;
	}

	public void setPoi_id(String poi_id) {
		this.poi_id = poi_id;
	}

	public Integer getUpdate_statues() {
		return update_statues;
	}

	public void setUpdate_statues(Integer update_statues) {
		this.update_statues = update_statues;
	}

	public Integer getAvailable_state() {
		return available_state;
	}

	public void setAvailable_state(Integer available_state) {
		this.available_state = available_state;
	}

	public String getBusiness_name() {
		return business_name;
	}

	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public Integer getOffset_type() {
		return offset_type;
	}

	public void setOffset_type(Integer offset_type) {
		this.offset_type = offset_type;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getOpen_time() {
		return open_time;
	}

	public void setOpen_time(String open_time) {
		this.open_time = open_time;
	}

	public Integer getAvg_price() {
		return avg_price;
	}

	public void setAvg_price(Integer avg_price) {
		this.avg_price = avg_price;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
