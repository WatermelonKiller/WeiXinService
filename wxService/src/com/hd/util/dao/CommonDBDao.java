package com.hd.util.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.util.BMAjax;


public class CommonDBDao extends DBDAO {
	
//	@Autowired
//	private DBQ db;
	
	private List lstValue = null;
	/* 组成SQL */
	private StringBuilder sbSQL = null;
	
	/* 组成表列 */
	private StringBuilder sbColumn = null;
	
	/* 组成VALUES ? */
	private StringBuilder sbValue = null;
	
	/* 设置 strSeparatorColumns 数组长度 */
	private int iLength = 0;
	
	private boolean isFinished = false;
	
	/**
	 * @param sql
	 * @return
	 * @throws Exception 
	 * 方法功能: 根据sql语句JDBC查询数据单一结果 以map键�?对形成一条数据，用list集合装载
	 * 			并将对应的数据列名称(键的名称) 形成 , 分隔的字符串
	 * 			用map装载 数据集合list �?键�?的字符串stmt
	 */
	public Map jsonListmap(String sql)throws Exception{
		return jsonListmap(sql,"");
	}
	public Map jsonListmap(String sql,String talog)throws Exception{
		db.DBQuery();
    	Map maps=new HashMap();
    	String stmt="";
    	lstValue = new ArrayList();
        try{
        	if(!"".equals(talog)){
    			db.setCatalog(talog);
    		}
        	boolean md = true;
	        ResultSet rs = db.getResults(sql);
	        ResultSetMetaData rsmd = rs.getMetaData();
	        while(rs.next()){
	            Map map = new HashMap();
	          for(int i=1;i<=  rsmd.getColumnCount();i++){
	              String value = (String) rs.getString(i);
	              if("null".equalsIgnoreCase(value))value="";
	              map.put(rsmd.getColumnLabel(i),value);
	              if(md){
	            	  stmt+=rsmd.getColumnLabel(i)+",";
	              }
	          }
	          lstValue.add(map);
	          md = false;
	        }
        }catch (Exception ex){
        	System.out.println(" Map jsonListmap 方法异常");
		}finally{
			db.close();
		}
        maps.put("list", lstValue);
        maps.put("stmt", stmt);
        return maps;
    }
	/**
	 * @param sql
	 * @return 
	 * 方法功能:  根据sql语句JDBC查询数据单一结果 以map键�?对形成一条数据，用list集合装载返回
	 */
	public List DBjsonList(String sql){
		return DBjsonList(sql,"");
	}
	public List DBjsonList(String sql,String talog){
		if(!"".equals(talog)){
			getJdbcTemplate().setDatabaseProductName(talog);
		}
		lstValue = getJdbcTemplate().queryForList(sql);
//		db.DBQuery();
//		lstValue = new ArrayList();
//        ResultSet rs;
//		try {
//			if(!"".equals(talog)){
//				db.setCatalog(talog);
//			}
//			rs = db.getResults(sql);
//	        ResultSetMetaData rsmd = rs.getMetaData();
//	        while(rs.next()){
//	            Map map = new HashMap();
//	          for(int i=1;i<=  rsmd.getColumnCount();i++){
//	              String value = (String) rs.getString(i);
//	              if("null".equalsIgnoreCase(value))value="";
//	              map.put(rsmd.getColumnLabel(i),value);
//	          }
//	          lstValue.add(map);
//	        }
//		} catch (SQLException e) {
//			System.out.println(" List DBjsonList 方法异常");
//			e.printStackTrace();
//		}finally{
//			db.close();
//		}
        return lstValue;
    }
	/**
	 * @param sql
	 * @param flag
	 * @return 
	 * 方法功能: flag为true情况下，对sql语句进行解密，然后进行查�?
	 */
	public List staticList(String sql,boolean flag){
		if(flag){
			sql = BMAjax.doDesString(sql);
		}
		lstValue = DBjsonList(sql);
		return lstValue;
	}
	
	/**
	 * @param sql
	 * @return 
	 * 方法功能: 根据sql语句JDBC查询数据单一结果 以map键�?对形式返回数�?
	 */
	public Map DBjsonMap(String sql){
		return DBjsonMap(sql,"");
	}
	public Map DBjsonMap(String sql,String talog){
		if(!"".equals(talog)){
			getJdbcTemplate().setDatabaseProductName(talog);
		}
		Map map = null ;
		try{
			map = getJdbcTemplate().queryForList(sql).get(0);
		}catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
//      db.DBQuery();
//      Map map = new HashMap();
//      ResultSet rs;
//		try {
//			if(!"".equals(talog)){
//				db.setCatalog(talog);
//			}
//			rs = db.getResults(sql);
//	        ResultSetMetaData rsmd = rs.getMetaData();
//	        if(rs.next()){
//	          for(int i=1;i<=  rsmd.getColumnCount();i++){
//	              String value = (String) rs.getString(i);
//	              if("null".equalsIgnoreCase(value))value="";
//	              map.put(rsmd.getColumnLabel(i),value);
//	          }
//	        }
//		} catch (SQLException e) {
//			System.out.println(" Map DBjsonMap 方法异常");
//			e.printStackTrace();
//		}finally{
//			db.close();
//		}
        return map;
    }

//	普�?sql语句 执行对库操作方法
	public boolean insorupd(String sql){
		return insorupd_talog(sql,"");
	}
	public boolean insorupd_talog(String sql,String talog){
		isFinished = false;
		db.DBQuery();
		try {
			if(!"".equals(talog)){
				db.setCatalog(talog);
			}
			db.setAutoCommit(false);//设置为不自动提交
			isFinished = db.executeCreate(sql);
			db.commit();//提交事务
		} catch (Exception exception) {
			db.rollback();
			isFinished = false;
		} finally {
			db.close();
		}
		return isFinished;
	}
//	预处�?执行对库操作方法
	public boolean insorupd(String sql,String... values){
		isFinished = false;
		db.DBQuery();
		try {
			db.setAutoCommit(false);//设置为不自动提交
			isFinished = db.operatedb(sql, values);
			db.commit();//提交事务
		} catch (Exception exception) {
			exception.printStackTrace();
			db.rollback();
			isFinished = false;
		} finally {
			db.close();
		}
		return isFinished;
	}
//	执行对库操作 sql语句的数�?
	public boolean insorupdArray(Object... sql){
		isFinished = false;
		db.DBQuery();
		try {
			db.setAutoCommit(false);//设置为不自动提交
			if(sql.length>0){
				for(int i=0;i<sql.length;i++){
					if(sql[i]!=null)isFinished = db.executeCreate((String)sql[i]);
				}
			}
			db.commit();//提交事务
		} catch (Exception exception) {
			db.rollback();
			isFinished = false;
		} finally {
			db.close();
		}
		return isFinished;
	}
//	执行对库操作 sql语句的list集合 执行对库操作方法
	public boolean insorupdArray(List sql){
		return insorupdArray(sql,"");
	}
	//执行对库操作 sql语句的list集合
	public boolean insorupdArray(List sql,String talog){
		isFinished = false;
		db.DBQuery();
		try {
			if(!"".equals(talog)){
				db.setCatalog(talog);
			}
			db.setAutoCommit(false);//设置为不自动提交
			if(!sql.isEmpty()){
				for(int i=0;i<sql.size();i++){
					isFinished = db.executeCreate((String)sql.get(i));
				}
			}
			db.commit();//提交事务
		} catch (Exception exception) {
			db.rollback();
			isFinished = false;
		} finally {
			db.close();
		}
		return isFinished;
	}
//	普�?sql语句 执行对库操作方法 , 返回刚插入的数据的主�?
	public String insorupdrid(String sql){
		String id = "0";
		db.DBQuery();
		try {
			db.setAutoCommit(false);//设置为不自动提交
			id = db.updateRkey(sql);
			db.commit();//提交事务
		} catch (Exception exception) {
			db.rollback();
			id = "-1";
		} finally {
			db.close();
		}
		return id;
	}
	
	/**
	 * @param sql
	 * @return 
	 * 方法功能:执行sql返回�?�� �?��字符�?
	 */
	public String getOneValue(String sql){
		String value = "";
		db.DBQuery();
		try {
			ResultSet rs = db.query(sql);
			if(rs.next()){
				value=rs.getString(1);
			}
		} catch (Exception exception) {
			System.out.println(exception);
		} finally {
			db.close();
		}
		return value;
	}
	public String getOneValue(String sql ,String... params ){
		String value = "";
		try {
			db.DBQuery();
			ResultSet rs = db.getResultSet(sql,params);
			if(rs.next()){
				value=rs.getString(1);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			db.close();
		}
		return value;
	}
	
//	更新表字段�?  参数 :  表名 , 要更新的字段 , 对应的�? , id条件的�?
	public boolean updateTable(String table, String upwhat, String upvalue, String whereid, String wherevalue){
		boolean isFinished = false;
		String sql = "";
		if (table == null||table == "")
			return isFinished;
			
		sql = "update "+table+" set "+upwhat+"='"+upvalue+"' where "+whereid+"='" + wherevalue + "'";
		db.DBQuery();
		try {
			isFinished = db.execute(sql);
		} catch (Exception exception) {
			isFinished = false;
		} finally {
			db.close();
		}
		return isFinished;
	}
//	组成jdbc 更新语句 （value：以字段为键的map对象，names：对应字段名称数组，table：对应数据库表名,主键名称�?
	public String doUpdateSQL(Map value, String[] names, String table,String idvalue) throws Exception {

		/* 组成SQL */
		sbSQL = new StringBuilder();
		/* 组成表列 */
		sbColumn = new StringBuilder();
		/* 组成VALUES ? */
		sbValue = new StringBuilder();
		if(names == null){
			return "";
		}else{
			iLength = names.length;
		}
		/**
		 * 生成 INSERT SQL及获取控件对象�?
		 * */
		if(value != null){
			sbSQL.append("UPDATE ");
			sbSQL.append(table);
			sbSQL.append(" SET ");
			for(int j=0;j<iLength;j++){
				if(!idvalue.equals(names[j])){
					sbColumn.append(",");
					sbColumn.append(names[j]+"='"+value.get(names[j])+"'");
				}
			}
			sbValue.append(" where "+idvalue+"='"+value.get(idvalue)+"'");
			sbSQL.append(sbColumn.toString().replaceFirst(",", ""));
			sbSQL.append(sbValue);
		}
		return sbSQL.toString();
	}
//	组成jdbc 插入语句  （value：以字段为键的map对象，names：对应字段名称数组，table：对应数据库表名�?
	public String doInsertSQL(Map value, String[] names, String table) throws Exception {

		/* 组成SQL */
		sbSQL = new StringBuilder();
		
		/* 组成表列 */
		sbColumn = new StringBuilder();
		
		/* 组成VALUES ? */
		sbValue = new StringBuilder();
			
		if(names == null){
			return "";
		}else{
			iLength = names.length;
		}
		/**
		 * 生成 INSERT SQL及获取控件对象�?
		 * */
		if(value != null){
			
			sbColumn.append("INSERT INTO ");
			sbColumn.append(table);
			sbColumn.append(" (");
			sbValue.append(" VALUES('");
			for(int j=0;j<iLength;j++){
				sbColumn.append(names[j]);
				sbValue.append(value.get(names[j]));
				if(j != iLength -1){
					sbColumn.append(",");
					sbValue.append("','");			
				}
			}

			sbValue.append("')");
			sbColumn.append(")");
			sbSQL.append(sbColumn);
			sbSQL.append(sbValue);
//			System.out.println(sbSQL);
		}
		return sbSQL.toString();
	}
	
	/**
	 * @param table
	 * @param where
	 * @param value
	 * @return 
	 * 方法功能: 执行�?��集合的删除功�?参数: 表名 , where条件(单一), 对应的�?的集�?
	 */
	public boolean DeleteSql(String table, String where, String value) {
		
		if (!"".equals(table)) {
			/* 执行DELETESQL */
			String sql = "delete from " + table ;
			if(!"".equals(where)){
				sql += " where " + where + " in (" + value + ")";
			}
			db.DBQuery();
			try {
				db.setAutoCommit(false);//设置为不自动提交
				isFinished = db.executeCreate(sql);
				db.commit();//提交事务
			} catch (Exception e) {
				e.printStackTrace();
				db.rollback();
				isFinished = false;
			} finally {
				db.close();
			}
		}
		return isFinished;
	}
	
	public String getMaxId(String TableName,String id,String PropertiesName){
		   
		String value="0";
		String sql ="select MAX(CONVERT(SUBSTR("+id+","+(PropertiesName.length()+1)+",LENGTH("+id+")) ,SIGNED)) "+id+" from "+TableName+" where "+id+" like '%"+PropertiesName+"%'";
		ResultSet rs=null;
		db.DBQuery();
		try {
			rs = db.query(sql);
			if(rs.next()){
				value=rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.close();
		}
		return Integer.parseInt(value==null?"0":value)+1+"";
	}
}
