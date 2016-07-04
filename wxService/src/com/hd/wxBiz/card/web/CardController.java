package com.hd.wxBiz.card.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hd.SystemAction.BaseAction;
import com.hd.wxBiz.card.service.CardService;
import com.hd.wxBiz.card.sql.CardSql;

@Controller
@RequestMapping(value="/cardJ")
public class CardController extends BaseAction {
	
	@Autowired
	private CardService cs;
	
	/**
	 * 
	 * @Description:卡卷列表主页跳转
	 * @Author:Edwin
	 * @param:
	 * @Date:2016-1-5
	 */
	@RequestMapping(value="homeList")
	public ModelAndView cardJPage(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/card/homeList");
		String sql = CardSql.CARD_INFO_SEL;
		doAllSearchList(request, "list",sql, "jdbc", 15);
		return mav;
	}

}
