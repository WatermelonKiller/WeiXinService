/**  
 * @Title: ButtonController.java
 * @Package com.hz.wxBiz.menu.web
 * @Description: TODO
 * @author 李继�?
 * @date 2015-6-19 上午9:41:22
 * @version V1.0  
 */
package com.hd.wxBiz.menu.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.core.pojo.menu.Menu;
import com.core.util.MenuUtil;
import com.hd.SystemAction.BaseAction;
import com.hd.wxBiz.menu.bean.ButtonBean;
import com.hd.wxBiz.menu.service.ButtonService;
import com.hd.wxBiz.menu.service.EventService;

@Controller
public class ButtonController extends BaseAction {
	@Autowired
	private ButtonService bs;
	@Autowired
	private EventService es;

	/**
	 * 
	 * @description: 菜单详情�?
	 * @time 2015-7-22
	 * @return
	 * @author Jc-Li
	 */
	@RequestMapping(value = "/weixin/menulist")
	public ModelAndView menuView() {
		ModelAndView view = new ModelAndView();
		view.addObject("fathers", bs.selFatherButton());
		view.addObject("sub_button", bs.selSubButton());
		view.setViewName("/menuManager/mainview");
		return view;
	}

	/**
	 * 
	 * @description: 添加按钮�?
	 * @time 2015-7-22
	 * @param request
	 * @return
	 * @author Jc-Li
	 */
	@RequestMapping(value = "/weixin/addButton")
	public ModelAndView addNewButton(HttpServletRequest request) {
		String father_id = request.getParameter("father_id");
		int num = bs.countNums(father_id);
		int last_num = 0;
		if (father_id.equals("00000000-0000-0000-0000-000000000000")) {
			last_num = 3 - num;
		} else {
			last_num = 5 - num;
		}
		ModelAndView view = new ModelAndView();
		view.addObject("last_num", last_num);
		view.addObject("father_id", father_id);
		view.addObject("sequence", num + 1);
		view.setViewName("/menuManager/addButton");
		return view;
	}

	/**
	 * 
	 * @description:添加按钮
	 * @time 2015-7-22
	 * @param bb
	 * @return
	 * @author Jc-Li
	 */
	@RequestMapping(value = "/weixin/doAddButton")
	@ResponseBody
	public String doAddButton(ButtonBean bb) {
		if (bb.getFather_id() != "00000000-0000-0000-0000-000000000000") {
			ButtonBean fb = bs.getButton(bb.getFather_id());
			fb.setKey_link("");
			fb.setType("father");
			bs.update(fb);
		}
		if (bs.add(bb)) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 
	 * @description: 按钮信息�?
	 * @time 2015-7-22
	 * @param request
	 * @return
	 * @author Jc-Li
	 */
	@RequestMapping(value = "/weixin/rightMenu")
	public ModelAndView mainRight(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String id = request.getParameter("id");
		if (id != null && !"".equals(id)) {
			ButtonBean bean = bs.getButton(id);
			int num = bs.countNums(bean.getId());
			int last_num = 0;
			last_num = 5 - num;
			view.addObject("eventList", es.getEventInfo());
			view.addObject("button", bean);
			view.addObject("nums", last_num);
		}

		view.setViewName("/menuManager/rightview");
		return view;
	}

	/**
	 * 
	 * @description: 添加按钮功能
	 * @time 2015-7-22
	 * @param bb
	 * @param request
	 * @return
	 * @author Jc-Li
	 */
	@RequestMapping(value = "/weixin/addButtonAction")
	@ResponseBody
	public String addButtonAction(ButtonBean bb, HttpServletRequest request) {
		boolean flag = bs.update(bb);
		if (flag) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 
	 * @description: 重命名按钮页�?
	 * @time 2015-7-22
	 * @param request
	 * @return
	 * @author Jc-Li
	 */
	@RequestMapping(value = "/weixin/renameButtonPage")
	public ModelAndView renameButtonPage(HttpServletRequest request) {
		String id = request.getParameter("id");
		ModelAndView view = new ModelAndView();
		view.addObject("button", bs.getButton(id));
		view.setViewName("/menuManager/renameButton");
		return view;
	}

	/**
	 * 
	 * @description: 重命名按钮操�?
	 * @time 2015-7-22
	 * @param request
	 * @return
	 * @author Jc-Li
	 */
	@RequestMapping(value = "/weixin/renameButton")
	@ResponseBody
	public String rename(HttpServletRequest request) {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		if (bs.renameButton(id, name)) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 
	 * @description: 删除按钮
	 * @time 2015-7-22
	 * @param request
	 * @return
	 * @author Jc-Li
	 */
	@RequestMapping(value = "/weixin/delButton")
	@ResponseBody
	public String delButton(HttpServletRequest request) {
		boolean flag = false;
		String id = request.getParameter("id");
		String father_id = request.getParameter("father_id");
		if (father_id != null
				&& "00000000-0000-0000-0000-000000000000".equals(father_id)) {
			flag = bs.delButton(id) && bs.delSubButton(id);
		} else {
			flag = bs.delButton(id);
		}
		if (flag) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 
	 * @description: 找到第一个没设置内容的按�?
	 * @time 2015-6-29
	 * @return
	 * @author Jc-Li
	 */
	@RequestMapping(value = "/weixin/selMissButton")
	@ResponseBody
	public String selMissButton() {
		return bs.selMissButton();
	}

	/**
	 * 
	 * @description: 发布
	 * @time 2015-6-30
	 * @return
	 * @author Jc-Li
	 */
	@RequestMapping(value = "/weixin/releaseMenu")
	@ResponseBody
	public String releaseMenu() {
		if (bs.createMenu()) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 
	 * @description: 从服务器同步
	 * @time 2015-6-30
	 * @return
	 * @author Jc-Li
	 */
	@RequestMapping(value = "/weixin/syncMenu")
	@ResponseBody
	public String syncMenu() {
		Menu menu = MenuUtil.getMenu_info();
		if (bs.syncMenu(menu)) {
			return "success";
		} else {
			return "error";
		}
	}
}
