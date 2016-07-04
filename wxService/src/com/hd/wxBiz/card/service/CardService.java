package com.hd.wxBiz.card.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.pojo.card.CashCard;
import com.core.pojo.card.CouponCard;
import com.core.pojo.card.DiscountCard;
import com.core.pojo.card.GiftCard;
import com.core.pojo.card.GroupOnCard;
import com.core.pojo.card.tool.BaseInfo;
import com.core.pojo.card.tool.DateInfo_FixTerm;
import com.core.pojo.card.tool.DateInfo_TimeRanger;
import com.core.pojo.card.tool.Sku;
import com.core.util.CardUtil;
import com.hd.util.dao.CommonDao;
import com.hd.wxBiz.card.bean.CardInfo;
import com.hd.wxBiz.card.bean.UserPayFromCardInfo;

@Service
@Transactional
public class CardService {

	@Autowired
	private CommonDao cd;

	/**
	 * 
	 * @Description:保存
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-17
	 */
	public boolean savePayFromCard(UserPayFromCardInfo upfci) {
		upfci.setId(UUID.randomUUID().toString().toUpperCase());
		return cd.insertinfor(upfci);
	}

	public boolean createCard(CardInfo cardInfo) {
		JSONObject json = new JSONObject();
		JSONObject cardJson = new JSONObject();
		cardJson.put("card_type", cardInfo.getCard_type());
		BaseInfo baseInfo = new BaseInfo();
		// 必填
		baseInfo.setBrand_name(cardInfo.getBrand_name());
		baseInfo.setCode_type(cardInfo.getCode_type());
		baseInfo.setColor(cardInfo.getColor());
		// 固定日期区间
		if (cardInfo.getType() != null
				&& "DATE_TYPE_FIX_TIME_RANGE".equals(cardInfo.getType())) {
			DateInfo_TimeRanger dateInfo_timeRanger = new DateInfo_TimeRanger();
			dateInfo_timeRanger.setBegin_timestamp(cardInfo
					.getBegin_timestamp());
			dateInfo_timeRanger.setEnd_timestamp(cardInfo.getEnd_timestamp());
			dateInfo_timeRanger.setType(cardInfo.getType());
			baseInfo.setDate_info(dateInfo_timeRanger);
		}
		// 固定时长（自领取后按天算)
		if (cardInfo.getType() != null
				&& "DATE_TYPE_FIX_TERM".equals(cardInfo.getType())) {
			DateInfo_FixTerm dateInfo_fixTerm = new DateInfo_FixTerm();
			dateInfo_fixTerm.setEnd_timestamp(cardInfo.getEnd_timestamp());
			dateInfo_fixTerm
					.setFixed_begin_term(cardInfo.getFixed_begin_term());
			dateInfo_fixTerm.setFixed_term(cardInfo.getFixed_term());
			dateInfo_fixTerm.setType(cardInfo.getType());
			baseInfo.setDate_info(dateInfo_fixTerm);
		}
		baseInfo.setDescription(cardInfo.getDescription());
		baseInfo.setLogo_url(cardInfo.getLogo_url());
		baseInfo.setNotice(cardInfo.getNotice());
		// 商品信息
		Sku sku = new Sku();
		sku.setQuantity(cardInfo.getQuantity());
		baseInfo.setSku(sku);
		baseInfo.setSub_title(cardInfo.getSub_title());
		baseInfo.setTitle(cardInfo.getTitle());
		// 非必填
		baseInfo.setUse_custom_code(cardInfo.getUse_custom_code());
		baseInfo.setBind_openid(cardInfo.getBind_openid());
		baseInfo.setService_phone(cardInfo.getService_phone());
		// 门店id数组
		if (cardInfo.getLocation_id_list() != null
				& !"".equals(cardInfo.getLocation_id_list())) {
			String[] location_ids = cardInfo.getLocation_id_list().split(",");
			List<Integer> location_id_list = new ArrayList<Integer>();
			for (String temp : location_ids) {
				location_id_list.add(Integer.valueOf(temp));
			}
			baseInfo.setLocation_id_list((Integer[]) location_id_list
					.toArray(new Integer[location_id_list.size()]));
		}
		baseInfo.setSource(cardInfo.getSource());
		baseInfo.setCenter_sub_title(cardInfo.getCenter_sub_title());
		baseInfo.setCenter_title(cardInfo.getCenter_title());
		baseInfo.setCenter_url(cardInfo.getCenter_url());
		baseInfo.setPromotion_url(cardInfo.getPromotion_url());
		baseInfo.setPromotion_url_name(cardInfo.getPromotion_url_name());
		baseInfo.setPromotion_url_sub_title(cardInfo
				.getPromotion_url_sub_title());
		baseInfo.setCustom_url(cardInfo.getCustom_url());
		baseInfo.setCustom_url_name(cardInfo.getCustom_url_name());
		baseInfo.setCustom_url_sub_title(cardInfo.getCustom_url_sub_title());
		baseInfo.setCan_give_friend(cardInfo.getCan_give_friend());
		baseInfo.setCan_share(cardInfo.getCan_share());
		baseInfo.setGet_limit(cardInfo.getGet_limit());
		/**
		 * 滤掉json中value为null的键值对。
		 */
		JsonConfig jsonConfig = new JsonConfig();
		PropertyFilter filter = new PropertyFilter() {
			public boolean apply(Object object, String fieldName,
					Object fieldValue) {
				return null == fieldValue;
			}

			public boolean apply1(Object object, String filedName,
					Object filedValue) {
				return "".equals(filedValue);
			}
		};
		jsonConfig.setJsonPropertyFilter(filter);
		// 团购券
		if (cardInfo.getCard_type() != null
				&& "GROUPON".equals(cardInfo.getCard_type())) {
			GroupOnCard goc = new GroupOnCard();
			goc.setDeal_detail(cardInfo.getDeal_detail());
			goc.setBase_info(baseInfo);
			cardJson.put("GROUPON", JSONObject.fromObject(goc, jsonConfig));
		}
		// 代金券
		if (cardInfo.getCard_type() != null
				&& "CASH".equals(cardInfo.getCard_type())) {
			CashCard cc = new CashCard();
			cc.setBase_info(baseInfo);
			cc.setLeast_cost(cardInfo.getLeast_cost());
			cc.setReduce_cost(cardInfo.getReduce_cost());
			cardJson.put("CASH", JSONObject.fromObject(cc, jsonConfig));
		}
		// 折扣券
		if (cardInfo.getCard_type() != null
				&& "DISCOUNT".equals(cardInfo.getCard_type())) {
			DiscountCard dc = new DiscountCard();
			dc.setBase_info(baseInfo);
			dc.setDiscount(cardInfo.getDiscount());
			cardJson.put("DISCOUNT", JSONObject.fromObject(dc, jsonConfig));
		}
		// 礼品券
		if (cardInfo.getCard_type() != null
				&& "GIFT".equals(cardInfo.getCard_type())) {
			GiftCard gc = new GiftCard();
			gc.setBase_info(baseInfo);
			gc.setGift(cardInfo.getGift());
			cardJson.put("GIFT", JSONObject.fromObject(gc, jsonConfig));
		}
		// 优惠券
		if (cardInfo.getCard_type() != null
				&& "GENERAL_COUPON".equals(cardInfo.getCard_type())) {
			CouponCard coc = new CouponCard();
			coc.setDefault_detail(cardInfo.getDefault_detail());
			coc.setBase_info(baseInfo);
			cardJson.put("GENERAL_COUPON",
					JSONObject.fromObject(coc, jsonConfig));
		}
		json.put("card", cardJson);
		String card_id = CardUtil.createCard(json);
		if (card_id != null && !"".equals(card_id)) {
			cardInfo.setCard_id(card_id);
			cardInfo.setId(UUID.randomUUID().toString().toUpperCase());
			return cd.insertinfor(cardInfo);
		} else {
			return false;
		}
	}
}
