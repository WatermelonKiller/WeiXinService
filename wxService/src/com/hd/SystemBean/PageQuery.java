package com.hd.SystemBean;

import java.util.List;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class PageQuery{ //extends DBQSimple{
	
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
	
	private JdbcTemplate jdbcTemplate; 
	/**
	 * 取得JdbcTemplate.
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	/**
	 * 采用@Autowired按类型注入JdbcTemplate, 当有多个JdbcTemplate的时候Override本函�?
	 */
	@Autowired
	public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	private List lstValue=null;
//	private Session session=null;

//	hql分页方法获得条数
	public int getRowCountHql(String Hql){
		int count =0;
		try {
			int u = Hql.toUpperCase().indexOf("FROM ");
			String usql="";
			if(Hql.toUpperCase().lastIndexOf(" ORDER ")!=-1){
				usql = Hql.substring( u ,Hql.toUpperCase().lastIndexOf(" ORDER "));
			}else{
				usql = Hql.substring(u);
			}
			Hql = "select count(*) as cout "+usql;
			List list = getSession().createQuery(Hql).list();
			if(list!=null){
				if(list.size()>1){
					count = list.size();
				}else{
					count = Integer.parseInt(list.get(0).toString());
				}
			}
			return count;
		} catch (Exception e) {
			System.err.println("Exception on getRowCountHql method" + e.toString());
			System.out.println("Hql:"+Hql);
			return count;
		}finally{
			//session.close();
		}
	}
//	sql分页方法获得条数
	public int getRowCountSql(String Sql){
		int count =0;
		try {
			int u = Sql.toUpperCase().indexOf("FROM ");
			String usql="";
			if(Sql.toUpperCase().lastIndexOf(" ORDER ")!=-1){
				usql = Sql.substring( 0 , Sql.toUpperCase().lastIndexOf(" ORDER "));
			}else{
				usql = Sql;
			}
			Sql = "select count(0) as cout from ("+usql+") as tab";
			List list = getSession().createSQLQuery(Sql).list();
			if(list!=null){
				if(list.size()>1){
					count = list.size();
				}else{
					count = Integer.parseInt(list.get(0).toString());
				}
			}
			return count;
		} catch (Exception e) {
			System.err.println("Exception on getRowCountSql method" + e.toString());
			System.out.println("Sql:"+Sql);
			return count;
		}finally{
			//session.close();
		}
	}
	
//	hql分页方法hql语句,�?���?�?���?
	public List queryHqlList(String hql,int first,int max){
		lstValue = new ArrayList();
		try{
			Query qu = getSession().createQuery(hql);
			qu.setFirstResult(first);
			//qu.setFetchSize(arg0); 每次取数据数
			qu.setMaxResults(max);
	    	lstValue = qu.list();
			return lstValue;
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}finally{
			//session.close();
		}
	}
//	public List queryHqlList(final String hql, final int first, final int max) {  
//		lstValue = getHibernateTemplate().executeFind(new HibernateCallback() {  
//			public Object doInHibernate(Session session) throws HibernateException, SQLException {  
//				Query query = session.createQuery(hql);  
//				query.setFirstResult(first);  
//				query.setMaxResults(max);  
//				return query.list();
//			}  
//		});  
//		return lstValue;  
//	}

//	sql分页方法hql语句,�?���?�?���?
	public List querySqlList(String sql,int first,int max){
		lstValue = new ArrayList();
		try{
			Query qu = getSession().createSQLQuery(sql);
			qu.setFirstResult(first);
			//qu.setFetchSize(arg0); 每次取数据数
			qu.setMaxResults(max);
	    	lstValue=qu.list();
			return lstValue;
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}finally{
			//session.close();
		}
	}
//	jdbc分页方法获得条数
	public int getRowCountJdbc(String Sql){
		int count =0;
		try {
//			db.DBQuery();
			int u = Sql.toUpperCase().indexOf("FROM ");
			String usql="";
			if(Sql.toUpperCase().lastIndexOf(" ORDER ")!=-1){
				usql = Sql.substring( 0 ,Sql.toUpperCase().lastIndexOf(" ORDER "));
			}else{
				usql = Sql;
			}
			Sql = "select count(0) as cout from ("+usql+") as tab";
//			ResultSet rs = db.getResults(Sql);
//			if(rs.next()){
//				count = rs.getInt(1);
//			}
			count = getJdbcTemplate().queryForInt(Sql);
			return count;
		} catch (Exception e) {
			System.err.println("Exception on getRowCountHql method" + e.toString());
			System.out.println("Hql:"+Sql);
			return count;
		}finally{
//			db.close();
		}
	}
	/**
	 * @param Sql
	 * @param first
	 * @param max
	 * @return 
	 * 方法功能: jdbc分页方法Sql语句,�?���?�?���?
	 */
	public List queryJdbcList(String Sql,int first,int max){
		lstValue = new ArrayList();
		try {
			StringBuffer sbf = new StringBuffer("WITH query AS (select ROW_NUMBER() OVER (");
			if(Sql.toUpperCase().lastIndexOf(" ORDER ")!=-1){
				sbf.append( Sql.substring(Sql.toUpperCase().lastIndexOf(" ORDER ")) + " ) as rownum, * from (");
				sbf.append( Sql.substring( 0 ,Sql.toUpperCase().lastIndexOf(" ORDER ")) );
			}else{
				sbf.append(" ORDER BY CURRENT_TIMESTAMP ) as rownum, * from (");
				sbf.append( Sql );
			}
			sbf.append(" ) as tab ");
			if(first==0){
				sbf.append(" ) SELECT * FROM query WHERE rownum BETWEEN "+first+" AND "+(first+max)+" ");
			}else{
				sbf.append(" ) SELECT * FROM query WHERE rownum BETWEEN "+(first+1)+" AND "+(first+max)+" ");
			}
			lstValue = getJdbcTemplate().queryForList(sbf.toString());
			//避免order by 出问�?
			//if(Sql.toUpperCase().lastIndexOf(" ORDER ")!=-1){
			//	sbf.append( Sql.substring( Sql.toUpperCase().lastIndexOf(" ORDER ")) );
			//}
			
//			db.DBQuery();
//			ResultSet rs = db.getResults(sbf.toString());
//			ResultSetMetaData rsmd = rs.getMetaData();
//	        while( rs.next() ){
//	            Map map = new HashMap();
//				for(int i=1;i<=  rsmd.getColumnCount();i++){
//				    String value = (String) rs.getString(i);
//				    if("null".equalsIgnoreCase(value))value="";
//					map.put(rsmd.getColumnLabel(i),value);
//				}
//				lstValue.add(map);
//	        }
//			PreparedStatement pst = db.getPreparedStatement(Sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			rs = pst.executeQuery();
//			if(rs.next()){
//				if(0==first){
//					rs.beforeFirst();
//				}else{
//					rs.relative(first-1);
//				}
//				int j=0;
//		        ResultSetMetaData rsmd = rs.getMetaData();
//		        while(rs.next() && j<max){
//		            Map map = new HashMap();
//					for(int i=1;i<=  rsmd.getColumnCount();i++){
//					    String value = (String) rs.getString(i);
//					    if("null".equalsIgnoreCase(value))value="";
//						map.put(rsmd.getColumnLabel(i),value);
//					}
//					lstValue.add(map);
//					j++;
//		        }
//			}
	        return lstValue;
		} catch (Exception e) {
			System.out.println(" List queryJdbcList 方法异常");
			e.printStackTrace();
			return null;
		}finally{
//			db.close();
		}
	}
	
	
	public static void main(String[] args) {
		
	}
}
