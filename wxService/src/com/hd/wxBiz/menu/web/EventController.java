/**  
 * @Title: EventAction.java
 * @Package com.hz.wxBiz.menu.web
 * @Description: TODO
 * @author 李继�?
 * @date 2015-6-27 上午9:28:08
 * @version V1.0  
 */
package com.hd.wxBiz.menu.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hd.SystemAction.BaseAction;
import com.hd.wxBiz.menu.bean.Event;
import com.hd.wxBiz.menu.service.EventService;

@Controller
public class EventController extends BaseAction {
	@Autowired
	private EventService es;

	@RequestMapping(value = "/weixin/eventList")
	public ModelAndView eventList(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		doAllSearchList(request, "list", es.selAllSql(), "jdbc", 10);
		view.setViewName("/event/eventList");
		return view;
	}

	@RequestMapping(value = "/weixin/eventForm")
	public ModelAndView eventForm(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/event/form");
		return view;
	}

	@RequestMapping(value = "/weixin/saveEvent")
	@ResponseBody
	public String saveEvent(Event event, HttpServletRequest request) {
		String user_name = "";
		Map<String, String> map = (Map<String, String>) request.getSession()
				.getAttribute(USER_CONTEXT);
		if (map != null) {
			user_name = map.get("user_name");
		}
		event.setCreate_user(user_name);
		if (es.addNewEvent(event)) {
			return "success";
		} else {
			return "error";
		}
	}

	@RequestMapping(value = "/weixin/delEvent")
	@ResponseBody
	public String delEvent(HttpServletRequest request) {
		String id = request.getParameter("id");
		if (es.delEvent(id)) {
			return "success";
		} else {
			return "error";
		}
	}
}
