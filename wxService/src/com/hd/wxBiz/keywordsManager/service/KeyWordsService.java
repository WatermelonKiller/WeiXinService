/**  
 * @Title: KeyWordsService.java
 * @Package com.hz.wxBiz.keywordsManager.service
 * @Description: TODO
 * @author 李继�?
 * @date 2015-7-22 下午4:34:31
 * @version V1.0  
 */
package com.hd.wxBiz.keywordsManager.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.util.DateUtil;
import com.hd.util.dao.CommonDao;
import com.hd.wxBiz.keywordsManager.bean.KeyWords;
import com.hd.wxBiz.keywordsManager.sql.KeyWordsSql;

@Service
@Transactional
public class KeyWordsService {
	@Autowired
	private CommonDao cd;

	public String selKeyWords(String keyword, String type, String in_use) {
		StringBuffer sql = new StringBuffer();
		sql.append(KeyWordsSql.SEL_KEYWORDS);
		if (keyword != null && !"".equals(keyword)) {
			sql.append(" and keyword like '%" + keyword + "%'");
		}
		if (type != null && !"".equals(type)) {
			sql.append(" and type like '%" + type + "%'");
		}
		if (in_use != null && !"".equals(in_use)) {
			sql.append(" and in_use ='" + in_use + "'");
		}
		return sql.toString();
	}

	public boolean saveKeyWord(KeyWords kw) {
		kw.setId(UUID.randomUUID().toString().toUpperCase());
		kw.setClick_num(0);
		kw.setCreate_time(DateUtil.getDateTime());
		return cd.insertinfor(kw);
	}

	public boolean updateKeyWord(KeyWords kw) {
		return cd.updateinfor(kw);
	}

	public KeyWords getOne(String id) {
		return (KeyWords) cd.hibernateGet(KeyWords.class, id);
	}

	public boolean delKeyWord(String id) {
		return cd.DeleteSql("tab_core_keywords", "id", "'" + id + "'");
	}

	/**
	 * 
	 * @description: 获取重定向地�?
	 * @time 2015-7-22
	 * @param keyword
	 * @return
	 * @author Jc-Li
	 */
	public String getRequestMapping(String keyword) {
		return cd.getOneValue(KeyWordsSql.SEL_KEYWORDS + " and keyword='"
				+ keyword + "'");
	}
}
