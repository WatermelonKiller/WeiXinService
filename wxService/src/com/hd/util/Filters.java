package com.hd.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import com.hd.login.bean.User;

public class Filters implements Filter{
	private static final String FILTERED_REQUEST = "@@session_context_filtered_request";
	public static final String USER_CONTEXT = "manager";
	public static final String LOGIN_TO_URL = "toUrl";

	private static final String[] INHERENT_ESCAPE_URIS = {
			"/login.jsp","/index.jsp","/login/login","/board/listBoardTopics-",
			"/board/listTopicPosts-","/column/updpassword.html","/column/checkpassword.html","/column/douppassword.html"
			,"/help.html","/inc/seal_interface","/syscount/count","/manager/dsjump","/bigscreen", "attached","/kindeditor"};

	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (request != null && request.getAttribute(FILTERED_REQUEST) != null) {
			chain.doFilter(request, response);
		} else {
			request.setAttribute(FILTERED_REQUEST, Boolean.TRUE);
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			User userContext = getSessionUser(httpRequest);
			if (userContext == null	&& !isURILogin(httpRequest.getRequestURI(), httpRequest)) {
				String toUrl = httpRequest.getRequestURL().toString();
				if (!StringUtils.isEmpty(httpRequest.getQueryString())) {
					toUrl += "?" + httpRequest.getQueryString();
				}
				httpRequest.getSession().setAttribute(LOGIN_TO_URL, toUrl);
				request.getRequestDispatcher("/index.jsp").forward(request,response);
				return;
			}
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
   /**
    * @param requestURI
    * @param request
    * @return
    */
	private boolean isURILogin(String requestURI, HttpServletRequest request) {
		if (request.getContextPath().equalsIgnoreCase(requestURI)
				|| (request.getContextPath() + "/").equalsIgnoreCase(requestURI))
			return true;
		for (String uri : INHERENT_ESCAPE_URIS) {
			if (requestURI != null && requestURI.indexOf(uri) >= 0) {
				return true;
			}
		}
		return false;
	}

	protected User getSessionUser(HttpServletRequest request) {
		Object oa = request.getSession().getAttribute(USER_CONTEXT);
		return (User) request.getSession().getAttribute(USER_CONTEXT);
	}
	
	public void destroy() {
	}
}
