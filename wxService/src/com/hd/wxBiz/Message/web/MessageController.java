package com.hd.wxBiz.Message.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hd.SystemAction.BaseAction;
import com.hd.wxBiz.Message.service.MessageService;
import com.hd.wxBiz.Message.sql.MessageSql;

/*******************************************************************************
 * 
 * Copyright(c) 2010 by 哈尔滨华泽数码科�?��限公�? All rights reserved.
 * 
 ******************************************************************************* 
 * File Name : com.hz.wxBiz.message.web.MessageController.java Description(说明)
 * : --------------------------------------------------------------------------
 * --- No. Date Revised by Description 0 2015-6-25 Edwin Created
 ******************************************************************************/
@Controller
@RequestMapping(value = "HZmessage")
public class MessageController extends BaseAction {

	@Autowired
	private MessageService messageService;

	/**
	 * @author Edwin
	 * @param HttpServletRequest
	 *            HttpSession
	 * @return The jsp of message control page in back
	 * @time 2015-6-29
	 */
	@RequestMapping(value = "controlPage")
	public ModelAndView messageControlPage(HttpServletRequest request,
			HttpSession session) {
		String sql = MessageSql.MAIN_MESSAGE_SEL;
		// 1.前台分页显示�?��消息数据�?
		doAllSearchList(request, "list", sql.toString(), "jdbc", 15);
		ModelAndView mav = new ModelAndView("/messageControl/contorlPage");
		return mav;
	}

}
