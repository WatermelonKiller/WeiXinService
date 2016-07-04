/**  
 * @Title: KeyWordsController.java
 * @Package com.hz.wxBiz.keywordsManager
 * @Description: TODO
 * @author 李继�?
 * @date 2015-7-22 下午4:35:46
 * @version V1.0  
 */
package com.hd.wxBiz.keywordsManager.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hd.SystemAction.BaseAction;
import com.hd.wxBiz.keywordsManager.bean.KeyWords;
import com.hd.wxBiz.keywordsManager.service.KeyWordsService;

@Controller
public class KeyWordsController extends BaseAction {
	@Autowired
	private KeyWordsService kws;

	@RequestMapping(value = "/keyword/list")
	public ModelAndView keyWordList(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String keyword = request.getParameter("keyword");
		String type = request.getParameter("type");
		String in_use = request.getParameter("in_use");
		doAllSearchList(request, "list",
				kws.selKeyWords(keyword, type, in_use), "jdbc", 10);
		view.addObject("keyword", keyword);
		view.addObject("type", type);
		view.addObject("in_use", in_use);
		view.setViewName("/keyword/keywordList");
		return view;
	}

	@RequestMapping(value = "/keyword/form")
	public ModelAndView updateForm(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String act = request.getParameter("act");
		if (null != act && "upd".equals(act)) {
			String id = request.getParameter("id");
			view.addObject("kw", kws.getOne(id));
		}
		view.addObject("act", act);
		view.setViewName("/keyword/form");
		return view;
	}

	@RequestMapping(value = "/keyword/save")
	@ResponseBody
	public String saveKeyWord(KeyWords kw, HttpServletRequest request) {
		String user_name = "";
		Map<String, String> map = (Map<String, String>) request.getSession()
				.getAttribute(USER_CONTEXT);
		if (map != null) {
			user_name = map.get("user_name");
		}
		kw.setCreate_user(user_name);
		if (kws.saveKeyWord(kw)) {
			return "success";
		} else {
			return "error";
		}
	}

	@RequestMapping(value = "/keyword/upd")
	@ResponseBody
	public String updKeyWord(KeyWords kw) {
		if (kws.updateKeyWord(kw)) {
			return "success";
		} else {
			return "error";
		}
	}

	@RequestMapping(value = "/keyword/del")
	@ResponseBody
	public String delKeyWord(HttpServletRequest request) {
		String id = request.getParameter("id");
		if (kws.delKeyWord(id)) {
			return "success";
		} else {
			return "error";
		}
	}
}
