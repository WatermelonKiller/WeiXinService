package com.hd.wxBiz.Core.messageService;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.util.DateFormatUtil;
import com.hd.shareSubscribe.bean.ShareSubscribe;
import com.hd.shareSubscribe.service.ShareSubscribeService;
import com.hd.util.DateUtil;
import com.hd.wxBiz.Core.CoreService.CoreService;
import com.hd.wxBiz.Core.CoreWeb.CoreWebController;
import com.hd.wxBiz.card.bean.UserPayFromCardInfo;
import com.hd.wxBiz.card.service.CardService;
import com.hd.wxBiz.store.service.StoreService;

@Service
public class EventMessageService {
	@Autowired
	private ShareSubscribeService shareSubService;

	@Autowired
	private StoreService storeService;
	@Autowired
	private CardService cardService;

	public static void primary(Map<String, String> requestMap,
			HttpServletResponse response) {
		EventMessageService eventMessageService = new EventMessageService();
		String event = requestMap.get("Event");
		if (event == null) {
			event = "";
		}
		if (event.equals("subscribe")) {
			// 扫描关注
			eventMessageService.subscribeMessage(requestMap, response);
		} else if (event.equals("poi_check_notify")) {
			// 门店审核消息推送
			eventMessageService.auditingStore(requestMap, response);
		} else if (event.equals("user_pay_from_pay_cell")) {
			eventMessageService.userPayFromCard(requestMap, response);
		} else {
			CoreService.defaultDeal(requestMap, response);
		}
	}

	/**
	 * 
	 * @Description:关注事件处理
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-6
	 */
	private void subscribeMessage(Map<String, String> requestMap,
			HttpServletResponse response) {
		if (requestMap.containsValue("EventKey")) {
			// 扫描关注
			String fromUserName = requestMap.get("FromUserName");
			String info = requestMap.get("EventKey");
			System.out.println();
			ShareSubscribe ss = new ShareSubscribe();
			ss.setTo_open_id(fromUserName);
			ss.setFrom_open_id(info.replace("qrscene_from_open_id:", ""));
			ss.setId(UUID.randomUUID().toString().toUpperCase());
			ss.setCreate_time(DateUtil.getDateTime());
			shareSubService.saveShareSubcribe(ss);
			CoreService.defaultDeal(requestMap, response);
		}

	}

	/**
	 * 
	 * @Description:门店审核事件
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-11
	 */
	private void auditingStore(Map<String, String> requestMap,
			HttpServletResponse response) {
		String sid = requestMap.get("UniqId");
		String poi_id = requestMap.get("PoiId");
		String result = requestMap.get("Result");
		storeService.auditingStore(sid, poi_id, result);
		CoreWebController.postMessage(response, "");
	}

	/**
	 * 
	 * @Description:买单事件推送
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-17
	 */
	public void userPayFromCard(Map<String, String> map,
			HttpServletResponse response) {
		UserPayFromCardInfo payInfo = new UserPayFromCardInfo();
		payInfo.setCard_id(map.get("CardId"));
		payInfo.setCreateTime(DateFormatUtil.formatTimeFromWx(map
				.get("CreateTime")));
		payInfo.setFee(Integer.valueOf(map.get("Fee")));
		payInfo.setFromUserName(map.get("FromUserName"));
		payInfo.setLocationName(map.get("LocationName"));
		payInfo.setOriginalFee(Integer.valueOf(map.get("OriginalFee")));
		payInfo.setTransld(map.get("TransId"));
		payInfo.setUserCardCode(map.get("UserCardCode"));
		cardService.savePayFromCard(payInfo);
		CoreWebController.postMessage(response, "");
	}
}
