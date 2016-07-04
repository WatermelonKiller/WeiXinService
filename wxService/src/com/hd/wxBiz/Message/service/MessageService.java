package com.hd.wxBiz.Message.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.util.dao.CommonDao;
import com.hd.wxBiz.Message.bean.MainMessage;
import com.hd.wxBiz.Message.bean.MainMessageDetail;

/*******************************************************************************
 * 
 * Copyright(c) 2010 by 哈尔滨华泽数码科�?��限公�? All rights reserved.
 * 
 ******************************************************************************* 
 * File Name : com.hz.wxBiz.message.service.MessageService.java Description(说明)
 * : --------------------------------------------------------------------------
 * --- No. Date Revised by Description 0 2015-6-25 Edwin Created
 ******************************************************************************/
@Service
@Transactional
public class MessageService {

	@Autowired
	private CommonDao cd;

	public String messageSave(MainMessage mainMessage,
			MainMessageDetail messageDetail) {

		String result = "";
		String mainId = UUID.randomUUID().toString();
		mainMessage.setId(mainId);
		boolean rsMain = cd.insertinfor(mainMessage);
		if (true == rsMain) {
			messageDetail.setId(UUID.randomUUID().toString());
			messageDetail.setMainId(mainId);
			boolean rsDetail = cd.insertinfor(messageDetail);
			if (true == rsDetail) {
				result = "success";
			} else {
				result = "fail";
			}
		} else {
			result = "fail";
		}
		return result;
	}

}
