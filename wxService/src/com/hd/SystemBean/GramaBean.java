package com.hd.SystemBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


public class GramaBean extends TagSupport {

	public List grama;
	public List gramaP;
//	 * 代表控件必须有的属�?
	private  String type; //按钮属�? *
	private  String value; //按钮显示名称 *
	private  String script; //按钮事件 *
	private  String urlpage;//页面调用的所在位�?*
	private  String className; //样式
	private  String other; //其他标签 
	private  String img;  //span和A标签包含的img图片文件路径
	
	private static String btnButton="";
	/***
	 * @throws JspException 
	 * 方法功能: 判断页面按钮权限
	 */
	public int doStartTag() throws JspException {
//		ServletRequest request = pageContext.getRequest();
		HttpSession session = pageContext.getSession();
		try {
			grama = (List) session.getAttribute("programa");
			gramaP = (List) session.getAttribute("programaP");
			pageContext.getOut().println(checkGrama());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	//验证按钮权限
	public String checkGrama() {
		if(!gramaP.isEmpty()){
			for(Iterator iter = gramaP.iterator(); iter.hasNext();){
				Map pro = (Map) iter.next();
				//对比按钮名称
				if(value.equals(pro.get("node_name"))){
					//对比按钮�?��位置
					if(urlpage.equals(pro.get("explain"))||urlpage.equals(seekParentName((String)pro.get("parent_id"), grama))){
						if(type.toUpperCase().equals("SPAN")){
//							span标签的按�?
							btnButton = getSpan();
						}else if(type.toUpperCase().equals("A")){
//							A标签的按�?
							btnButton = getAtag();
						}else{
//							input形式的按�?
							btnButton = getButton();
						}
						break;
					}else{
						btnButton = "";
					}
				}else{
					btnButton = "";
				}
			}
		}else{
			btnButton = "";
		}
		//System.out.println("---------------"+btnButton);
		return btnButton;
	}
//	Button按钮
	public String getButton() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("<INPUT class='"+className+"'"); //class样式
		sb.append(" type='"+type+"'");//input属�?
		sb.append(" value='"+value+"'");// 按钮显示�?
		sb.append(" " + script); //script事件
		sb.append(" " + other); //其他自定义标签及属�?
		sb.append(" style='cursor: pointer' >");
		return sb.toString();
	}
//	A标签按钮
	public String getAtag() {
//		<a href='####' onclick='doDiaoDong(this)' val='personid' value='' type='text'><img src='"+_c+"/parts/images/003.gif' />调动</a>
		StringBuffer sb = new StringBuffer();
		sb.append("<a href='####' class='"+className+"'"); //class样式
		sb.append(" " + script); //script事件
		sb.append(" " + other); //其他自定义标签及属�?
		sb.append(" style='cursor: pointer' >");
		if(img!=null){
			sb.append("<img src='"+img+"' />");//A标签包含的图�?
		}
		sb.append(value);//A标签包含的�?
		sb.append("</a>"); //A标签结尾
		return sb.toString();
	}
//	Span标签按钮
	public String getSpan() {
//		<span class='RoleSpan' onclick='doEmpEdit(this)' title='编辑' val='personid' value='' type='text' onmouseout='doMsOut()' onmouseover='doMsOver()' >编辑</span>
		StringBuffer sb = new StringBuffer();
		
		sb.append("<span class='"+className+"'"); //class样式
		sb.append(" " + script); //script事件
		sb.append(" " + other); //其他自定义标签及属�?
		sb.append(" onmouseout='doMsOut()' onmouseover='doMsOver()' style='cursor: pointer' >");
		if(img!=null){
			sb.append("<img src='"+img+"' />");//span包含的图�?
		}
		sb.append(value);//span包含的�?
		sb.append("</span>"); //span结尾
		return sb.toString();
	}
	
//	从用户权里面找出�?��按钮权限
	public static List initGrama(List lg) {
		List list = new ArrayList();
		if(!lg.isEmpty()){
			for(Iterator iter = lg.iterator(); iter.hasNext();){
				Map pro = (Map) iter.next();
//				属�?�?Z 的应该是系统中按�?
				if(pro.get("type").equals("Z")){
					list.add(pro);
				}
			}
		}
		return list;
	}
	
//	转换�?��栏目id 及名�?成为map 对象
	public static Map convertGrama(List lg) {
		Map map = new HashMap();
		if(!lg.isEmpty()){
			for(Iterator iter = lg.iterator(); iter.hasNext();){
				Map pro = (Map) iter.next();
				map.put(pro.get("column_id"),pro.get("node_name"));
			}
		}
		System.out.println(map.toString());
		return map;
	}
	//查找父级节点的名�?
	public String seekParentName(String parentid,List grama) {
		String parentName = "";
		if(!grama.isEmpty()){
			for(Iterator iter = grama.iterator(); iter.hasNext();){
				Map pro = (Map) iter.next();
				if(pro.get("column_id")==parentid){
					parentName = (String)pro.get("node_name");
					break;
				}
			}
		}
		return parentName;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUrlpage() {
		return urlpage;
	}

	public void setUrlpage(String urlpage) {
		this.urlpage = urlpage;
	}

	
}