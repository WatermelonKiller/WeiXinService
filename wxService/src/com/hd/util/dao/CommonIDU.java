package com.hd.util.dao;

import java.io.Serializable;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hd.util.SqlParameter;

/**
 * *****************************************************************************
 *     File Name         :  com.framework.dao.CommonIDU.java
 *     Description(说明)	: 公共增删改查方法�?包含增删该各�?��方法，参数传入类对象即可�?
 *     						包含根据传入参数进行拼装HQL语句进行删除和更新的方法
 * 						包含�?��根据�?��参数进行拼装HQL的查询方法和�?��传入HQL语句进行查询的方�?
 *****************************************************************************
 */
public class CommonIDU<T> extends CommonDBDao {

	
	private String strValue = "";
	private int iVerdict;
	private String strVName = "";
	private String[] strColumnV = null;
	private String hql = "";
	private Logger logger = Logger.getLogger(CommonIDU.class);
	private List<String> lstsql = null;

//	private static Session session = null;
//	private static Transaction tx = null;
	private static boolean flag = false;

	public CommonIDU() {
	}
	/**
	 * @param infor
	 * @return 
	 * 方法功能: 传对象插入方�?
	 */
	public boolean insertinfor(T infor) {
		try {
			getSession().save(infor);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			//session.close();
		}
		return flag;
	}
	/**
	 * @param list
	 * @return 
	 * 方法功能: 对list中的对象进行保存
	 */
	public boolean saveList(List<T> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				getSession().save(list.get(i));
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			//session.close();
		}
		return flag;
	}
	/**
	 * @param object
	 * @return 
	 * 方法功能: 对list中的对象进行保存
	 */
	public boolean saveArray(T[] object) {
		try {
			for (int i = 0; i < object.length; i++) {
				getSession().save(object[i]);
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			//session.close();
		}
		return flag;
	}
	/**
	 * @param list
	 * @return 
	 * 方法功能: 对list中的对象同时进行保存或更�?
	 */
	public boolean saveOrupdate(List savelist) {
		try {
			for(Iterator iter = savelist.iterator(); iter.hasNext();){
				getSession().saveOrUpdate(iter.next());
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			//session.close();
		}
		return flag;
	}
	/**
	 * @param infor
	 * @return 
	 * 方法功能: 传对象插入方法返回主�?但是正常save后主键对象就直接有�?�?
	 */
	public String saveReturn(T infor) {
		String zj = "-1";
		try {
			Serializable sz = getSession().save(infor);
			zj = sz.toString();
		} catch (Exception e) {
			zj = "-1";
			e.printStackTrace();
		} finally {
			//session.close();
		}
		return zj;
	}
	/**
	 * @param infor
	 * @return 
	 * 方法功能: 传对象更新方�?
	 */
	public boolean updateinfor(T infor) {
		try {
			getSession().update(infor);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			//session.close();
		}
		return flag;
	}
	/**
	 * @param hql
	 * @return 
	 * 方法功能: 传HQL处理 �?�?�?方法 String形式
	 */
	public boolean transactHql(String hql) {
		List<String> list = new ArrayList<String>();
		list.add(hql);
		return transactHqlplural(list);
	}
	/**
	 * @param hql
	 * @return 
	 * 方法功能: 传HQL处理 �?�?�?方法 数组形式
	 */
	public boolean transactHqlplural(String[] hql) {
		try {
			for (int i = 0; i < hql.length; i++)
				if(!"".equals(hql[i])){
					Query query = getSession().createQuery(hql[i]);
					query.executeUpdate();
				}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			//session.close();
		}
		return flag;
	}
	/**
	 * @param hql
	 * @return 
	 * 方法功能:传HQL的处�?�?�?�?方法 list集合形式
	 */
	public boolean transactHqlplural(List hql) {
		try {
			if(hql!=null&&!hql.isEmpty()){
				for (int i = 0; i < hql.size(); i++)
					if(!"".equals((String) hql.get(i))){
						Query query = getSession().createQuery((String) hql.get(i));
						query.executeUpdate();
					}
				flag = true;
			}else{
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			//session.close();
		}
		return flag;
	}
	/**
	 * @param sql
	 * @return 
	 * 方法功能: SQL 待测 处理 �?�?�?事件
	 */
	public boolean transactionSQL(String sql) {
		try {
			Query query = getSession().createSQLQuery(sql);
			query.executeUpdate();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			//session.close();
		}
		return flag;
	}
	/**
	 * @param hql
	 * @param sqlParams
	 * @return 
	 * 方法功能: 待测�?------------------------------------------------------------
	 */
	public boolean executeUpdate(String hql,SqlParameter sqlParams){
		int rs=executeUpdates(hql,sqlParams);
		if(rs == 0) return false; else return true;
	}
	public int executeUpdates(String hql,SqlParameter sqlParams){
		int rs=0;
		try {
			Query query = getSession().createQuery(hql);
			this.setParameters(query, sqlParams);
			rs = query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//session.close();
		}
		return rs;
	}
	/**
	 * @param infor
	 * @return 
	 * 方法功能: 	传对象删除方�?
	 */
	public boolean deleteinfor(T infor) {
		try {
			getSession().delete(infor);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			//session.close();
		}
		return flag;
	}
	/**
	 * @param table
	 * @param field
	 * @param column
	 * @param request
	 * @return 
	 * 方法功能: 修改方法,参数（表�?更新字段名数�?where条件名数�?request�?页面的字段名必须跟数据库中字段名�?��
	 */
	public boolean updatesTable(String table, String[] field, String[] column, HttpServletRequest request) {
		iVerdict = column.length;
		strVName = "";
		strColumnV = request.getParameterValues(column[0]);
		lstsql = new ArrayList();
		if (column != null && !"".equals(table) && field != null
				&& strColumnV != null) {
			/* 执行DELETESQL */
			for (int i = 0; i < strColumnV.length; i++) {// 确定更新条数
				hql = "update " + table + " set ";
				for (int j = 0; j < field.length; j++) {

					if (request.getParameterValues(field[j]) != null) {
						if (request.getParameterValues(field[j]).length == strColumnV.length) {
							strValue = request.getParameterValues(field[j])[i];
							strValue = strValue == null ? "" : strValue;
							if (j != field.length - 1) {
								hql += field[j] + "='" + strValue + "',";
							} else {
								hql += field[j] + "='" + strValue + "' ";
							}
						} else {
							strValue = request.getParameter(field[j]);
							strValue = strValue == null ? "" : strValue;
							if (j != field.length - 1) {
								hql += field[j] + "='" + strValue + "',";
							} else {
								hql += field[j] + "='" + strValue + "' ";
							}
						}
					}
				}
				hql += " where ";
				for (int j = 0; j < iVerdict; j++) {
					strValue = request.getParameterValues(column[j])[i];
					if (!"".equals(strValue) && strValue != null) {
						if (j != iVerdict - 1) {
							hql += column[j] + "='" + strValue + "' and ";
						} else {
							hql += column[j] + "='" + strValue + "' ";
						}
					}
				}
				lstsql.add(hql);
			}
			try {
				for (int s = 0; s < lstsql.size(); s++) {
					Query query = getSession().createQuery(lstsql.get(s).toString());
					query.executeUpdate();
				}
				logger.info("update true!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				//session.close();
			}
		} else {
			logger.info("update false!not table or field value");
			return false;
		}
	}
	/**
	 * @param table
	 * @param column
	 * @param request
	 * @return 
	 * 方法功能: 删除方法,参数（表�?where条件名数�?request�?页面的字段名必须跟数据库中字段名�?��
	 */
	public boolean DeleteCheck(String table, String[] column,HttpServletRequest request) {
		iVerdict = column.length;
		strVName = column[0];
		strColumnV = request.getParameterValues(strVName);
		lstsql = new ArrayList();
		if (column != null && !"".equals(table) && strColumnV != null) {
			/* 执行DELETESQL */
			for (int i = 0; i < strColumnV.length; i++) {
				hql = "delete from " + table + " where ";
				for (int j = 0; j < iVerdict; j++) {
					strValue = request.getParameterValues(column[j])[i];
					if (!"".equals(strValue) && strValue != null) {
						if (j != iVerdict - 1)
							hql += column[j] + "='" + strValue + "' and ";
						else
							hql += column[j] + "='" + strValue + "' ";
					}
				}
				lstsql.add(hql);
			}
			try {
				for (int s = 0; s < lstsql.size(); s++) {
					Query query = getSession().createQuery(lstsql.get(s).toString());
					query.executeUpdate();
				}
				logger.info("delete true!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				//session.close();
			}

		} else {
			logger.info("delete false!not table or field value");
			return false;
		}
	}

	/**
	 * @param table
	 * @param id
	 * @param request
	 * @return 
	 * 方法功能: 单条件删除方�?参数（表�?where条件id,request�?页面的字段名必须跟数据库中字段名�?��
	 */
	public boolean DeleteOne(String table, String id, HttpServletRequest request) {
		strColumnV = request.getParameterValues(id);
		if (!"".equals(id) && !"".equals(table) && strColumnV != null) {
			/* 执行DELETESQL */
			hql = "delete from " + table + " where " + id + " in (";
			for (int i = 0; i < strColumnV.length; i++) {
				strValue = request.getParameterValues(id)[i];
				if (!"".equals(strValue) && strValue != null) {
					if (i != strColumnV.length - 1)
						hql += "'" + strValue + "',";
					else
						hql += "'" + strValue + "')";
				}
			}
			try {
				Query query = getSession().createQuery(hql);
				query.executeUpdate();
				logger.info("delete true!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				//session.close();
			}
		} else {
			logger.info("delete false!not table or field value");
			return false;
		}
	}
	/**
	 * @param table
	 * @param where
	 * @param value
	 * @return 
	 * 方法功能: 执行�?��集合的删除功�?参数: 表名 , where条件(单一), 对应的�?的集�?
	 */
	public boolean DeleteHql(String table, String where, String value) {
		if (!"".equals(table)) {
			hql = "delete from " + table ;
			if(!"".equals(where)){
				hql += " where " + where + " in (" + value + ")";
			}
			/* 执行DELETESQL */
			try {
				Query query = getSession().createQuery(hql);
				query.executeUpdate();
				logger.info("delete true!");
				return true;
			} catch (Exception e) {
				return false;
			} finally {
				//session.close();
			}
		} else {
			logger.info("delete false!not table or field value");
			return false;
		}
	}
	
	/***************************************************************************
	 * 添加参数�?
	 * 
	 * @param sqlParams
	 */
	private void setParameters(Query q, SqlParameter sqlParams) {
		if (sqlParams != null && sqlParams.size() > 0) {
			for (int i = 0; i < sqlParams.size(); i++) {
				q.setParameter(i, sqlParams.get(i));
			}
		}
	}
	/**
	 * 取得sessionFactory.
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	/**
	 * 采用@Autowired按类型注入SessionFactory, 当有多个SesionFactory的时候Override本函�?
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	/**
	 * 取得当前Session.
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	protected SessionFactory sessionFactory;
	
	public static void main(String[] args) {
	
	}
	

}
