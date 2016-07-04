/**  
 * @Title: TemplateService.java
 * @Package com.hz.wxBiz.templateMsg.service
 * @Description: TODO
 * @author 李继�?
 * @date 2015-7-15 上午10:10:12
 * @version V1.0  
 */
package com.hd.wxBiz.templateMsg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.message.resp.templateMsg.Data;
import com.core.message.resp.templateMsg.TemplateData;
import com.core.message.resp.templateMsg.TemplateMsg;
import com.core.user.UserInfo;
import com.core.util.UserUtil;
import com.hd.util.dao.CommonDao;
import com.hd.wxBiz.templateMsg.bean.TemplateDataBean;
import com.hd.wxBiz.templateMsg.bean.TemplateInfo;
import com.hd.wxBiz.templateMsg.sql.TemplateSql;

@Service
@Transactional
public class TemplateService {
	@Autowired
	private CommonDao cd;

	/**
	 * 
	 * @description:保存模板
	 * @time 2015-7-15
	 * @param ti
	 * @param td
	 * @return
	 * @author Jc-Li
	 */
	public boolean saveTemplateMessage(TemplateInfo ti, TemplateDataBean[] td) {
		ti.setId(UUID.randomUUID().toString().toUpperCase());
		for (TemplateDataBean temp : td) {
			temp.setId(UUID.randomUUID().toString().toUpperCase());
			temp.setTemplate_id(ti.getTemplate_id());
		}
		return cd.insertinfor(ti) && cd.saveArray(td);
	}

	public String sel_templateMsg() {
		return TemplateSql.SEL_TEMPLATEMSG;
	}

	/**
	 * 
	 * @description: 根据模板id获取参数列表
	 * @time 2015-7-15
	 * @param template_id
	 * @return
	 * @author Jc-Li
	 */
	public List<Map<String, String>> selTemplateDataById(String template_id) {
		StringBuffer sql = new StringBuffer();
		sql.append(TemplateSql.SEL_DATA);
		sql.append(" and template_id='" + template_id + "'"
				+ "order by sequence asc ");
		return cd.queryListByPS(sql.toString(), null);
	}

	public boolean updateData(TemplateDataBean tdb) {
		return cd.updateinfor(tdb);
	}

	public boolean updateTemplateInfo(TemplateInfo info) {
		return cd.updateinfor(info);
	}

	/**
	 * 
	 * @description: 删除模板及参�?
	 * @time 2015-7-22
	 * @param template_id
	 * @return
	 * @author Jc-Li
	 */
	public boolean delTemplate(String template_id) {
		return cd.DeleteSql("tab_core_template_info", "template_id",
				template_id)
				&& cd.DeleteSql("tab_core_template_data", "template_id",
						template_id);
	}

	public boolean delTemplateData(String id) {
		return cd.DeleteSql("tab_core_template_data", "id", id);
	}

	/**
	 * 
	 * @description:获取模板基本信息By模板id
	 * @time 2015-7-15
	 * @param template_id
	 * @return
	 * @author Jc-Li
	 */
	public TemplateInfo getTemplateInfoByTemplateId(String template_id) {
		String tempBean_id = cd
				.getOneValue("select id from tab_core_template_info where template_id='"
						+ template_id + "'");
		return (TemplateInfo) cd.hibernateGet(TemplateInfo.class, tempBean_id);
	}

	/**
	 * 
	 * @description: 模板消息测试方法，获取用户信�?
	 * @time 2015-7-15
	 * @param openId
	 * @param template_id
	 * @return 返回成品模板信息
	 * @author Jc-Li
	 */
	public TemplateMsg replyUserInfo(String openId) {
		String template_id = "GvbYNLWRaFu6ZDK65BUYeQXs0FUfFAsL0fxgaE22itE";
		// 获取用户信息
		UserInfo ui = UserUtil.getUserDetailInfo(openId);
		// 获取参数列表
		List<Map<String, String>> dataList = selTemplateDataById(template_id);
		// 获取模板基本信息
		TemplateInfo ti = getTemplateInfoByTemplateId(template_id);
		// 将参数信息及值封装进模板信息�?
		List<TemplateData> tds = new ArrayList<TemplateData>();
		for (Map<String, String> map : dataList) {
			TemplateData td = new TemplateData();
			td.setData_name(map.get("data_name"));
			Data data = new Data();
			data.setColor(map.get("color"));
			// 判断参数放入对应�?
			if (td.getData_name().equals("first")) {
				data.setValue(ui.getNickname());
			} else if (td.getData_name().equals("keyword1")) {
				data.setValue(ui.getSex() == 1 ? "��" : "Ů");
			} else if (td.getData_name().equals("keyword2")) {
				data.setValue(ui.getCity());
			} else if (td.getData_name().equals("remark")) {
				data.setValue("测试成功");
			}
			td.setData(data);
			tds.add(td);
		}
		// 将参数list转换为参数数�?
		int tds_size = tds.size();
		TemplateData[] td_array = tds.toArray(new TemplateData[tds_size]);
		// 封装模板信息
		TemplateMsg tm = new TemplateMsg();
		tm.setTemplate_id(template_id);
		tm.setTopcolor(ti.getTop_color());
		tm.setUrl(ti.getTemplate_url());
		tm.setTouser(openId);
		tm.setData(td_array);
		System.out.println(tm.toString() + "---------------------------------");
		return tm;
	}
}
