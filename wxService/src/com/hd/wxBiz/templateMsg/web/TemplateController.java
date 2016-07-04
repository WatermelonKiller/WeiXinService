/**  
 * @Title: TemplateController.java
 * @Package com.hz.wxBiz.templateMsg
 * @Description: TODO
 * @author 李继�?
 * @date 2015-7-15 上午10:24:48
 * @version V1.0  
 */
package com.hd.wxBiz.templateMsg.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hd.SystemAction.BaseAction;
import com.hd.wxBiz.templateMsg.bean.TemplateDataBean;
import com.hd.wxBiz.templateMsg.bean.TemplateInfo;
import com.hd.wxBiz.templateMsg.service.TemplateService;

@Controller
public class TemplateController extends BaseAction {
	@Autowired
	private TemplateService ts;

	@RequestMapping(value = "/templateMsg/templateMsgList")
	public ModelAndView templateView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		doAllSearchList(request, "list", ts.sel_templateMsg(), "jdbc", 10);
		view.setViewName("/templateMsg/templateMsgList");
		return view;
	}

	@RequestMapping(value = "/templateMsg/addTemplateForm")
	public ModelAndView addTemplateView() {
		ModelAndView view = new ModelAndView();
		view.setViewName("/templateMsg/form");
		return view;
	}

	/**
	 * 
	 * @description: 保存模板
	 * @time 2015-7-22
	 * @param request
	 * @param ti
	 * @return
	 * @author Jc-Li
	 */
	@RequestMapping(value = "/templateMsg/saveTemplate")
	@ResponseBody
	public String saveTemplate(HttpServletRequest request, TemplateInfo ti) {
		String[] data_names = request.getParameterValues("data_name");
		String[] colors = request.getParameterValues("color");
		String[] sequence = request.getParameterValues("sequence");
		TemplateDataBean[] tdb = new TemplateDataBean[data_names.length];
		for (int i = 0; i < tdb.length; i++) {
			tdb[i] = new TemplateDataBean();
			tdb[i].setColor(colors[i]);
			tdb[i].setData_name(data_names[i]);
			tdb[i].setSequence(Integer.valueOf(sequence[i]));
		}
		if (ts.saveTemplateMessage(ti, tdb)) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 
	 * @description: 删除模板
	 * @time 2015-7-22
	 * @param request
	 * @return
	 * @author Jc-Li
	 */
	@RequestMapping(value = "/templateMsg/delTemplate")
	@ResponseBody
	public String delTemplate(HttpServletRequest request) {
		String template_id = request.getParameter("template_id");
		if (ts.delTemplate(template_id)) {
			return "success";
		} else {
			return "error";
		}
	}

	@RequestMapping(value = "/templateMsg/detail")
	public ModelAndView getDetail(HttpServletRequest request) {
		String template_id = request.getParameter("template_id");
		ModelAndView view = new ModelAndView();
		view.addObject("template", ts.getTemplateInfoByTemplateId(template_id));
		view.addObject("tempData", ts.selTemplateDataById(template_id));
		view.setViewName("/templateMsg/template_detail");
		return view;
	}
}
