package com.hd.SystemAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hd.SystemBean.AllPageBean;
import com.hd.SystemBean.UserBean;
import com.hd.SystemInterface.SystemInterFace;

/**
 * 公共action 所有Controller的基类 相当于 BaseController
 * */
public class BaseAction implements SystemInterFace {

	public BaseAction() {
	}

	/**
	 * 获取保存在Session中的用户对象
	 * 
	 * @param request
	 * @return
	 */
	protected UserBean getSessionUser(HttpServletRequest request) {
		return (UserBean) request.getSession().getAttribute(USER_CONTEXT);
	}

	/**
	 * 保存用户对象到Session中
	 * 
	 * @param request
	 * @param user
	 */
	protected void setSessionUser(HttpServletRequest request, UserBean user) {
		request.getSession().setAttribute(USER_CONTEXT, user);
	}

	/**
	 * 获取基于应用程序的url绝对路径
	 * 
	 * @param request
	 * @param url
	 *            以"/"打头的URL地址
	 * @return 基于应用程序的url绝对路径
	 */
	public final String getAppbaseUrl(HttpServletRequest request, String url) {
		Assert.hasLength(url, "url不能为空");
		Assert.isTrue(url.startsWith("/"), "必须以/打头");
		return request.getContextPath() + url;
	}

	public boolean notNull(String str) {
		if (null == str || "".equals(str.trim())) {
			return false;
		} else {
			return true;
		}
	}

	// 判断对象是null转成 "";
	public static String isnull(Object str) {
		return (String) (str == null ? "" : str);
	}

	public Object convert(Object target, Object o) throws Exception {
		if (o == null || target == null) {
			return null;
		}
		BeanUtils.copyProperties(target, o);
		return target;
	}

	/**
	 * Java 所有情况分页 参数: request对象 , 要放到的作用域的名称 , sql语句 或 List集合 , flag分页形式("sql","hql","jdbc","list") , 每页显示数据条数(可不传)
	 * 注：List 集合分页没有排序功能
	 * */
	@Autowired
	private AllPageBean apd;

	public void doAllSearchList(HttpServletRequest request, String listName, Object sqlList, String flag, int pagecount) {
		try {
			String sql = "";
			String onePageCount = request.getParameter("onePageCount");
			if (null == onePageCount || "".equals(onePageCount)) {
				onePageCount = pagecount + "";
			}
			pagecount = Integer.valueOf(onePageCount);
			String action = request.getParameter("action");
			String order = request.getParameter("order") == null ? "" : request.getParameter("order");
			String taxis = request.getParameter("taxis") == null || "".equals(request.getParameter("taxis")) ? ""
					: request.getParameter("taxis");
			if (flag.equals("list")) {
				// 如果是结果集分页
				apd.AllPageBean((List) sqlList, flag, pagecount, htmlpage);
			} else {
				// 正常查询语句分页
				sql = sqlList.toString();
				if (!"".equals(order)) {
					if (sql.lastIndexOf("order") != -1) {
						String[] sqls = sql.split("order ");
						if ("".equals(taxis) || "asc".equals(taxis)) {
							taxis = "asc";
						} else {
							taxis = "desc";
						}
						sql = sqls[0] + " order by " + order + " " + taxis;
					} else {
						if ("".equals(taxis) || "asc".equals(taxis)) {
							taxis = "asc";
						} else {
							taxis = "desc";
						}
						sql += " order by " + order + " " + taxis;
					}
				}
				apd.AllPageBean(sql, flag, pagecount, htmlpage);
			}

			String page = request.getParameter("page");
			int ln = 1;
			// 把当前页数转型
			if (page != null && !"".equals(page)) {
				ln = Integer.parseInt(page.trim());
			}
			if (action == null || "".equals(action)) {
				request.setAttribute(listName, apd.getBooks());
			} else {
				if (action == "nextPage" || "nextPage".equals(action)) {
					request.setAttribute(listName, apd.getIndexPage(String.valueOf(ln + 1)));
				}
				if (action == "previousPage" || "previousPage".equals(action)) {
					request.setAttribute(listName, apd.getIndexPage(String.valueOf(ln - 1)));
				}
				if (action == "index" || "index".equals(action)) {
					String leafNumber = request.getParameter("leafNumber");
					request.setAttribute(listName, apd.getIndexPage(leafNumber));
				}
			}
			if (Integer.valueOf(apd.getCurrentPage()) > apd.totalPages) {
				apd.setCurrentPage(apd.totalPages);
			}
			request.setAttribute("onePageCount", onePageCount);
			request.setAttribute("order", order);
			request.setAttribute("taxis", taxis);
			// request.setAttribute("pagesss", "pagesss");
			request.setAttribute("page", apd);
		} catch (Exception e) {
		}
	}

	public void doAllSearchList(HttpServletRequest request, String listName, Object sqlList, String flag) {
		this.doAllSearchList(request, listName, sqlList, flag, 10);
	}

	private String htmlpage; // 自定义分页标签中的html

	private String action;
	private String leafNumber;
	private String order;
	private String page;
	private String taxis;
	private String thisUrl;
	private String npage;

	public String getHtmlpage() {
		return htmlpage;
	}

	public void setHtmlpage(String htmlpage) {
		this.htmlpage = htmlpage;
	}

	public String getNpage() {
		return npage;
	}

	public void setNpage(String npage) {
		this.npage = npage;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getLeafNumber() {
		return leafNumber;
	}

	public void setLeafNumber(String leafNumber) {
		this.leafNumber = leafNumber;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getTaxis() {
		return taxis;
	}

	public void setTaxis(String taxis) {
		this.taxis = taxis;
	}

	public String getThisUrl() {
		return thisUrl;
	}

	public void setThisUrl(String thisUrl) {
		this.thisUrl = thisUrl;
	}
}
