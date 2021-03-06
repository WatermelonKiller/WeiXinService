/**  
 * @Title: BuutonService.java
 * @Package com.hz.wxBiz.menu.service
 * @Description: TODO
 * @author æç»§è¶?
 * @date 2015-6-18 ä¸å1:46:12
 * @version V1.0  
 */
package com.hd.wxBiz.menu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.pojo.menu.Button;
import com.core.pojo.menu.CommonButton;
import com.core.pojo.menu.ComplexButton;
import com.core.pojo.menu.Menu;
import com.core.pojo.menu.ViewButton;
import com.core.util.MenuUtil;
import com.hd.util.dao.CommonDao;
import com.hd.wxBiz.menu.bean.ButtonBean;
import com.hd.wxBiz.menu.sql.ButtonSql;

@Service
@Transactional
public class ButtonService {
	@Autowired
	private CommonDao cd;

	/**
	 * 
	 * @description:æ·»å 
	 * @time 2015-6-18
	 * @param bb
	 * @return
	 * @author Jc-Li
	 */
	public boolean add(ButtonBean bb) {
		bb.setId(UUID.randomUUID().toString().toUpperCase());
		return cd.insertinfor(bb);
	}

	/**
	 * 
	 * @description: ä¿®æ¹
	 * @time 2015-7-22
	 * @param bb
	 * @return
	 * @author Jc-Li
	 */
	public boolean update(ButtonBean bb) {
		return cd.updateinfor(bb);
	}

	/**
	 * 
	 * @description: éå½å?
	 * @time 2015-7-22
	 * @param id
	 * @param name
	 * @return
	 * @author Jc-Li
	 */
	public boolean renameButton(String id, String name) {
		ButtonBean bb = (ButtonBean) cd.hibernateGet(ButtonBean.class, id);
		bb.setName(name);
		return cd.updateinfor(bb);
	}

	/**
	 * 
	 * @description: å é¤
	 * @time 2015-7-22
	 * @param id
	 * @return
	 * @author Jc-Li
	 */
	public boolean delButton(String id) {
		return cd.DeleteSql("tab_core_menuButton", "id", "'" + id + "'");
	}

	/**
	 * 
	 * @description: æ ¹æ®ç¶èç¹idå é¤æ?å­èç?
	 * @time 2015-6-30
	 * @param id
	 * @return
	 * @author Jc-Li
	 */
	public boolean delSubButton(String id) {
		return cd.DeleteSql("tab_core_menuButton", "father_id", id);
	}

	/**
	 * 
	 * @description: æ¥è¯¢æ?ç¶èç?
	 * @time 2015-6-30
	 * @return
	 * @author Jc-Li
	 */
	public List<Map<String, String>> selFatherButton() {
		String fathers_sql = ButtonSql.SEL_ALL
				+ " and father_id='00000000-0000-0000-0000-000000000000' order by sequence asc";
		List<Map<String, String>> fathers = cd.queryListByPS(fathers_sql, null);
		return fathers;
	}

	/**
	 * 
	 * @description: æ?å­èç?
	 * @time 2015-6-30
	 * @return
	 * @author Jc-Li
	 */
	public List<Map<String, String>> selSubButton() {
		String sql = ButtonSql.SEL_ALL
				+ " and father_id <>'00000000-0000-0000-0000-000000000000' order by sequence asc";
		List<Map<String, String>> sub = cd.queryListByPS(sql, null);
		return sub;
	}

	/**
	 * 
	 * @description: çæèªå®ä¹èåè³æå¡å?
	 * @time 2015-6-19
	 * @return
	 * @author Jc-Li
	 */
	public boolean createMenu() {
		List<Map<String, String>> fathers = selFatherButton();
		List<Map<String, String>> sub = selSubButton();
		List<Button> cbList = new ArrayList<Button>();
		Menu menu = new Menu();
		// å¾ªç¯ç¶èç?
		for (int i = 0; i < fathers.size(); i++) {
			// å¤æ­ä¸?º§èåç±»å
			if (fathers.get(i).get("type") != null
					&& "father".equals(fathers.get(i).get("type"))) {
				ComplexButton cb = new ComplexButton();
				cb.setName(fathers.get(i).get("name"));
				// ä¸?º§èåç±»åä¸ºç¶èå å¾ªç¯å­èå?
				for (int j = 0; j < sub.size(); j++) {
					// å¦æç¶idä¸åä¸ä¸çº§èåid ç»§ç»­å¾ªç¯
					if (!sub.get(j).get("father_id")
							.equals(fathers.get(i).get("id"))) {
						continue;
					}
					String type = sub.get(j).get("type");
					// åå»ºå­èåéå?
					List<Button> sub_buttons = new ArrayList<Button>();
					// å¤æ­ç±»å
					if (null != type && type.equals("view")) {
						ViewButton button = new ViewButton();
						button.setName(sub.get(j).get("name"));
						button.setType(sub.get(j).get("type"));
						button.setUrl(sub.get(j).get("key_link"));
						sub_buttons.add(button);
					} else {
						CommonButton button = new CommonButton();
						button.setName(sub.get(j).get("name"));
						button.setType(sub.get(j).get("type"));
						button.setKey(sub.get(j).get("key_link"));
						sub_buttons.add(button);
					}
					// å°éåè½¬æ¢ä¸ºæ°ç»
					int size = sub_buttons.size();
					Button[] b = sub_buttons.toArray(new Button[size]);
					cb.setSub_button(b);
				}
				cbList.add(cb);
			} else if (fathers.get(i).get("type") != null
					&& "view".equals(fathers.get(i).get("type"))) {
				ViewButton vb = new ViewButton();
				vb.setName(fathers.get(i).get("name"));
				vb.setType(fathers.get(i).get("type"));
				vb.setUrl(fathers.get(i).get("key_link"));
				cbList.add(vb);
			} else {
				CommonButton comb = new CommonButton();
				comb.setName(fathers.get(i).get("name"));
				comb.setType(fathers.get(i).get("type"));
				comb.setKey(fathers.get(i).get("key_link"));
				cbList.add(comb);
			}
		}
		// å°ä¸çº§èåè½¬æ¢ä¸ºæ°ç»
		int size = cbList.size();
		Button[] cbs = cbList.toArray(new Button[size]);
		menu.setButton(cbs);
		int result = MenuUtil.createMenu(menu);
		if (result == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @description: æ¥è¯¢è¯¥ç¶éä¸æè¿å°ä¸ªå­èå?
	 * @time 2015-6-30
	 * @param father_id
	 * @return
	 * @author Jc-Li
	 */
	public int countNums(String father_id) {
		String sql = ButtonSql.count_nums;
		sql += " and father_id='" + father_id + "'";
		String num_str = cd.getOneValue(sql);
		return Integer.valueOf(num_str);
	}

	public ButtonBean getButton(String id) {
		if (id != null && !"".equals(id)) {
			return (ButtonBean) cd.hibernateGet(ButtonBean.class, id);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @description: æ¥è¯¢æªè®¾å®åè½çæé®
	 * @time 2015-6-30
	 * @return
	 * @author Jc-Li
	 */
	public String selMissButton() {
		return cd.getOneValue(ButtonSql.sel_miss_button);
	}

	/**
	 * 
	 * @description: ä»æå¡å¨è§£æèå
	 * @time 2015-6-30
	 * @param menu
	 * @return
	 * @author Jc-Li
	 */
	public boolean syncMenu(Menu menu) {
		boolean flag = false;
		int sequence_f = 1;
		Button[] fatherButton = menu.getButton();
		List<ButtonBean> buttonBeans = new ArrayList<ButtonBean>();
		for (Button b : fatherButton) {
			ButtonBean bean = new ButtonBean();
			bean.setFather_id("00000000-0000-0000-0000-000000000000");
			bean.setId(UUID.randomUUID().toString().toUpperCase());
			bean.setName(b.getName());
			bean.setSequence(sequence_f);
			sequence_f++;
			if (b.getClass().equals(ComplexButton.class)) {
				bean.setKey_link("");
				bean.setType("father");
				ComplexButton cb = (ComplexButton) b;
				Button[] bs = cb.getSub_button();
				for (int j = 0; j < bs.length; j++) {
					ButtonBean sub = new ButtonBean();
					sub.setFather_id(bean.getId());
					sub.setName(bs[j].getName());
					sub.setId(UUID.randomUUID().toString().toUpperCase());
					if (bs[j].getClass().equals(ViewButton.class)) {
						ViewButton vb = (ViewButton) bs[j];
						sub.setKey_link(vb.getUrl());
						sub.setType(vb.getType());
					} else {
						CommonButton comb = (CommonButton) bs[j];
						sub.setKey_link(comb.getKey());
						sub.setType(comb.getType());
					}
					sub.setSequence(j + 1);
					buttonBeans.add(sub);
				}
			} else {
				if (b.getClass().equals(ViewButton.class)) {
					ViewButton vb = (ViewButton) b;
					bean.setKey_link(vb.getUrl());
					bean.setType(vb.getType());
				} else {
					CommonButton comb = (CommonButton) b;
					bean.setKey_link(comb.getKey());
					bean.setType(comb.getType());
				}
			}
			buttonBeans.add(bean);
		}
		cd.executeSQL("delete from tab_core_menuButton", null);
		return cd.saveOrupdate(buttonBeans);
	}
}
