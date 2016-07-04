package com.core.pojo.card;

import java.util.List;
import java.util.Map;

public class LandingPage {
	private String banner;// 页面的banner图片链接，须调用，建议尺寸为640*300。
	private String page_title;
	private Boolean can_share;
	private String scence;// 投放页面的场景值；
	// SCENE_NEAR_BY 附近
	// SCENE_MENU 自定义菜单
	// SCENE_QRCODE 二维码
	// SCENE_ARTICLE 公众号文章
	// SCENE_H5 h5页面
	// SCENE_IVR 自动回复
	// SCENE_CARD_CUSTOM_CELL 卡券自定义cell
	private List<Map<String, String>> card_list;// map中包含card_id和thumb_url(缩略图)

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getPage_title() {
		return page_title;
	}

	public void setPage_title(String page_title) {
		this.page_title = page_title;
	}

	public Boolean getCan_share() {
		return can_share;
	}

	public void setCan_share(Boolean can_share) {
		this.can_share = can_share;
	}

	public String getScence() {
		return scence;
	}

	public void setScence(String scence) {
		this.scence = scence;
	}

	public List<Map<String, String>> getCard_list() {
		return card_list;
	}

	public void setCard_list(List<Map<String, String>> card_list) {
		this.card_list = card_list;
	}

}
