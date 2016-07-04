package com.hd.util.ipage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hd.util.ipage.MPageDialect;
import com.hd.util.ipage.MParamUtil;
import com.hd.util.ipage.Page;
import com.hd.util.ipage.PageUtil;
import com.hd.util.ipage.PropertyFilter;
import com.hd.util.ipage.PropertyFilter2ParamSql;
import com.hd.util.ipage.ReflectionUtils;

/**
 * mybatis分页插件
 * 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class MPagePlugin implements Interceptor {

	private static final Logger log = LoggerFactory.getLogger(MPagePlugin.class);

	private MPageDialect dialect; // 数据库方�?
	private String pageSqlId = ""; // mapper.xml中需要拦截的ID(正则匹配)

	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getTarget() instanceof RoutingStatementHandler) {

			dialect.setDateDialect();

			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectionUtils.getFieldValue(statementHandler,
					"delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectionUtils.getFieldValue(delegate,
					"mappedStatement");

			if (mappedStatement.getSqlCommandType() == SqlCommandType.SELECT
					&& mappedStatement.getId().matches(pageSqlId)) { // 拦截�?��分页的SQL

				BoundSql boundSql = delegate.getBoundSql();

				String sql = boundSql.getSql();

				Object parameterObject = boundSql.getParameterObject();// 分页SQL<select>中parameterType属�?对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为�?
				if (parameterObject instanceof Map) {
					Map map = (Map) parameterObject;
					String paramSql = new PropertyFilter2ParamSql(
							(List<PropertyFilter>) map.get(MParamUtil.PARAM_FILTERS)).convert();
					if (null != paramSql && !"".equals(paramSql)) {
						sql += paramSql;
					}
				}

				Page<? extends Object> page = null;
				if (parameterObject == null) {
					page = new Page<Object>();
					log.error("分页查询的parameterObject尚未实例化！");
				} else {
					Connection connection = (Connection) invocation.getArgs()[0];
					// String countSql = "select count(0) from (" + sql+
					// ") as tmp_count"; //记录统计
					int len = getFromIndex(sql.toLowerCase());
					String countSql = "select count(*) " + sql.substring(len); // 记录统计
					PreparedStatement countStmt = connection.prepareStatement(countSql);
					BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
							boundSql.getParameterMappings(), parameterObject);
					setParameters(countStmt, mappedStatement, countBS, parameterObject);
					ResultSet rs = countStmt.executeQuery();
					int count = 0;
					if (rs.next()) {
						count = rs.getInt(1);
					}
					rs.close();
					countStmt.close();

					if (parameterObject instanceof Page) { // 参数就是Page实体
						page = (Page) parameterObject;
						// 注释
						page.setTotalCount(count);
					} else if (parameterObject instanceof Map) {
						page = (Page) ((Map) parameterObject).get(MParamUtil.PAGE_OBJ_NAME);
						page.setTotalCount(count);
					} else { // 参数为某个实体，该实体拥有Page属�?
						String fieldname = PageUtil.getFieldNameImplPage(parameterObject.getClass());

						if (fieldname != null)
							page = (Page) ReflectionUtils.getFieldValue(parameterObject, fieldname);
						if (page == null)
							page = new Page<Object>();
						page.setTotalCount(count);
						ReflectionUtils.setFieldValue(parameterObject, fieldname, page); // 通过反射，对实体对象设置分页对象
					}
				}
				ReflectionUtils.setFieldValue(boundSql, "sql", dialect.generatePageSql(sql, page)); // 将分页sql语句反射回BoundSql.
			}
		}
		return invocation.proceed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 * 
	 * 
	 * @date 2012-4-12 下午3:05:00
	 */
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 * 
	 * 
	 * @date 2012-4-12 下午3:05:00
	 */
	public void setProperties(Properties properties) {
		setDialect(properties.getProperty("dialect"));
		if (dialect == null)
			log.error("dialect property is not found!");
		pageSqlId = properties.getProperty("pageSqlId");
		if (pageSqlId == null || pageSqlId.equals(""))
			log.error("pageSqlId property is not found!");
	}

	/**
	 * 对SQL参数(?)设�?,参�?org.apache.ibatis.executor.parameter.
	 * DefaultParameterHandler
	 * 
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
							&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(
									propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}

					@SuppressWarnings("unchecked")
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName
								+ " of statement " + mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}

	private static int getFromIndex(String sql) {
		int len = sql.indexOf("from");
		int left = sql.indexOf("(", len);
		int right = sql.indexOf(")", len);
		while (right > 0 && (left < 0 || left > right)) {
			len = sql.indexOf("from", len + 1);
			left = sql.indexOf("(", len);
			right = sql.indexOf(")", len);
		}

		char b = sql.charAt(len - 1);
		if ((b >= '0' && b <= '9') || (b >= 'a' && b <= 'z') || b == '_')
			return len + 4 + getFromIndex(sql.substring(len + 4));
		b = sql.charAt(len + 4);
		if ((b >= '0' && b <= '9') || (b >= 'a' && b <= 'z') || b == '_')
			return len + 4 + getFromIndex(sql.substring(len + 4));
		return len;
	}

	public void setDialect(String dialect) {
		this.dialect = MPageDialect.valueOf(dialect);
	}

	public void setPageSqlId(String pageSqlId) {
		this.pageSqlId = pageSqlId;
	}

}
