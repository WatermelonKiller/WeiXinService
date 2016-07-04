package com.hd.util.dao;

import java.io.Serializable;
import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.hd.util.SqlParameter;

/**
 * *****************************************************************************
 *     File Name         :  com.framework.dao.CommonIDU.java
 *     Description(说明)	: 公共增删改查方法类 包含增删该各一个方法，参数传入类对象即可。 
 *     						包含根据传入参数进行拼装HQL语句进行删除和更新的方法
 * 						包含一个根据所传参数进行拼装HQL的查询方法和一个传入HQL语句进行查询的方法
 *****************************************************************************
 */
public class CommonSel<T> extends CommonIDU<T> {

	private List lstValue = null;

	public CommonSel() {
	}

	// 传HQL数组查询出，返回值必须是一样的.返回的结果是多个SQL语句加起来返回的结果
	public List FindHqlplural(String[] hql) {
		List<String> totallist = new ArrayList<String>();
		List singalist = null;
		try {
			for (int i = 0; i < hql.length; i++){
				singalist = new ArrayList();
				singalist = getSession().createQuery(hql[i]).list();
				totallist.addAll(singalist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//session.close();
		}
		return totallist;
	}

	// 标准化查询 参数: 对象 , where条件(单一) , 对应的值(单一) 返回单一对象
	public Object getStandObj(Object obj, String where, String value) {
		try {
			Object hobj = getSession().createCriteria(obj.getClass()).add(
					Restrictions.eq(where, value)).uniqueResult();
			return hobj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	// 标准化查询 参数: 对象 , where条件(单一) , 对应的值(单一) 返回结果集合的list对象
	public List getStandList(Object obj, String where, String value) {
		try {
			lstValue = getSession().createCriteria(obj.getClass()).add(
					Restrictions.eq(where, value)).list();
			return lstValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
//	 标准化查询 参数: 对象 , where条件(多条件) , 对应的值(多值) 返回结果集合的list对象 咱只支持条件匹配一种
	public List getStandList(Object obj, String where[], String value[]) {
		try {
			Criteria ca = getSession().createCriteria(obj.getClass());
			for (int i = 0; i < where.length; i++){
				ca.add(Restrictions.eq(where[i], value[i]));
			}
			lstValue = ca.list();
			return lstValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}

	// HQL查询 参数: HQL语句 返回结果集合的list对象
	public List selHqlList(String hql) {
		lstValue = new ArrayList();
		try {
			lstValue = getSession().createQuery(hql).list();
			return lstValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}

	// HQL查询 参数: HQL语句 返回结果的Object对象
	public Object selHqlObj(String hql) {
		try {
			Query query = getSession().createQuery(hql);
			Object obj = query.setMaxResults(1).uniqueResult();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
//	 SQL查询 参数: SQL语句 返回结果集合的list对象
	public List selSqlList(String sql) {
		lstValue = new ArrayList();
		try {
			lstValue = getSession().createSQLQuery(sql).list();
			return lstValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	// SQL查询 参数: SQL语句 返回结果的Object对象
	public Object selSqlObj(String sql) {
		try {
			Object obj = getSession().createSQLQuery(sql).setMaxResults(1).uniqueResult();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
//	 SQL查询 参数: SQL语句预处理 返回结果的Object对象
	public Object selSqlObjByST(String sql,String ...params) {
		try {
			Query query = getSession().createSQLQuery(sql);
			Object obj = null;
			if (params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					// 循环为query中的hql加上参数
					query.setParameter(i, params[i]);
					if (i == params.length - 1) {
						obj = query.setMaxResults(1).uniqueResult();
					}
				}
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
//	 SQL查询 参数: SQL语句预处理 返回结果的List对象
	public List selSqlListByST(String sql,String...params) {
		List list = null;
		try {
			Query query = getSession().createSQLQuery(sql);
			if (params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					// 循环为query中的hql加上参数
					query.setParameter(i, params[i]);

					if (i == params.length - 1) {
						list = query.list() ;
					}
				}
			}else{
				list = query.list() ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
		return list;
	}
	/**
	 * 利用Hibernate预处理来处理hql语句
	 * @param hql
	 * @param params 参数数组
	 * @return Object 返回单一对象
	 */
	public Object selHqlObjByST(String hql, String ...params) {
		Object obj = null;
		try {
			Query query = getSession().createQuery(hql);
			if (params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					// 循环为query中的hql加上参数
					query.setParameter(i, params[i]);

					// 当为最后最后一个元素时候,加上uniqueResult方法,进行处理
					if (i == params.length - 1) {
						obj = query.setMaxResults(1).uniqueResult();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
		return obj;
	}
	/**
	 * 利用Hibernate预处理来处理hql语句
	 * @param hql
	 * @param params 参数数组
	 * @return Object 返回list集合对象
	 */
	public List selHqlListByST(String hql, String ...params) {
		List list = null;
		try {
			Query query = getSession().createQuery(hql);
			if (params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					// 循环为query中的hql加上参数
					query.setParameter(i, params[i]);

					if (i == params.length - 1) {
						list = query.list() ;
					}
				}
			}else{
				list = query.list() ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
		return list;
	}
	/**
	 * @param eam  类位置
	 * @param szb  主键单一值
	 * @return 方法功能: 根据主键使用get方法查询对象
	 */
	public Object hibernateGet(String eam, Serializable szb) {
		try {
			Object obj = getSession().get(eam, szb);
			return obj;
		} catch (Exception e) {
			System.out.println("hibernateGet String 失败");
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	/**
	 * @param eam  类
	 * @param szb  主键单一值
	 * @return 方法功能: 根据主键使用get方法查询对象
	 */
	public <T extends Serializable> T hibernateGet (Class<T> eam, Serializable szb) {
		try {
			return (T) getSession().get(eam, szb);
		} catch (Exception e) {
			System.out.println("hibernateGet Class<T> 失败");
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	/**
	 * @param eam
	 * @param szb
	 * @return 方法功能: 根据条件hql查询，返回对象
	 */
	public T selHqlObjByPS(String hql,SqlParameter sqlParams) {
		try {
			Query q= getSession().createQuery(hql);
			this.setParameters(q,sqlParams);
			return (T)q.uniqueResult();
		} catch (Exception e) {
			System.out.println("selHqlObjByPS 失败");
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	/**
	 * @param eam
	 * @param szb
	 * @return 方法功能: 根据条件sql查询，返回List集合
	 */
	public List selSqlListByPS(String sql,SqlParameter sqlParams) {
		try {
			Query q =  getSession().createSQLQuery(sql);
			this.setParameters(q,sqlParams);
			return q.list();
		} catch (Exception e) {
			System.out.println("selSqlListByPS 失败");
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	/**
	 * @param eam
	 * @param szb
	 * @return 方法功能: 根据条件hql查询，返回对象
	 */
	public T selSqlObjByPS(String sql,SqlParameter sqlParams) {
		try {
			Query q =  getSession().createSQLQuery(sql);
			this.setParameters(q,sqlParams);
			return (T)q.uniqueResult();
		} catch (Exception e) {
			System.out.println("selSqlObjByPS 失败");
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	/**
	 * @param eam
	 * @param szb
	 * @return 方法功能: 根据条件sql查询，返回List集合
	 */
	public List selHqlListByPS(String hql,SqlParameter sqlParams) {
		try {
			Query q =  getSession().createQuery(hql);
			this.setParameters(q,sqlParams);
			return q.list();
		} catch (Exception e) {
			System.out.println("selHqlListByPS 失败");
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	/***************************************************************************
	 * 添加参数。
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
}
