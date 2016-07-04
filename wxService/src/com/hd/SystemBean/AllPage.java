package com.hd.SystemBean;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.hd.SystemInterface.SystemInterFace;
import com.hd.util.BMAjax;
import com.hd.util.CommonFunc;

//import com.forexception.all.Forlog;

/**
 * 公共查询后分页方法
 * */
public class AllPage extends TagSupport implements SystemInterFace {

	String parts = "/parts/images/paging/";

	String nextPage = CommonFunc.PROJECT_YJY + parts + "next.jpg";// 定义下一页图片按钮

	String previousPage = CommonFunc.PROJECT_YJY + parts + "previous.jpg";// 定义上一页图片按钮

	String first = CommonFunc.PROJECT_YJY + parts + "first.jpg";// 定义首页图片按钮

	String end = CommonFunc.PROJECT_YJY + parts + "end.jpg";// 定义尾页图片按钮

	String turn = CommonFunc.PROJECT_YJY + parts + "go.jpg";// 定义转图片按钮

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			ServletRequest request = pageContext.getRequest();
			pageContext.getOut().println(getPage(request));

		} catch (Exception e) {
			// Forlog.inslog(this.getClass(), e);
		}
		return super.doEndTag();
	}

	public String getPage(ServletRequest request) {
		AllPageBean apb = (AllPageBean) request.getAttribute("page");
		StringBuffer page = new StringBuffer();
		if (apb.currentPage > apb.totalPages) {
			apb.setCurrentPage(apb.totalPages);
		}
		if (apb.getHtmlpage() != null && !apb.getHtmlpage().isEmpty()) {
			// 自定义分页标签的html内容
			page.append(apb.getHtmlpage());
			// 当前页面的页码
			page.append("<input id='page' name='page' type='hidden' value='"
					+ apb.getCurrentPage() + "' />");
		} else {
			// 默认分页样式
			page.append("<div class=\"row\" style=\"width:99%;\"><div class=\"col-lg-6\"><style type=\"text/css\">a:link{color:#333; text-decoration:none;}a:visited{color:#333; text-decoration:none;}</style>");
			page.append("<div class=\"input-group\">");
			page.append("<input type=\"hidden\" name=\"onePageCount\" class=\"total\" ");
			page.append("  onkeyup=\"value=value.replace(/[^\\d]/g,\'\')\" value=\"");
			page.append(apb.pageRecorders);
			page.append("\"/>");
			page.append("当前第<span class=\"current_page\">");
			page.append(apb.getCurrentPage());
			page.append("</span>/<span>");
			page.append(apb.getTotalPages());
			page.append("</span>页。");
			page.append("<a href=\"#\" name=\"PgUp\" class=\"btn btn-default\" title=\"上一页\"  ");
			if (Integer.valueOf(apb.getCurrentPage()) != 1) {
				page.append("onClick=\"previousPage()\"");
			}
			page.append(">上一页</a>");
			page.append("<a href=\"#\" name=\"PgUp\" class=\"btn btn-default\" title=\"下一页\"");
			if (!apb.getCurrentPage().equals(apb.getTotalPages())) {
				page.append("onClick=\"nextPage()\"");
			}
			page.append(">下一页</a>");
			page.append("<input type=\"hidden\" class=\"form-control\" style=\"width:40px\" id=\"page\" name=\"page\" onkeyup=\"value=value.replace(/[^\\d]/g,\'\')\" ");
			page.append("value=\"" + apb.getCurrentPage() + "\">");
			page.append(" </div>");
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			page.append("<input id='npage' name='npage' type='hidden' /><input type='hidden' id='leafNumber' name='leafNumber'/>");
			page.append("<input type=\"hidden\" name=\"thisUrl\" id=\"thisUrl\" value=\""
					+ httpRequest.getSession().getAttribute("thisUrl")
					+ "\">"
					+ "<input type=\"hidden\" id=\"order\" name=\"order\" value=\""
					+ request.getAttribute("order")
					+ "\">"
					+ "<input type=\"hidden\" name=\"sqlprint\" value=\""
					+ BMAjax.doEncString(apb.getSearcSql())
					+ "\">"
					+ "<input type=\"hidden\" id=\"taxis\" name=\"taxis\" value=\""
					+ request.getAttribute("taxis") + "\">");
			page.append("<input type=\"hidden\" name=\"allPageNumber\" id=\"allPageNumber\" value=\""
					+ apb.getTotalPages() + "\"/>");
			page.append("</h4></div></div>");

		}
		return page.toString();
	}
}
