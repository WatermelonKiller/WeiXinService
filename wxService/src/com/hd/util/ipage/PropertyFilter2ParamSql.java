package com.hd.util.ipage;

import java.util.Date;
import java.util.List;

import com.hd.util.ipage.PropertyFilter;
import com.hd.util.ipage.PropertyFilter.MatchType;
import com.hd.util.ipage.PropertyFilter.PropertyType;
import com.hd.util.ipage.DateUtil;

/**
 * 动态查询条件拼装,暂时时间类型参数只支持sqlserver

 * 
 */
public class PropertyFilter2ParamSql {

	private static final String CONN_AND = " and ";

	private StringBuffer paramSql = new StringBuffer();
	private List<PropertyFilter> propertyFilters;

	public PropertyFilter2ParamSql(List<PropertyFilter> propertyFilters) {
		this.propertyFilters = propertyFilters;
	}

	public String convert() {
		for (PropertyFilter propertyFilter : propertyFilters) {
			Class<?> propertyType = propertyFilter.getPropertyType();
			if (PropertyType.S.getValue().equals(propertyType)) {
				setterStringProperty(propertyFilter);
			} else if (PropertyType.D.getValue().equals(propertyType)) {
				setterDateProperty(propertyFilter);
			} else {
				setterNumberProperty(propertyFilter);
			}
		}
		return paramSql.toString();
	}

	private void setterStringProperty(PropertyFilter propertyFilter) {
		MatchType matchType = propertyFilter.getMatchType();
		if (MatchType.EQ.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append("='").append(
					propertyFilter.getPropertyValue()).append("' ");
		} else if (MatchType.LIKE.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append(" like '%").append(
					propertyFilter.getPropertyValue()).append("%' ");
		} else if (MatchType.LE.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append("<='").append(
					propertyFilter.getPropertyValue()).append("' ");
		} else if (MatchType.LT.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append("<'").append(
					propertyFilter.getPropertyValue()).append("' ");
		} else if (MatchType.GE.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append(">='").append(
					propertyFilter.getPropertyValue()).append("' ");
		} else if (MatchType.GT.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append(">'").append(
					propertyFilter.getPropertyValue()).append("' ");
		}
	}

	private void setterNumberProperty(PropertyFilter propertyFilter) {
		MatchType matchType = propertyFilter.getMatchType();
		if (MatchType.EQ.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append("=").append(
					propertyFilter.getPropertyValue());
		} else if (MatchType.LE.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append("<=").append(
					propertyFilter.getPropertyValue());
		} else if (MatchType.LT.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append("<").append(
					propertyFilter.getPropertyValue());
		} else if (MatchType.GE.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append(">=").append(
					propertyFilter.getPropertyValue());
		} else if (MatchType.GT.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append(">").append(
					propertyFilter.getPropertyValue());
		}
	}

	/**
	 * 时间类型查询条件紧支持sqlserver暂时.
	 * 
	 * @param propertyFilter
	 */
	private void setterDateProperty(PropertyFilter propertyFilter) {
		MatchType matchType = propertyFilter.getMatchType();
		String dateString = DateUtil.getDate((Date) propertyFilter.getPropertyValue(), DateUtil.NORM_DATE_FORMAT);

		String castTime = MPageDialect.dateDialect.get("castDateTime");
		String convertTime = MPageDialect.dateDialect.get("convertTime");

		String dateParam = castTime.replace("{0}", dateString);

		if (MatchType.EQ.equals(matchType)) {
			paramSql.append(CONN_AND).append(convertTime.replace("{0}", propertyFilter.getPropertyName())).append("=").append(dateParam);
		} else if (MatchType.LIKE.equals(matchType)) {
			paramSql.append(CONN_AND).append(convertTime.replace("{0}", propertyFilter.getPropertyName())).append(
					" like '%").append(dateString.substring(0, 10)).append("%' ");
		} else if (MatchType.LE.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append("<=").append(dateParam);
		} else if (MatchType.LT.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append("<").append(dateParam);
		} else if (MatchType.GE.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append(">=").append(dateParam);
		} else if (MatchType.GT.equals(matchType)) {
			paramSql.append(CONN_AND).append(propertyFilter.getPropertyName()).append(">").append(dateParam);
		}
	}
}
