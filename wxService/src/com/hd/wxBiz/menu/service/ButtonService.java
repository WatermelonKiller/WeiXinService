/**  
 * @Title: BuutonService.java
 * @Package com.hz.wxBiz.menu.service
 * @Description: TODO
 * @author 李继�?
 * @date 2015-6-18 下午1:46:12
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
	 * @description:添加
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
	 * @description: 修改
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
	 * @description: 重命�?
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
	 * @description: 删除
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
	 * @description: 根据父节点id删除�?��子节�?
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
	 * @description: 查询�?��父节�?
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
	 * @description: �?��子节�?
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
	 * @description: 生成自定义菜单至服务�?
	 * @time 2015-6-19
	 * @return
	 * @author Jc-Li
	 */
	public boolean createMenu() {
		List<Map<String, String>> fathers = selFatherButton();
		List<Map<String, String>> sub = selSubButton();
		List<Button> cbList = new ArrayList<Button>();
		Menu menu = new Menu();
		// 循环父节�?
		for (int i = 0; i < fathers.size(); i++) {
			// 判断�?��菜单类型
			if (fathers.get(i).get("type") != null
					&& "father".equals(fathers.get(i).get("type"))) {
				ComplexButton cb = new ComplexButton();
				cb.setName(fathers.get(i).get("name"));
				// �?��菜单类型为父菜单 循环子菜�?
				for (int j = 0; j < sub.size(); j++) {
					// 如果父id不同与一级菜单id 继续循环
					if (!sub.get(j).get("father_id")
							.equals(fathers.get(i).get("id"))) {
						continue;
					}
					String type = sub.get(j).get("type");
					// 创建子菜单集�?
					List<Button> sub_buttons = new ArrayList<Button>();
					// 判断类型
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
					// 将集合转换为数组
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
		// 将一级菜单转换为数组
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
	 * @description: 查询该父集下有过少个子菜�?
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
	 * @description: 查询未设定功能的按钮
	 * @time 2015-6-30
	 * @return
	 * @author Jc-Li
	 */
	public String selMissButton() {
		return cd.getOneValue(ButtonSql.sel_miss_button);
	}

	/**
	 * 
	 * @description: 从服务器解析菜单
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
