package com.hd.shareSubscribe.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.core.util.AppInfoUtil;
import com.core.util.JsUtil;
import com.core.util.PastUtil;
import com.core.util.PropertiesUtil;
import com.core.util.UserUtil;
import com.hd.SystemAction.BaseAction;
import com.hd.shareSubscribe.service.ShareSubscribeService;

@Controller
public class ShareSubscribeController extends BaseAction {
	@Autowired
	private ShareSubscribeService sss;

	/**
	 * 
	 * @Description:测试分享基础页面
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-2
	 */
	@RequestMapping(value = "/test/sharePage")
	public ModelAndView testShareLink(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		// 添加js验证
		Map<String, String> map = PastUtil.getParam(AppInfoUtil.getAppid(),
				AppInfoUtil.getAppsercret(), request);
		// 获取页面accessToken
		String json = JsUtil.getPageAccessToken(request);
		JSONObject jsonObject = JSONObject.fromObject(json);
		// 获取用户基本信息
		JSONObject result = JSONObject.fromObject(UserUtil
				.getUserInfo(jsonObject));
		String open_id = result.getString("openid");
		String state = request.getParameter("state");
		// 如果来自分享，拼装二维码ftp地址
		if (null != state && !state.equals("STATE")) {
			String qr_code_path = "";
			PropertiesUtil prop = new PropertiesUtil();
			qr_code_path += "http://" + prop.getProperties("ftp.outip");
			String outPort = prop.getProperties("ftp.outport");
			if (!"80".equals(outPort)) {
				qr_code_path += ":" + outPort;
			}
			qr_code_path += sss.getCodePathByOpenId(state);
			view.addObject("qr_code_path", qr_code_path);
		}
		view.addObject("open_id", open_id);
		view.addObject("map", map);
		view.setViewName("/test/sharePage");
		return view;
	}

	/**
	 * 
	 * @Description:分享给朋友或朋友圈是生成个人专属二维码
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-2
	 */
	@RequestMapping(value = "/qrcode/produceQrCode")
	@ResponseBody
	public String produceQrCode(HttpServletRequest request) throws IOException {
		String open_id = request.getParameter("open_id");
		if (sss.savePrivateQrCode(open_id)) {
			return "success";
		} else {
			return "error";
		}
	}
}
