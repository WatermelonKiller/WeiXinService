package com.hd.util.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hd.util.CommonUtils;
import com.hd.util.SqlParameter;


public class DBQ {
	
	private Connection con = null;//数据库连接对�?
	private Statement st = null;  //数据库操作对�?
	private PreparedStatement pst = null;//预处理操作对�?
	private CallableStatement cst = null;//调用存储过程对象
	private ResultSet rs = null;//结果�?
	
	@Autowired
	public DataSource dataSource;
	
	private static int activeCount = 0;
	
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
	public DBQ() {	 
	}
	
	//加载数据库驱�?
	public void DBQuery(){
		try
		{
			//演示示例�?用当前连�?Test为测试数据库
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//con = DriverManager.getConnection("jdbc:sqlserver://192.168.2.196:1433;databaseName=Test","sa","999888");
			
			//正常发布时�?用下面的连接
			con = dataSource.getConnection();
			//调用输出数据库连接数方法
//			showSnapshotInfo();
		} catch (SQLException e) {
			CommonUtils.printE(e) ;
		}
		
		CommonUtils.deBug("---创建Connection---");
	}
	/**
	 * @param talog 
	 * 方法功能: 设置不同�?
	 */
	public void setCatalog(String talog) {
		try {
			con.setCatalog(talog);
		} catch (SQLException e) {
			CommonUtils.deBug("---设置"+talog+"库失�?--");
			e.printStackTrace();
		}
	}
	//输出数据库连接状�?
	public static void showSnapshotInfo()
	{
		try
		{
			SnapshotIF snapshot = ProxoolFacade.getSnapshot("proxool-pool", true);
			int curActiveCount = snapshot.getActiveConnectionCount();
			int availableCount = snapshot.getAvailableConnectionCount();
			int maxCount = snapshot.getMaximumConnectionCount();
			if (curActiveCount != activeCount)
			{
				CommonUtils.deBug("活动连接�?" + curActiveCount + "(active)  可得到的连接�?" + availableCount + "(available)  总连接数:" + maxCount + " (max)");
				activeCount = curActiveCount;
			}
		}
		catch (ProxoolException e)
		{
			e.printStackTrace();
		}
	}
	
	public DBQ(String IP) {	 
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//127.0.0.1:3306
			con = DriverManager.getConnection("jdbc:sqlserver://"+IP+":1433;","sa","999888");
		} 
		catch (Exception e)
		{
		}
		CommonUtils.deBug("---创建Connection---");
	}
	
	/**
	 * @return Connection数据库连接对�?
	 * 方法功能:获取Connection
	 */
	public Connection getConnection()
	{
		return con;
	}
	
	/**
	 * @return Statement对象
	 * @throws SQLException 
	 * 方法功能:获取当前连接的Statement对象
	 */
	public Statement getStatement() throws SQLException{
		st = con.createStatement();
		return st;
	}

	/**
	 * @param sql 要执行的sql
	 * @return ResultSet 结果�?
	 * @throws SQLException 
	 * 方法功能:查询方法
	 */
	public ResultSet query(String sql) throws SQLException
	{
		st = con.createStatement();
		rs = st.executeQuery(sql);
		return rs;
	}
	
//	数据库查询方�?
	public ResultSet getResults(String sql) throws SQLException
	{
		st = con.createStatement();
		rs = st.executeQuery(sql);
		return rs;
	}
	
	//预处理查询方�?
	public ResultSet getResultSet(String sql, String... values) throws SQLException {
		pst = con.prepareStatement(sql);
		for (int i = 0; i < values.length; i++) {
			pst.setString(i + 1, values[i]);
		}
		rs = pst.executeQuery();
		return rs;
	}
//	预处�?插入删除修改方法
	public boolean operatedb(String sql, String[] values) throws SQLException {
		boolean isFinished = false;
		pst = con.prepareStatement(sql);
		for (int i = 0; i < values.length; i++) {
			pst.setString(i + 1, values[i]);
		}
		int p = pst.executeUpdate();
		if(p==-1){
			isFinished = false;
		}else{
			isFinished = true;
		}
		return isFinished;
	}
	
	//数据库修改方�?
	public int update(String sql) throws SQLException
	{
		st = con.createStatement();
		int i  = st.executeUpdate(sql);
		return i;
	}
	/**
	 * @return 
	 * @throws SQLException 
	 * 方法功能: 执行sql对库操作，并返回主键  PreparedStatement 预处理对�?
	 */
	public String updateRkey(String sql) throws SQLException
	{
		pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pst.executeUpdate(sql);
		rs = pst.getGeneratedKeys();
		String i = "0";
		if(rs.next()){
			i = rs.getString(1);
		}
		return i;
	}
	public String updateRkey(String sql ,SqlParameter sqlParams) throws SQLException
	{
		pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		executeSQL(pst,sqlParams);//调用预处理执行方�?
		rs = pst.getGeneratedKeys();
		String i = "0";
		if(rs.next()){
			i = rs.getString(1);
		}
		return i;
	}
	
	public boolean executeCreate(String sql) throws SQLException
	{	
		boolean isFinished = false;
		st = con.createStatement();
		int i  = st.executeUpdate(sql);
		if(i==-1){
			isFinished = false;
		}else{
			isFinished = true;
		}
		return isFinished;
	}
	public boolean execute(String sql) throws SQLException
	{	
		boolean isFinished = false;
		st = con.createStatement();
		int i  = st.executeUpdate(sql);
		if(i==-1){
			isFinished = false;
		}else{
			isFinished = true;
		}
		return isFinished;
	}
	/**
	 * @param sql 要执行的sql
	 * @return PreparedStatement 预处理对�?
	 * @throws SQLException 
	 * 方法功能:获取预处理对�?
	 */
	public PreparedStatement getPreparedStatement(String sql) throws SQLException
	{
		pst = con.prepareStatement(sql);
		return pst;
	}
	
	/**
	 * @param sql 要执行的sql语句
	 * @param resultSetType
	 * @param resultSetConcurrency
	 * @return PreparedStatement 预处理对�?
	 * @throws SQLException 
	 * 方法功能:获取�?��执行查询后可滑动游标的结果集
	 */
	public PreparedStatement getPreparedStatement(String sql,int resultSetType,int resultSetConcurrency) throws SQLException
	{
		pst = con.prepareStatement(sql, resultSetType, resultSetConcurrency);
		return pst;
	}
	
	/**
	 * @param sql
	 * @return CallableStatement 调用存储过程对象
	 * @throws SQLException 
	 * 方法功能:获得调用存储过程对象
	 */
	public CallableStatement getCallableStatement(String sql) throws SQLException
	{
		cst = con.prepareCall(sql);
		return cst;
	}
	
	/** 
	 * 方法功能:关闭当前数据库连接所有对�?
	 */
	public void close(){
		try
		{
			if(rs != null )
			{
				rs.close();
				rs = null;
			}
			if(st != null )
			{
				st.close();
				st = null;
			}
			if(pst != null )
			{
				pst.close();
				pst = null;
			}
			if(cst != null )
			{
				cst.close();
				cst = null;
			}
			if(con != null )
			{
				con.close();
				con = null;
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		CommonUtils.deBug("---关闭Connection---");
	}
	
	/**
	 * @param flag false不启动自动提�?true自动提交(默认为自动提�?
	 * 方法功能:设置数据库事务自动提�?
	 */
	public void setAutoCommit(boolean flag)
	{
		try
		{
			con.setAutoCommit(flag) ;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/** 
	 * 方法功能:提交当前数据库执行的操作
	 */
	public void commit()
	{
		try
		{
			con.commit() ;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 方法功能:回滚当前数据库执行的操作
	 */
	public void rollback()
	{
		try
		{
			con.rollback() ;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @param sqlList 要执行语句的集合,可传list与vector
	 * @return 执行成功的数�?-1未失�?
	 * @throws SQLException 
	 * 方法功能:批量执行sql
	 */
	public int execute(Collection sqlList) throws SQLException
	{
		int flag = 0 ;
		boolean reFlag = false ;
		
		//判断list是否为空
		if(sqlList == null || sqlList.size() ==0)
		{
			return -1 ;
		}
		else
		{
			//设置事物
			this.setAutoCommit(false) ;
			
			//取得迭代�?
			Iterator it = sqlList.iterator() ;
			
			while(it.hasNext())
			{
				//取得每条sql语句
				reFlag = this.execute(it.next().toString()) ;
				if(reFlag)
				{
					flag ++ ;
				}
				else
				{
					//执行失败跳出
					this.rollback() ;
					
					flag = -1 ;
					
					break ;
				}
			}
			
			if(flag >= 0)
			{
				this.commit() ;
			}
		}
		
		return flag ;
	}
	
	/**
	 * @param pst 预处理对�?
	 * @param sqlParams 参数集合
	 * 方法功能:将sql语句的参数放置到预处理中
	 */
	private void setParameters(PreparedStatement pst, SqlParameter sqlParams) 
	{
		//判断SqlParameter是否为空
		if (sqlParams != null && !sqlParams.isEmpty()) 
		{
			for (int i = 1; i <= sqlParams.size() ; i++) 
			{
				try	{
					//将参数放入到pst对象�?
					pst.setObject(i, sqlParams.get(i-1));
				}
				catch (SQLException e)
				{
					CommonUtils.printE(e) ;
				}
			}
		}
	}
	
	/**
	 * @param rs 要遍历的结果�?
	 * @return List 当前行的数据集合
	 * 方法功能:将结果集当前行变为List
	 */
	private List rsToList(ResultSet rs)
	{
		List dataList = new ArrayList() ; 
		int count = 0 ; //共有多少�?
		try
		{
			ResultSetMetaData rsmd = rs.getMetaData();
			count = rsmd.getColumnCount() ;
			
			//遍历rs当前�?
			for(int i = 1 ; i <= count ; i ++)
			{
				dataList.add(rs.getString(i)) ;
			}
		}
		catch (SQLException e)
		{
			CommonUtils.printE(e) ;
		}
		return dataList ;
	}
	
	/**
	 * @param rs 要遍历的结果�?
	 * @return Map 当前行的记录
	 * 方法功能:将rs当前行放入一个map中key为列�?value为字段�?
	 */
	private Map rsToMap(ResultSet rs)
	{
		Map dataMap = new HashMap() ;
		String tempValue = null ;
		int count = 0 ; //共有多少�?
		
		try
		{
			ResultSetMetaData rsmd = rs.getMetaData();
			count = rsmd.getColumnCount() ;
			
			//遍历rs当前�?
			for(int i = 1 ; i <= count ; i ++)
			{
				tempValue = rs.getString(rsmd.getColumnLabel(i)) ;
				
				///判断当前列�?是否为null如果是null将其转换�?",避免toString的空指针
				if(tempValue == null)
				{
					tempValue = "" ;
				}
				//将rs当前行放入一个map中key为列�?value为字段�?
				dataMap.put(rsmd.getColumnLabel(i), tempValue) ;
			}
		}
		catch (SQLException e)
		{
			CommonUtils.printE(e) ;
		}
		return dataMap ;
	}
	
	/**
	 * @param pst 预处理对�?
	 * @param sqlParams 参数集合,无参数传null或�?空的SqlParameter都可�?
	 * @param flag 该参数有2个�?,�?��为list,�?��为map(默认为map) list代表返回的list中每个子元素都是list,每个子元素都是rs的一条记�?map代表返回的list中每个子元素都是map,每个子元素都是rs的一条记�?
	 * @return List数据集合
	 * 方法功能:利用预处理查询当前sql语句,按照flag要求返回结果(推荐使用)
	 */
	public List queryListByPS(PreparedStatement pst,SqlParameter sqlParams,String flag)
	{
		this.setParameters(pst, sqlParams) ;//设置参数
		List dataList = new ArrayList() ;
		
		try
		{
			ResultSet rs = pst.executeQuery() ;
			
			while(rs.next())
			{
				//根据flag判断应该用那个方法取�?
				if("list".equals(flag))
				{
					dataList.add(this.rsToList(rs)) ;
				}
				else
				{
					dataList.add(this.rsToMap(rs)) ;
				}
			}
		}
		catch (SQLException e)
		{
			CommonUtils.printE(e) ;
		}
		
		return dataList ;
	}
	/**
	 * @param pst 预处理对�?
	 * @param sqlParams 参数
	 * @param flag 是返回list还是map
	 * @return Object根据flag进行转型
	 * 方法功能:预处理查询单�?��象根据flag判断返回的是map还是list
	 */
	public Object queryObjectByPS(PreparedStatement pst,SqlParameter sqlParams,String flag)
	{
		this.setParameters(pst, sqlParams) ;//设置参数
		Object obj = null ;
		
		try
		{
			ResultSet rs = pst.executeQuery() ;
			
			if(rs.next())
			{
				//根据flag判断应该用那个方法取�?
				if("list".equals(flag))
				{
					obj = this.rsToList(rs) ;
				}
				else
				{
					obj = this.rsToMap(rs) ;
				}
			}
		}
		catch (SQLException e)
		{
			CommonUtils.printE(e) ;
		}
		return obj ;
	}
	
	/**
	 * @param pst 预处理对�?
	 * @param sqlParams 参数
	 * @return true成功,false失败
	 * 方法功能:利用预处理方法执行sql语句,�?�?改功�?
	 */
	public boolean executeSQL(PreparedStatement pst,SqlParameter sqlParams)
	{
		boolean flag = true ;
		
		this.setParameters(pst, sqlParams) ;//设置参数
		
		try
		{
			int i = pst.executeUpdate() ;
			
			if(i < 0)
			{
				flag = false ;
			}
		}
		catch (SQLException e)
		{
			CommonUtils.printE(e) ;
		}
		
		return flag ;
	}
}
