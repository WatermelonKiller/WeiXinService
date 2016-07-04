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
 *     Description(˵��)	: ������ɾ�Ĳ鷽���� ������ɾ�ø�һ��������������������󼴿ɡ� 
 *     						�������ݴ����������ƴװHQL������ɾ���͸��µķ���
 * 						����һ������������������ƴװHQL�Ĳ�ѯ������һ������HQL�����в�ѯ�ķ���
 *****************************************************************************
 */
public class CommonSel<T> extends CommonIDU<T> {

	private List lstValue = null;

	public CommonSel() {
	}

	// ��HQL�����ѯ��������ֵ������һ����.���صĽ���Ƕ��SQL�����������صĽ��
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

	// ��׼����ѯ ����: ���� , where����(��һ) , ��Ӧ��ֵ(��һ) ���ص�һ����
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
	// ��׼����ѯ ����: ���� , where����(��һ) , ��Ӧ��ֵ(��һ) ���ؽ�����ϵ�list����
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
//	 ��׼����ѯ ����: ���� , where����(������) , ��Ӧ��ֵ(��ֵ) ���ؽ�����ϵ�list���� ��ֻ֧������ƥ��һ��
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

	// HQL��ѯ ����: HQL��� ���ؽ�����ϵ�list����
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

	// HQL��ѯ ����: HQL��� ���ؽ����Object����
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
//	 SQL��ѯ ����: SQL��� ���ؽ�����ϵ�list����
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
	// SQL��ѯ ����: SQL��� ���ؽ����Object����
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
//	 SQL��ѯ ����: SQL���Ԥ���� ���ؽ����Object����
	public Object selSqlObjByST(String sql,String ...params) {
		try {
			Query query = getSession().createSQLQuery(sql);
			Object obj = null;
			if (params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					// ѭ��Ϊquery�е�hql���ϲ���
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
//	 SQL��ѯ ����: SQL���Ԥ���� ���ؽ����List����
	public List selSqlListByST(String sql,String...params) {
		List list = null;
		try {
			Query query = getSession().createSQLQuery(sql);
			if (params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					// ѭ��Ϊquery�е�hql���ϲ���
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
	 * ����HibernateԤ����������hql���
	 * @param hql
	 * @param params ��������
	 * @return Object ���ص�һ����
	 */
	public Object selHqlObjByST(String hql, String ...params) {
		Object obj = null;
		try {
			Query query = getSession().createQuery(hql);
			if (params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					// ѭ��Ϊquery�е�hql���ϲ���
					query.setParameter(i, params[i]);

					// ��Ϊ������һ��Ԫ��ʱ��,����uniqueResult����,���д���
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
	 * ����HibernateԤ����������hql���
	 * @param hql
	 * @param params ��������
	 * @return Object ����list���϶���
	 */
	public List selHqlListByST(String hql, String ...params) {
		List list = null;
		try {
			Query query = getSession().createQuery(hql);
			if (params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					// ѭ��Ϊquery�е�hql���ϲ���
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
	 * @param eam  ��λ��
	 * @param szb  ������һֵ
	 * @return ��������: ��������ʹ��get������ѯ����
	 */
	public Object hibernateGet(String eam, Serializable szb) {
		try {
			Object obj = getSession().get(eam, szb);
			return obj;
		} catch (Exception e) {
			System.out.println("hibernateGet String ʧ��");
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	/**
	 * @param eam  ��
	 * @param szb  ������һֵ
	 * @return ��������: ��������ʹ��get������ѯ����
	 */
	public <T extends Serializable> T hibernateGet (Class<T> eam, Serializable szb) {
		try {
			return (T) getSession().get(eam, szb);
		} catch (Exception e) {
			System.out.println("hibernateGet Class<T> ʧ��");
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	/**
	 * @param eam
	 * @param szb
	 * @return ��������: ��������hql��ѯ�����ض���
	 */
	public T selHqlObjByPS(String hql,SqlParameter sqlParams) {
		try {
			Query q= getSession().createQuery(hql);
			this.setParameters(q,sqlParams);
			return (T)q.uniqueResult();
		} catch (Exception e) {
			System.out.println("selHqlObjByPS ʧ��");
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	/**
	 * @param eam
	 * @param szb
	 * @return ��������: ��������sql��ѯ������List����
	 */
	public List selSqlListByPS(String sql,SqlParameter sqlParams) {
		try {
			Query q =  getSession().createSQLQuery(sql);
			this.setParameters(q,sqlParams);
			return q.list();
		} catch (Exception e) {
			System.out.println("selSqlListByPS ʧ��");
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	/**
	 * @param eam
	 * @param szb
	 * @return ��������: ��������hql��ѯ�����ض���
	 */
	public T selSqlObjByPS(String sql,SqlParameter sqlParams) {
		try {
			Query q =  getSession().createSQLQuery(sql);
			this.setParameters(q,sqlParams);
			return (T)q.uniqueResult();
		} catch (Exception e) {
			System.out.println("selSqlObjByPS ʧ��");
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	/**
	 * @param eam
	 * @param szb
	 * @return ��������: ��������sql��ѯ������List����
	 */
	public List selHqlListByPS(String hql,SqlParameter sqlParams) {
		try {
			Query q =  getSession().createQuery(hql);
			this.setParameters(q,sqlParams);
			return q.list();
		} catch (Exception e) {
			System.out.println("selHqlListByPS ʧ��");
			e.printStackTrace();
			return null;
		} finally {
			//session.close();
		}
	}
	/***************************************************************************
	 * ��Ӳ�����
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
