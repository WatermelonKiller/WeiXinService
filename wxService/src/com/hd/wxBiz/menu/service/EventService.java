/**  
 * @Title: EventService.java
 * @Package com.hz.wxBiz.menu.service
 * @Description: TODO
 * @author 李继�?
 * @date 2015-6-27 上午9:20:09
 * @version V1.0  
 */
package com.hd.wxBiz.menu.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.util.ToolUtil;
import com.hd.util.DateUtil;
import com.hd.util.dao.CommonDao;
import com.hd.wxBiz.menu.bean.Event;
import com.hd.wxBiz.menu.sql.EventSql;

@Transactional
@Service
public class EventService {
	@Autowired
	private CommonDao cd;

	public String selAllSql() {
		String sql = EventSql.selAll;
		return sql;
	}

	/**
	 * 
	 * @description: 添加事件
	 * @time 2015-7-22
	 * @param event
	 * @return
	 * @author Jc-Li
	 */
	public boolean addNewEvent(Event event) {
		event.setClick_num(0);
		event.setCreate_time(DateUtil.getDateTime());
		if (event.getEvent_type() != null
				&& "biz".equals(event.getEvent_type())) {
			event.setEvent_key(EventSql.BIZK + ToolUtil.getSixBitWords());
		} else {
			event.setEvent_key(EventSql.COMK + ToolUtil.getSixBitWords());
		}
		event.setId(UUID.randomUUID().toString().toUpperCase());
		event.setReleased("N");
		return cd.insertinfor(event);
	}

	public boolean updEvent(Event event) {
		if (event.getReleased() != null && "Y".equals(event.getReleased())) {
			return false;
		} else {
			return cd.updateinfor(event);
		}

	}

	public Event getEvent(String id) {
		return (Event) cd.hibernateGet(Event.class, id);
	}

	public boolean delEvent(String id) {
		return cd.DeleteSql("tab_core_event", "id", "'" + id + "'");
	}

	public List<Map<String, String>> getEventInfo() {
		List<Map<String, String>> list = cd.queryListByPS(selAllSql(), null);
		return list;
	}

	public String getRequestMapping(String event_key) {
		return cd
				.getOneValue("select request_mapping from tab_core_event where event_key ='"
						+ event_key + "'");
	}
}
