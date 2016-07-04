package com.hd.SystemBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * 公共查询后分页方�?
 * */

@Repository
@Scope(value = "prototype")
public class AllPageBean extends PageQuery{
	
	int currentPage = 1;// 当前页数

	public int totalPages = 0;// 总页�?

	int pageRecorders = 10;// 每页显示行数

	public int totalRows = 0;// 总行�?

	int pageStartRow = 0;// 每页的起始行�?

	int pageEndRow;// 每页的终止行�?

	public boolean hasNextPage = false;// 是否有下�?��

	public boolean hasPreviousPage = false;// 是否有前�?��
	
	private String SearcSql="";
	
	private String SearcFlag=""; //查询类别 jdbc 数据库语�? hql hibernate语句  sql hibernate对应数据库语�?
	
	private List arrayList = null;

	private Iterator it = null;
	
	private String htmlpage = null; //自定义分页标签的html内容 

	/**
	 * 服务器加载时实例�?
	 */
	public AllPageBean() {
	}
	
	public void AllPageBean(String sql ,String SearcFlag ,int pagecount ,String htmlpage) {
		
		if(pagecount>0){
			pageRecorders = pagecount ;
		}
		this.SearcSql = sql;
		this.SearcFlag = SearcFlag;
		this.htmlpage = htmlpage;
		if(SearcFlag.equals("sql")){
			totalRows = getRowCountSql(SearcSql);
		}else if(SearcFlag.equals("hql")){
			totalRows = getRowCountHql(SearcSql);
		}else if(SearcFlag.equals("jdbc")){
			totalRows = getRowCountJdbc(SearcSql);
		}

		hasPreviousPage = false;

		currentPage = 1;

		if ((totalRows % pageRecorders) == 0) {

			totalPages = totalRows / pageRecorders;

		}else {

			totalPages = totalRows / pageRecorders + 1;

		}

		/** 根据当前页数 和�?页数 判断 是否有下�?�� */
		if (currentPage >= totalPages) {

			hasNextPage = false;

		}else {

			hasNextPage = true;

		}

		if (totalRows < pageRecorders) {

			this.pageStartRow = 0;

			this.pageEndRow = totalRows;

		}else {

			this.pageStartRow = 0;

			this.pageEndRow = pageRecorders;

		}

	}

	public void AllPageBean(List arrayList ,String SearcFlag ,int pagecount ,String htmlpage) {
		if(pagecount>0){
			pageRecorders = pagecount ;
		}
		this.arrayList = arrayList;
		this.SearcFlag = SearcFlag;
		totalRows = arrayList.size();

		it = arrayList.iterator();

		hasPreviousPage = false;

		currentPage = 1;

		if ((totalRows % pageRecorders) == 0) {

			totalPages = totalRows / pageRecorders;

		}else {

			totalPages = totalRows / pageRecorders + 1;

		}

		/** 根据当前页数 和�?页数 判断 是否有下�?�� */
		if (currentPage >= totalPages) {

			hasNextPage = false;

		}else {

			hasNextPage = true;

		}

		if (totalRows < pageRecorders) {

			this.pageStartRow = 0;

			this.pageEndRow = totalRows;

		}else {

			this.pageStartRow = 0;

			this.pageEndRow = pageRecorders;

		}

	}
	
	public void setCurrentPage(int currentPage) {

		this.currentPage = currentPage;

	}

	public void setPageRecorders(int pageRecorders) {

		this.pageRecorders = pageRecorders;

	}

	public void setHasNextPage(boolean hasNextPage) {

		this.hasNextPage = hasNextPage;

	}

	public void setHasPreviosPage(boolean hasPreviousPage) {

		this.hasPreviousPage = hasPreviousPage;

	}

	public String getCurrentPage() {

		return this.toString(currentPage);

	}

	public String getTotalPages() {

		return this.toString(totalPages);

	}

	public String getTotalRow() {

		return this.toString(totalRows);

	}

	public int getPageRecorders() {

		return pageRecorders;

	}

	public int getPageEndRow() {

		return pageEndRow;

	}

	public int getPageStartRow() {

		return pageStartRow;

	}

	public boolean isHasNextPage() {

		return hasNextPage;

	}

	public boolean isHasPreviousPage() {

		return hasPreviousPage;

	}

	public List getNextPage() {

		currentPage = currentPage + 1;

		if ((currentPage - 1) > 0) {

			hasPreviousPage = true;

		}

		else {

			hasPreviousPage = false;

		}

		if (currentPage >= totalPages) {

			hasNextPage = false;

		}

		else {

			hasNextPage = true;

		}

		List list = getBooks();

		return list;

	}

	public List getPreviousPage() {

		currentPage = currentPage - 1;

		if (currentPage == 0) {

			currentPage = 1;

		}

		if (currentPage >= totalPages) {

			hasNextPage = false;

		}

		else {

			hasNextPage = true;

		}

		if ((currentPage - 1) > 0) {

			hasPreviousPage = true;

		}

		else {

			hasPreviousPage = false;

		}

		List list = getBooks();

		return list;

	}

	public List getIndexPage(String leafNumber) {

		currentPage = Integer.valueOf(leafNumber.trim());

		if (currentPage == 0) {
			currentPage = 1;

		}

		if (currentPage >= totalPages) {
			hasNextPage = false;

		}

		else {
			hasNextPage = true;
		}

		if ((currentPage - 1) > 0) {
			hasPreviousPage = true;

		}

		else {
			hasPreviousPage = false;

		}

		List list = getBooks();

		return list;

	}

	/**
	 * .----------------------------------------------------------------------------------------------------------- * * *
	 * -----------------------------------------------------------------------------------------------------------
	 */

	public List getBooks() {
		if (currentPage * pageRecorders < totalRows) {

			pageEndRow = currentPage * pageRecorders;

			pageStartRow = pageEndRow - pageRecorders;

		} else {

			pageEndRow = totalRows;

			pageStartRow = pageRecorders * (totalPages - 1);

		}
		List list = new ArrayList();
//		flag分页形式("sql","hql","jdbc","list")
		if(SearcFlag.equals("list")){
			//根据list集合分页
			for (int i = pageStartRow; i < pageEndRow; i++) {
				if (arrayList.size() == 0) {
					return null;
				}
				list.add(arrayList.get(i));				
			}
		}else if(SearcFlag.equals("sql")){
//			根据hibernate的sql查询分页
			list = querySqlList(SearcSql, pageStartRow, pageRecorders);
		}else if(SearcFlag.equals("hql")){
//			根据hibernate的hql查询分页
			list = queryHqlList(SearcSql, pageStartRow, pageRecorders);
		}else if(SearcFlag.equals("jdbc")){
//			根据jdbc的sql查询分页
			list = queryJdbcList(SearcSql, pageStartRow, pageRecorders);
		}

		return list;
	}

	public String toString(int temp) {

		String str = Integer.toString(temp);

		return str;

	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public void setPageEndRow(int pageEndRow) {
		this.pageEndRow = pageEndRow;
	}

	public void setPageStartRow(int pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public String getSearcSql() {
		return this.SearcSql;
	}

	public void setSearcSql(String searcSql) {
		this.SearcSql = searcSql;
	}

	public String getHtmlpage() {
		return htmlpage;
	}

	public void setHtmlpage(String htmlpage) {
		this.htmlpage = htmlpage;
	}
}
