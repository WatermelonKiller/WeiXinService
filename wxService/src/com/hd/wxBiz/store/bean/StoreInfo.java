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
	private String poi_id;// �ŵ�ٷ�id
	private Integer update_statues;// �޸����״̬
	private Integer available_state;// �������״̬
	private String business_name;// �ŵ����ƣ���Ϊ�̻������磺���������ͣ���Ӧ������������ַ���ֵ�������Ϣ������ʾ��������������
	private String branch_name;// �ֵ����ƣ���Ӧ����������Ϣ����Ӧ���ŵ������ظ�������ʾ���������������꣩
	private String province;
	private String city;
	private String district;// �ŵ����ڵ���
	private String address;
	private String telephone;// �ŵ�ĵ绰�������֣����š��ֻ��ž��ɡ�-��������
	private String categories;// �ŵ�����ͣ���ͬ�������á�,���������磺��ʳ�����ˣ��������ϸ����μ�������΢���ŵ���Ŀ��
	private Integer offset_type;// �������ͣ�1 Ϊ�������꣨Ŀǰֻ��ѡ1��
	private Float longitude;// �ŵ����ڵ���λ�õľ���
	private Float latitude;// �ŵ����ڵ���λ�õ�γ�ȣ���γ�Ⱦ�Ϊ�������꣬���ѡ����Ѷ��ͼ��ǵ����꣩
	private String recommend;// �Ƽ�Ʒ��������Ϊ�Ƽ��ˣ��Ƶ�Ϊ�Ƽ��׷�������Ϊ�Ƽ����澰��ȣ�����Լ���ҵ���Ƽ�����
	private String special;// ��ɫ���������wifi�����ͣ�����ͻ����ŵ��̻����ṩ����ɫ���ܻ����
	private String open_time;// Ӫҵʱ�䣬24 Сʱ�Ʊ�ʾ���á�-�����ӣ��� 8:00-20:00
	private Integer avg_price;// �˾��۸񣬴���0 ������
	private String introduction;// �̻���飬��Ҫ�����̻���Ϣ��
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
