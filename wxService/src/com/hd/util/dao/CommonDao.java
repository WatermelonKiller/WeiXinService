package com.hd.util.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

/**
 * *****************************************************************************
 *     File Name         :  com.framework.dao.CommonIDU.java
 *     Description(说明)	: 公共增删改查方法�?包含增删该各�?��方法，参数传入类对象即可�?
 *     						包含根据传入参数进行拼装HQL语句进行删除和更新的方法
 * 						包含�?��根据�?��参数进行拼装HQL的查询方法和�?��传入HQL语句进行查询的方�?
 *****************************************************************************
 */
@Repository
public class CommonDao<T> extends CommonSel<T> {

	public CommonDao() {
	}
	public static void deBug(Object str){
        System.out.println(str);
    }
	public void deBugs(Object str){
        System.out.println(str);
    }
	/**
	 * 方法功能: 将对象为null的转�?"";
	 */
	public static String isnull(Object str){
		return String.valueOf (str==null?"":str);
    }
	public static String isnull(Object str,Object mrv){
		return String.valueOf (str==null?mrv==null?"":mrv:str);
    }
	/**
	 * 方法功能: 获取临时目录文件位置; 
	 * 临时目录�?tomcat 下与webapps目录同级的fileTempPath文件目录 防止充不项目时文件丢�?
	 * weblogic �?tomcat 完全不同
	 */
	public static String getFileTempPath(HttpServletRequest request){
		String tempPath = request.getSession().getServletContext().getRealPath("//")
			.replace("\\webapps" + request.getContextPath().replace("/", "\\"), "");
		return tempPath+"\\fileTempPath";
    }
	/** 传字段数组返回List对象，包含页面多条数据内容（不要包含ID�?*/
	public static List<Map<String, String>> dogetList(HttpServletRequest request,String[] field){
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        int num=request.getParameterValues(field[0]).length;
        String value = "";
        for(int i=0;i<num;i++){
            Map<String, String> map = new HashMap<String, String>();
            for(int j=0;j<field.length;j++){
            	if(request.getParameterValues(field[j])!=null && request.getParameterValues(field[j]).length==num){
            		value = request.getParameterValues(field[j])[i];
            	}else{
            		value = request.getParameter(field[j])==null?"":request.getParameter(field[j]);
            	}
    		    if("null".equalsIgnoreCase(value))value="";
    		    map.put(field[j],value);
            }
            list.add(map);
        }
        return list;
    }    
	/** 传字段数组返回Map对象，包含页面一条数据内容（不要包含ID�?*/
	public static Map<String, String> dogetMap(HttpServletRequest request,String[] field){
		Map<String, String> map = new HashMap<String, String>();
		for(int i=0;i<field.length;i++){
			String value = request.getParameter(field[i])==null?"":request.getParameter(field[i]);
		    if("null".equalsIgnoreCase(value))value="";
		    map.put(field[i],value);
		}
        return map;
    }

	

}
