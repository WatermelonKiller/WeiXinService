package com.hd.util.ipage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.util.ipage.Page;
import com.hd.util.ipage.PropertyFilter;

/**
 * mybatis动态查询条件工具类.

 */
public class MParamUtil {
	/** page **/
	public static final String PAGE_OBJ_NAME = "page";
	/** param **/
	public static final String PARAM_OBJ_NAME = "param";
	/** paramSql **/
	public static final String PARAM_SQL_OBJ_NAME = "paramSql";

	/** filters **/
	public static final String PARAM_FILTERS = "filters";

	private final Map<String, Object> map = new HashMap<String, Object>();

	private MParamUtil() {
	}

	public static MParamUtil newInstance() {
		return new MParamUtil();
	}

	public MParamUtil addPage(Page<?> page) {
		map.put(MParamUtil.PAGE_OBJ_NAME, page);
		return this;
	}

	public MParamUtil addParam(List<PropertyFilter> filters) {
		this.map.put(MParamUtil.PARAM_FILTERS, filters);
		return this;
	}

	public MParamUtil addParam(Map<String, Object> paramMap) {
		this.map.putAll(paramMap);
		return this;
	}

	public MParamUtil addParam(String key, Object value) {
		map.put(key, value);
		return this;
	}

	public MParamUtil addParamSql(String paramSql) {
		map.put(MParamUtil.PARAM_SQL_OBJ_NAME, paramSql);
		return this;
	}

	public Map<String, Object> result() {
		return map;
	}

}
