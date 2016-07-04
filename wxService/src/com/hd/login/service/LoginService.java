package com.hd.login.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.login.bean.User;
import com.hd.login.sql.LoginSql;
import com.hd.util.MD5Encoder;
import com.hd.util.SqlParameter;
import com.hd.util.dao.CommonDao;

@Service
@Transactional
public class LoginService {

	@Autowired
	private CommonDao<?> cd;

	public Map<String, String> checkUser(String userName, String passWord) {
		String sql = "select * from tab_user where username='" + userName
				+ "' and password ='" + MD5Encoder.encode(passWord) + "'";
		Map<String, String> map = cd.DBjsonMap(sql);
		return map;
	}

	public Map<String, String> selImages() {
		String sql = "select * from tab_archive_attach order by create_time desc";
		Map<String, String> map = cd.DBjsonMap(sql);
		return map;
	}

	public boolean changePassword(String id, String newPassword) {
		String newMd5Password = MD5Encoder.encode(newPassword);
		User user = getOneUser(id);
		if (!user.getPassword().equals(newPassword)) {
			user.setPassword(newMd5Password);
			return cd.executeSQL(
					"update  [dbo].[tab_user] set password=? where id=?",
					new SqlParameter(newMd5Password, id));
		} else {
			return false;
		}
	}
	/**
	 * 
	 * @author : T-rq
	 * @date : 2016-3-1下午6:19:21
	 * @param :
	 * @return :
	 * @description :数据库的更新
	 */
	public boolean UpdatePassword(String id, String Password) {
		String Md5Password = MD5Encoder.encode(Password);
		User user = getOneUser(id);
		if (!user.getPassword().equals(Password)) {
			user.setPassword(Md5Password);
			return cd.executeSQL(
					"update  [dbo].[tab_user] set password=? where id=?",
					new SqlParameter(Md5Password, id));
		} else {
			return false;
		}
	}

	/**
	 * . Description:淇濆瓨鐢ㄦ埛 Author:Jc-Li parameters:鐢ㄦ埛瀵硅薄 Date:2015-8-18
	 */
	@Transactional
	public boolean saveUser(User user) {
		user.setId(UUID.randomUUID().toString().toUpperCase());
		user.setPassword(MD5Encoder.encode(user.getPassword()));
		String sql = "INSERT INTO [dbo].[tab_user] ([id] ,[username],[password],[cid],[mail],[system_id],[tel],[dept_id]) VALUES (?,?,?,?,?,?,?,?)";
		boolean flag = cd.executeSQL(
				sql,
				new SqlParameter(user.getId(), user.getUsername(), user.getPassword(), user.getCid(),user.getMail(),user.getSystem_id(), user.getTel(),user.getDept_id()));
		return flag;
	}

	public User getOneUser(String id) {
		return (User) cd.hibernateGet(User.class, id);
	}

	public String getIdByUsername(String username) {
		String sql = LoginSql.SEL_IDBYUSERNAME + " and username='" + username
				+ "'";
		return cd.getOneValue(sql);
	}
	
	public String getIdByMail(String mail) {
		String sql = LoginSql.SEL_IDBYUSERNAME + " and mail='" + mail
				+ "'";
		System.out.println(sql);
		return cd.getOneValue(sql);
	}
	
	public String getIdByUserMail(String username,String mail) {
		String sql = LoginSql.SEL_IDBYUSERNAME + " and username='" + username + "'" + " and mail='" + mail + "'";
		System.out.println("sql:"+sql);		
		return cd.getOneValue(sql);
	}

	/**
	 * @author Yang Jiyu
	 * @function 获取左侧菜单栏目
	 * @function 
	 * @time 2015-11-04
	 * @param roleIds
	 * @param systemId
	 * @return Map<String,List<Map<String,String>>>
	 */
	public List<Map<String, Object>> getMenuTree(String dept_id,String systemId, HttpServletRequest request) {
		String city = "SZF";
		List<Map<String, Object>> columnList = cd.queryListByPS(LoginSql.SEL_COLUMN_BY_SYSTEM_ROLE, new SqlParameter(systemId,dept_id));
		if (columnList != null) {
			List<Map<String, Object>> parent = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> son = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < columnList.size(); i++) {
				Map<String, Object> ss = (Map<String, Object>) columnList.get(i);
				String type = (String) ss.get("type");
				if ("J".equals(type)) {
					parent.add(ss);
				} else {
					son.add(ss);
				}
			}
			
			List<Map<String, Object>> sonZhi = null;
			Map<String, Object> son_map, parnet_map;
			for (int i = 0; i < parent.size(); i++) {
				parnet_map = (Map<String, Object>) parent.get(i);
				String parnet_id = (String) parnet_map.get("id");
				sonZhi = new ArrayList<Map<String, Object>>();
				for (int s = 0; s < son.size(); s++) {
					son_map = (Map<String, Object>) son.get(s);
					String son_id = (String) son_map.get("pId");
					String str_liu = son_map.get("name") + "";
					if (!"SZF".equals(city)) {
						if (str_liu.equals("锟斤拷锟斤拷锟斤拷织锟杰癸拷")) {
							son_map.put("link",
									"/goRedirectPG?systemcontro=bmzzjg");
						} else if (str_liu.equals("系统锟斤拷锟斤拷目锟斤拷锟斤拷")) {
							son_map.put("link",
									"/goRedirectPG?systemcontro=xtlmg");
						}
					}
					if (son_id.equals(parnet_id)) {
						sonZhi.add(son_map);
					}
				}
				parnet_map.put("soninfo", sonZhi);
			}
			return parent;
		}
		return null;
	}
}
