/**  
 * @Title: CoreService.java
 * @Package com.hz.core.service
 * @Description: TODO
 * @author 李继超
 * @date 2015-6-5 下午4:01:05
 * @version V1.0  
 */
package com.hd.wxBiz.Core.CoreService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.core.message.resp.TextMessage;
import com.core.util.MessageUtil;
import com.hd.util.DateUtil;
import com.hd.wxBiz.Core.CoreWeb.CoreWebController;
import com.hd.wxBiz.Core.messageService.EventMessageService;
import com.hd.wxBiz.Core.messageService.ImageMessageService;
import com.hd.wxBiz.Core.messageService.LinkMessageService;
import com.hd.wxBiz.Core.messageService.LocationMessageService;
import com.hd.wxBiz.Core.messageService.ShortVideoMessageService;
import com.hd.wxBiz.Core.messageService.TextMessageService;
import com.hd.wxBiz.Core.messageService.VideoMessageService;
import com.hd.wxBiz.Core.messageService.VoiceMessageService;

@Service
public class CoreService {
	/**
	 * 
	 * @Description:解析消息，并 回应微信服务器
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-6
	 */
	@SuppressWarnings("unused")
	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> requestMap = new HashMap<String, String>();
		try {
			requestMap = MessageUtil.parseXml(request);
			String msgType = requestMap.get("MsgType");
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// --------文本消息处理
				TextMessageService.primary(requestMap, response);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				// ----图片类型消息处理
				ImageMessageService.primary(requestMap, response);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				// ---地理位置消息处理
				LocationMessageService.primary(requestMap, response);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				// ----链接消息处理
				LinkMessageService.primary(requestMap, response);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				// ----语音消息处理
				VoiceMessageService.primary(requestMap, response);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE__EVENT)) {
				// ---事件处理
				EventMessageService.primary(requestMap, response);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORT_VIDEO)) {
				// 小视频
				ShortVideoMessageService.primary(requestMap, response);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				// 视频
				VideoMessageService.primary(requestMap, response);
			} else {
				CoreWebController.postMessage(response, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			CoreWebController.postMessage(response, "");
		}

	}

	/**
	 * 
	 * @Description:默认无业务时处理
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-6
	 */
	public static void defaultDeal(Map<String, String> requestMap,
			HttpServletResponse response) {
		TextMessage textMessage = new TextMessage();
		textMessage.setContent("暂不支持此业务");
		textMessage.setCreateTime(DateUtil.longtime());
		textMessage.setFromUserName(requestMap.get("ToUserName"));
		textMessage.setFuncFlag(0);
		textMessage.setToUserName(requestMap.get("FromUserName"));
		textMessage.setMsgType("text");
		CoreWebController.postMessage(response,
				MessageUtil.textMessageToXml(textMessage));
	}
}
