package com.hd.util.dao;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import com.hd.util.CommonUtils;
import com.hd.util.SqlParameter;
/**
 * *****************************************************************************
 *     File Name        :com.framework.dao.DBDAO.java
 *     Description(说明)	:针对DBQ类的封装,让查询更加方�?进行JDBC查询时�?不用实例化JDBC对象直接DBDAO.XXXX 即可对数据库操作,支持多种查询返回结果集为list方便操作
 *     				     大大�?��编写代码时对rs的操�?提高了代码重用率.
 *****************************************************************************
 */
public class DBDAO extends DBQ{
	
//	@Autowired
	public DBQ db = this;
	
	/**
	 * @param sql 要执行的sql语句
	 * @param sqlParams 参数集合
	 * @param flag 该参数有2个�?,�?��为list,�?��为map(默认为map) list代表返回的list中每个子元素都是list,每个子元素都是rs的一条记�?map代表返回的list中每个子元素都是map,每个子元素都是rs的一条记�?
	 * @return List 结果集的集合,未查询到结果的时候listsize�?,有实�?不会为null
	 * 方法功能:利用预处理查询当前sql语句,按照flag要求返回结果
	 */
	public List queryListByPS(String sql,SqlParameter sqlParams) {
		return queryListByPS(sql,sqlParams,null);
	}
	public List queryListByPS(String sql,SqlParameter sqlParams,String flag) {
//		if(!"".equals(talog)){
//			getJdbcTemplate().setDatabaseProductName(talog);
//		}
		List dataList = null ; 
		if(sqlParams!=null && !sqlParams.isEmpty()){
			dataList = getJdbcTemplate().queryForList(sql, sqlParams.toArray());
		}else{
			dataList = getJdbcTemplate().queryForList(sql);
		}
//		db.DBQuery();
//		try {
//			if(!"".equals(talog)){
//				db.setCatalog(talog);
//			}
//			dataList = db.queryListByPS(db.getPreparedStatement(sql), sqlParams, flag) ;
//		}
//		catch (SQLException e)
//		{
//			CommonUtils.printE(e) ;
//		}
//		finally
//		{
//			db.close() ;
//		}
		
		return dataList ;
	}
	
	/**
	 * @param sql 要执行的SQL语句
	 * @param sqlParams 参数集合
	 * @return true成功,false失败
	 * 方法功能:利用预处理对象处理SQL语句�?�?�?
	 */
	public boolean executeSQL(String sql,SqlParameter sqlParams) {
		boolean flag = false ;
		
		db.DBQuery();
		try
		{
			flag = db.executeSQL(db.getPreparedStatement(sql), sqlParams) ;
		}
		catch (SQLException e)
		{
			CommonUtils.printE(e) ;
		}
		finally
		{
			db.close() ;
		}
		
		return flag ;
	}
	/**
	 * @param sqlParams 中为map 键�?�? sql键对应要执行的SQL语句 sqlParams键对应参数集�?
	 * @return true成功,false失败
	 * 方法功能:利用预处理对象处理SQL语句�?�?�?
	 */
	public  boolean executeSQL(List sqlParams) {
		return executeSQL(sqlParams,"");
	}
	public  boolean executeSQL(List sqlParams,String talog) {
		boolean flag = true ;
		String sql = "";
		try {
			db.DBQuery();
			if(!"".equals(talog)){
				db.setCatalog(talog);
			}
			db.setAutoCommit(false);//设置为不自动提交
			if(sqlParams!=null&&!sqlParams.isEmpty()){
				Map<?, ?> map;
				for(int i=0;i<sqlParams.size();i++){
					map = (Map<?, ?>) sqlParams.get(i);
					sql = (String)map.get("sql");
					flag = db.executeSQL(db.getPreparedStatement(sql),(SqlParameter) map.get("sqlParams")) ;
				}
			}
			db.commit();//提交事务
		} catch (Exception e) {
			CommonDao.deBug("错误语句�?" + sql);
			e.printStackTrace();
			db.rollback();
			flag = false;
		} finally {
			db.close();
		}
		return flag ;
	}
	/**
	 * @param sql 要执行的SQL语句
	 * @return true成功,false失败
	 * 方法功能:利用statement执行sql语句
	 */
	public  boolean executeUpdate(String sql)
	{
		boolean flag = true ;
		
		db.DBQuery();
		
		try
		{
			flag = db.executeCreate(sql) ;
		}
		catch (SQLException e)
		{
			CommonUtils.printE(e) ;
		}
		
		db.close() ;
		
		return flag ;
	}
	
	/**
	 * @param sql 要执行的sql语句
	 * @param sqlParams 参数集合
	 * @param flag 返回对象的类�?map还是list
	 * @return Object 按照flag传的参数转型,如果返回值为null代表未查询出结果
	 * 方法功能:根据flag传的参数,返回当前sql语句可以查询的一条数�?map或�?list.
	 */
	public  Object queryObjectByPS(String sql) {
		return queryObjectByPS(sql,null,null);
	}
	public  Object queryObjectByPS(String sql,SqlParameter sqlParams) {
		return queryObjectByPS(sql,sqlParams,null);
	}
	public  Object queryObjectByPS(String sql,SqlParameter sqlParams,String flag) {
//		if(!"".equals(talog)){
//			getJdbcTemplate().setDatabaseProductName(talog);
//		}
		Object obj = null ;
		try{
			if(sqlParams!=null && !sqlParams.isEmpty()){
				obj = getJdbcTemplate().queryForList(sql, sqlParams.toArray()).get(0);
			}else{
				obj = getJdbcTemplate().queryForList(sql).get(0);
			}
		}catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
//		db.DBQuery();
//		try
//		{
//			if(!"".equals(talog)){
//				db.setCatalog(talog);
//			}
//			obj = db.queryObjectByPS(db.getPreparedStatement(sql),sqlParams,flag) ;
//		}
//		catch (SQLException e)
//		{
//			CommonUtils.printE(e) ;
//		}
//		finally
//		{
//			db.close() ;
//		}
		
		return obj ;
	}
	
	//DBDAO演示示例
	public static void main(String[] args)
	{
//		String sql = "" ;
//		List dataList ;
//		List tempList ;
//		Map tempMap ;
		/**
		//1.查询演示返回List list中元素还是list
		//查询USER表所有字�?
		sql = "SELECT USERNAME,SEX,ADDR FROM USERTEST" ;
		dataList = DBDAO.queryListByPS(sql, null, "list") ;//无参数所以传null
		//遍历list,外层list遍历每条数据
		for(int i = 0 ; i < dataList.size() ; i ++) {
			tempList = (List)dataList.get(i) ;
			//内部list遍历每个字段
			for(int j = 0 ; j < tempList.size() ; j ++) {
				System.out.print(tempList.get(j).toString().concat("    "));
			}
			
			//为每�?��数据换行显示
			System.out.println("");
		}
		*/
		
		/**
		//2.查询演示返回List list中元素是map
		sql = "SELECT USERNAME,SEX,ADDR FROM USERTEST" ;
		
		dataList = DBDAO.queryListByPS(sql, null, "map") ;//无参数所以传null
		
		//遍历list,外层list遍历每条数据
		for(int i = 0 ; i < dataList.size() ; i ++) {
			tempMap = (Map)dataList.get(i) ;
			
			System.out.print(tempMap.get("USERNAME").toString().concat("    "));
			System.out.print(tempMap.get("SEX").toString().concat("    "));
			System.out.print(tempMap.get("ADDR").toString().concat("    "));
			
			//为每�?��数据换行显示
			System.out.println("");
		}
		*/
		/**
		//3.有参数据查询演示返回List list中元素还是list
		sql = "SELECT ID,USERNAME,SEX,ADDR FROM USERTEST WHERE SEX=?" ;
		SqlParameter sqlParams = new SqlParameter() ;//SqlParameter对象类似于ArrayList
		sqlParams.add("�?) ;//第一个参数的值为�?�?��第一个元素为"�?其他元素以此类推
		
		dataList = DBDAO.queryListByPS(sql, sqlParams, "list") ;
		
		for(int i = 0 ; i < dataList.size() ; i ++) {
			tempList = (List)dataList.get(i) ;
			//内部list遍历每个字段
			for(int j = 0 ; j < tempList.size() ; j ++)
			{
				System.out.print(tempList.get(j).toString().concat("    "));
			}
			
			//为每�?��数据换行显示
			System.out.println("");
		}
		 */
		/**
		//4.有参数据查询演示返回List list中元素map
		sql = "SELECT ID,USERNAME,SEX,ADDR FROM USERTEST WHERE SEX='".concat("�?).concat("'") ;
		SqlParameter sqlParams = new SqlParameter() ;//SqlParameter对象类似于ArrayList
		sqlParams.add("�?) ;//第一个参数的值为�?�?��第一个元素为"�?其他元素以此类推
		
		dataList = DBDAO.queryList(sql, "map") ;
		
		for(int i = 0 ; i < dataList.size() ; i ++) {
			tempMap = (Map)dataList.get(i) ;
			
			System.out.print(tempMap.get("USERNAME").toString().concat("    "));
			System.out.print(tempMap.get("SEX").toString().concat("    "));
			System.out.print(tempMap.get("ADDR").toString().concat("    "));
			//为每�?��数据换行显示
			System.out.println("");
		}
		*/
		/**
		//5.有参利用查询单一数据,返回map
		sql = "SELECT ID,USERNAME,SEX,ADDR FROM USERTEST WHERE ID=?" ;
		SqlParameter sqlParams = new SqlParameter(1) ;//SqlParameter也可以这样传参数,不定参数,具体参见SqlParameter类与不定参数写法
		tempMap = (Map) DBDAO.queryObjectByPS(sql, sqlParams, "map") ;//�?��判断是否为空再转�?
		System.out.println(tempMap.get("ID"));
		System.out.println(tempMap.get("USERNAME"));
		System.out.println(tempMap.get("SEX"));
		System.out.println(tempMap.get("ADDR"));
		 */
		/**
		//6.有参利用查询单一数据,返回list
		sql = "SELECT ID,USERNAME,SEX,ADDR FROM USERTEST WHERE ID='1'" ;
		tempList = (List) DBDAO.queryObject(sql, "list") ;//�?��判断是否为空再转�?
		for(int i = 0 ; i < tempList.size() ; i ++) {
			System.out.print(tempList.get(i).toString().concat("    "));
		}
		 */
	}
}
