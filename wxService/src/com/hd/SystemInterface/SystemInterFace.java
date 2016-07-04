package com.hd.SystemInterface;

import java.io.File;
import java.util.ResourceBundle;

/**
 * 公共Forward 查找页面的对象名称
 * */
public interface SystemInterFace {

	public static final String PROJECT_YJY = "/yjy";

	// 项目 物理路径
	public static final String PHYSICAL_PATH = new File(
			System.getProperty("java.io.tmpdir")).getParentFile()
			.getAbsolutePath();

	/**
	 * 将登录前的URL放到Session中的键名称
	 */
	public static final String LOGIN_TO_URL = "toUrl";

	/**
	 * 用户对象放到Session中的键名称
	 */
	public static final String USER_CONTEXT = "manager";
	public static final String IS_MANAGER = "是";
	public static final String ERROR_MSG_KEY = "errorMsg";

	// 获取登陆到服务器所需要的配置文件
	public static final ResourceBundle rb = ResourceBundle.getBundle("jdbc");
	// 获得配置文件中用于上传文件FTP服务器的IP地址
	public static final String FTP_INIP = rb.getString("ftp.inip");
	// 获得配置文件中用于下载预览http服务器的IP地址
	public static final String FTP_OUTIP = rb.getString("ftp.outip");
	// 获得配置文件中用于上传文件FTP服务器的端口号
	public static final String FTP_INPORT = rb.getString("ftp.inport");
	// 获得配置文件中用于下载预览http服务器的端口号
	public static final String FTP_OUTPORT = rb.getString("ftp.outport");
	// 获得配置文件中用于登陆FTP服务器的用户名
	public static final String FTP_USERNAME = rb.getString("ftp.username");
	// 获得配置文件中用于登陆FTP服务器的密码
	public static final String FTP_PASSWORD = rb.getString("ftp.password");
	// 获得配置文件中用于存储二维码文件路径
	public static final String FTP_CODEIMG = rb.getString("ftp.codeimg");
	// 获得配置文件中用于登陆FTP服务器的路径
	public static final String FTP_PATH = rb.getString("ftp.path");
	// 获得配置文件中用于存储二维码文件路径
	public static final String FTP_SERVERID = rb.getString("ftp.serverid");

	// 用户汇总查询分类条件
	public static final String AYNC_DEP_PID = rb.getString("aync_dep_pid");

	/* 空字符串 */
	public static final String EMPTY = "";

	public static final String SUCCESS = "success";
	/* 删除 ActionForward 转页参数 */
	public static final String DELFW = "delFW";

	/* 查询 ActionForward 转页参数 */
	public static final String SELFW = "selFW";

	/* 修改 ActionForward 转页参数 */
	public static final String UPDFW = "updFW";

	/* 插入 ActionForward 转页参数 */
	public static final String INSFW = "insFW";

	/* 下一页 ActionForward 转页参数 */
	public static final String NEXTFW = "nextFW";

	/* 上一页 ActionForward 转页参数 */
	public static final String PREFW = "preFW";

	/* request.setAttribute()方法 name 名 */
	public static final String LSTVALUE = "value";

	/* XML页面数据邦定 request.setAttribute()方法 name 名 */
	public static final String XMLVALUE = "xml";

	/* 判断删除页面控件按扭 name 名 */
	public static final String DEL = "删除";

	/* 判断录入页面控件按扭 name 名 */
	public static final String INS = "保存";

	/* 判断修改页面控件按扭 name 名 */
	public static final String UPD = "修改";

	/* 判断查询页面控件按扭 name 名 */
	public static final String SEL = "查询";

	/* 判断下一页页面控件按扭 name 名 */
	public static final String NEXT = "下一页";

	/* 判断上一页页面控件按扭 name 名 */
	public static final String PRE = "上一页";

	// 数据库驱动mysql
	String mySqlDriver = "org.gjt.mm.mysql.Driver";

	// mssql Jtds驱动
	String mssqlDriver = "net.sourceforge.jtds.jdbc.Driver";

	// Oracle驱动
	String oracleDriver = "oracle.jdbc.driver.OracleDriver";

	// Oracle查表名
	String tableName_Oracle = "SELECT table_name FROM all_tables where owner=?";

	// Oracle查字段
	String columnName_Oracle = "SELECT column_name,data_type FROM all_tab_columns where table_name=? ";

	// MsSql2000查库名
	String dataBaseName_MsSql2000 = "select name from sysdatabases order by name ";

	// MsSql2000查表名
	String tableName_MsSql2000 = "select name from sysobjects where type='U' and category = 0  order by name";

	// MsSql2000查字段
	String columnName_MsSql2000 = "SELECT c.name as ColumnName,bt.name as BaseTypeName from syscolumns c INNER JOIN systypes st on st.xusertype = c.xusertype INNER JOIN "
			+ "systypes bt on bt.xusertype = c.xtype INNER JOIN sysobjects o on o.id=c.id WHERE c.number = 0 and c.name IS NOT NULL and o.name=?  order by ColumnName";

	// MsSql2005查库名
	String dataBaseName_MsSql2005 = "select name from sys.sysdatabases order by name";

	// MsSql2005查表名
	String tableName_MsSql2005 = "select name from sysobjects where type='U' and category = 0  order by name";

	// MsSql2005查字段
	String columnName_MsSql2005 = "SELECT c.name as ColumnName,bt.name as BaseTypeName from sys.syscolumns c INNER JOIN sys.systypes st on st.xusertype = c.xusertype INNER JOIN "
			+ "sys.systypes bt on bt.xusertype = c.xtype INNER JOIN sys.sysobjects o on o.id=c.id WHERE c.number = 0 and c.name IS NOT NULL and o.name=?  order by ColumnName";

	// MySql查库名
	String dataBaseName_MySql = "show databases";

	// MySql查表名
	String tableName_MySql = "show tables";

	// MySql查字段
	String columnName_MySql = "select column_name,data_type from information_schema.columns where table_name=?";

	public static final String IN_USE = "启用";
	public static final String NOT_IN_USE = "禁用";

	public static final String DBO = "dbo.";
	public static final String DB_WX_DBO = "dd_wx." + DBO;
	public static final String TAB_COLUMN = DB_WX_DBO + "tab_column"; // 系统表
	public static final String TAB_DEPT=DB_WX_DBO+"tab_dept";//部门表
	public static final String TAB_USER = DB_WX_DBO + "tab_user";// 人员表
	public static final String TAB_ROLE_USER = DB_WX_DBO + "tab_role_column"; // 人员权限表
	public static final String TAB_SYSTEM = DB_WX_DBO + "tab_system";// 系统表

	public static final String TAB_ROLE_COLUMN_VIEW = DB_WX_DBO
			+ "hd_view_tab_role_column"; // 角色栏目关系表视图
}
