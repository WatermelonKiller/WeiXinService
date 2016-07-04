package com.hd.wxBiz.Core.CoreWeb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.core.util.AppInfoUtil;
import com.core.util.SecretUtil;
import com.core.util.SignUtil;
import com.hd.wxBiz.Core.CoreService.CoreService;

/*******************************************************************************
 * 
 * Copyright(c) 2010 by 鍝堝皵婊ㄥ崕娉芥暟鐮佺鎶�湁闄愬叕鍙� All rights reserved.
 * 
 ******************************************************************************* 
 * File Name : com.hz.wxBiz.Core.CoreWeb.CoreWebController.java Description(璇存槑)
 * : --------------------------------------------------------------------------
 * --- No. Date Revised by Description 0 2015-7-16 Edwin Created
 ******************************************************************************/
@Controller
public class CoreWebController {
	@Autowired
	private CoreService cs;

	@RequestMapping(value = "/CoreServlet", method = RequestMethod.POST)
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		cs.processRequest(request, response);
	}

	@RequestMapping(value = "/CoreServlet", method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	/**
	 * 
	 * @Description:回应服务器事件
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-6
	 */
	public static void postMessage(HttpServletResponse response,
			String respMessage) {
		String encrypt_type = AppInfoUtil.getEncrypt_type();
		if (null != encrypt_type && encrypt_type.equals("aes")) {
			if (null != respMessage && !"".equals(respMessage)) {
				respMessage = SecretUtil.encryptMsg(respMessage);
			}
		}
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(respMessage);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
