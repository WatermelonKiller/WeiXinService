package com.hd.util.ipage;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.SQLServer2008Dialect;

import com.hd.util.ipage.Page;

/**
 * mybatis自定义方言类.
 * 
 */
public enum MPageDialect {

	mysql {
		@Override
		public String generatePageSql(String sql, Page<? extends Object> page) {
			StringBuffer pageSql = new StringBuffer(sql);
			if (page != null)
				pageSql.append(" limit " + page.getFirst() + "," + page.getPageSize());
			return pageSql.toString();
		}

		@Override
		public void setDateDialect() {
			// TODO mysql时间类型方言等待写入

		}

	},
	oracle {
		@Override
		public String generatePageSql(String sql, Page<? extends Object> page) {
			StringBuffer pageSql = new StringBuffer(sql);
			if (page != null) {
				pageSql.insert(0, "select * from (select tmp_tb.*,ROWNUM row_id from (")
						.append(") tmp_tb where ROWNUM<=").append(page.getFirst() + page.getPageSize())
						.append(") where row_id>").append(page.getFirst());
			}
			return pageSql.toString();
		}

		@Override
		public void setDateDialect() {
			// TODO oracle时间类型方言等待写入

		}

	},
	sqlServer {
		@Override
		public String generatePageSql(String sql, Page<? extends Object> page) {
			if (page != null) {
				Dialect dialect = null;
				dialect = new SQLServer2008Dialect();
				sql = dialect.getLimitString(sql, 5, 5);
				sql = this.setPageParam(sql, page);
			}
			String sqlBefore = sql.substring(0, 50);
			String sqlLater = sql.substring(50);
			String relpaceString = sqlLater.substring(0, sqlLater.indexOf(")"));
			sqlLater = sqlLater.replaceFirst(relpaceString, page.getOrderBy() + " " + page.getOrder());
			sql = sqlBefore + sqlLater;
			return sql;
		}

		@Override
		public void setDateDialect() {
			dateDialect.put("castDateTime", "cast('{0}' as datetime)");
			dateDialect.put("convertTime", "convert(varchar(50),{0},120)");
		}

	};

	protected String setPageParam(String sql, Page<? extends Object> page) {
		StringBuilder sb = new StringBuilder(sql);
		sb.replace(sb.lastIndexOf("?"), sb.lastIndexOf("?") + 1, +page.getFirst() + page.getPageSize() - 1 + "");
		sb.replace(sb.lastIndexOf("?"), sb.lastIndexOf("?") + 1, page.getFirst() + "");
		return sb.toString();
	}

	public abstract String generatePageSql(String sql, Page<? extends Object> page);

	public abstract void setDateDialect();

	public static final Map<String, String> dateDialect = new HashMap<String, String>();
}
