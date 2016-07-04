/**  
 * @Title: InterActiveImpl.java
 * @Package com.core.interactive.impl
 * @Description: TODO
 * @author 鏉庣户瓒�
 * @date 2015-7-13 涓婂崍9:28:18
 * @version V1.0  
 */
package com.hd.wxBiz.Core.CoreService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.util.dao.CommonDao;
import com.hd.wxBiz.Message.bean.MainMessage;
import com.hd.wxBiz.Message.bean.MainMessageDetail;

@Service
@Transactional
public class InterActiveService {

	@SuppressWarnings("rawtypes")
	@Autowired
	private CommonDao cd;

	@SuppressWarnings("unchecked")
	public String messageSave(MainMessage mainMessage,
			MainMessageDetail messageDetail) {
		String result = "";
		String mainId = UUID.randomUUID().toString();
		mainMessage.setId(mainId);
		boolean rsMain = cd.insertinfor(mainMessage);
		if (true == rsMain) {
			messageDetail.setId(UUID.randomUUID().toString());
			messageDetail.setMainId(mainId);
			boolean rsDetail = cd.insertinfor(messageDetail);
			if (true == rsDetail) {
				result = "success";
			} else {
				result = "fail";
			}
		} else {
			result = "fail";
		}
		return result;
	}

}
