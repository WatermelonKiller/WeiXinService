package com.hd.SystemBean;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.hd.SystemInterface.SystemInterFace;
import com.hd.util.dao.CommonDao;


public class ForValueBean extends TagSupport implements SystemInterFace {

//	 * 代表控件必须有的属�?
	private  String type; //标签属�? *
	private  String value; //默认�?
	private  String Parentvalue; //父级标签
	private  String Sonvalue; //子级标签
	
	private static String forValue="";
	/***
	 * @throws JspException 
	 * 方法功能: 根据不同标识返回不同信息
	 */
	public int doEndTag() throws JspException {
		ServletRequest request = pageContext.getRequest();
		HttpSession session = pageContext.getSession();
		try {
			String se_parentlink = session.getAttribute("parentlink")==null?"":(String)session.getAttribute("parentlink");
			String se_sonlink = session.getAttribute("sonlink")==null?"":(String)session.getAttribute("sonlink");
			Parentvalue = request.getParameter("parentlink")==null?"":request.getParameter("parentlink");
			Sonvalue = request.getParameter("sonlink");
			if(!"".equals(Parentvalue)){
				if(!se_parentlink.equals(Parentvalue) || !se_sonlink.equals(Sonvalue)){
					session.setAttribute("parentlink", Parentvalue);
					session.setAttribute("sonlink", Sonvalue);
				}
			}else{
				Parentvalue = se_parentlink;
				Sonvalue = se_sonlink;
			}
			pageContext.getOut().println(forValue(session));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
//	返回相关信息
	public String forValue(HttpSession session) {
		if("title".equals(type)){
			forValue = SelValue(type,Parentvalue, (Map)findCurrentUser(session , "proname"))+"-"+SelValue(type,Sonvalue, (Map)findCurrentUser(session , "proname"));
			if("-".equals(forValue)&&value!=null)forValue= value;
		}
		return forValue;
	}
	
	//查询相关信息
	public String SelValue(String flag,String forid,Map obj) {
//		CommonSel cs = new CommonSel();
		String back = "";
		if("title".equals(type)&& forid != null&& !"".equals(forid)){
			if(obj!=null){
				back =  CommonDao.isnull( obj.get(forid) ) ;
			}
		}
		return back;
	}
	
	/**
	 * @param request
	 * @param getvalue
	 * @return 
	 * 方法功能: 获取 session 中用户随带信�?
	 */
	public static final Object findCurrentUser(HttpServletRequest request , String getvalue) {
		return findCurrentUser(request.getSession() , getvalue);
	}
	public static final Object findCurrentUser(HttpSession session , String getvalue) {
		Map<String, String> sessionMap = (Map<String, String>) session.getAttribute(USER_CONTEXT);
		if(sessionMap != null && !sessionMap.isEmpty()){
			return sessionMap.get(getvalue);
		}else{
			return null;
		}
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}