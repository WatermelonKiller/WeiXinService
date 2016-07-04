package com.hd.wxBiz.Core.messageService;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.hd.wxBiz.Core.CoreService.CoreService;

public class TextMessageService {
	public static void primary(Map<String, String> requestMap,
			HttpServletResponse response) {
		CoreService.defaultDeal(requestMap, response);
	}

}
