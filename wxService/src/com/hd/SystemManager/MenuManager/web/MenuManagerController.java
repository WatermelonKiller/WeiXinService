package com.hd.SystemManager.MenuManager.web;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.mapper.JsonMapper;

import com.hd.SystemAction.BaseAction;
import com.hd.SystemManager.MenuManager.bean.Department;
import com.hd.SystemManager.MenuManager.bean.SysMenu;
import com.hd.SystemManager.MenuManager.service.MenuManagerService;
import com.hd.SystemManager.MenuManager.sql.MenuMangerSql;

@RequestMapping(value="/menu")
@Controller
public class MenuManagerController  extends BaseAction{
	
	@Autowired
	private MenuManagerService  mms;
	
	   /**
	    * 
	    * @author : Yang Jiyu
	    * @date : 2016-2-29下午1:42:09
	    * @param :
	    * @return :
	    * @description :
	    */
	@RequestMapping(value="/menuManagement")
	public ModelAndView CreateMenu(HttpServletRequest request,SysMenu menu){
		ModelAndView mav = new ModelAndView("/systemMenu/menuList");
		String sql = MenuMangerSql.TABLE_SEL+" where flag =1"; 
		doAllSearchList(request, "list", sql.toString(), "jdbc", 15); 
		return mav;
	}
	
	   /**
	    * 
	    * @author : Yang Jiyu
	    * @date : 2016-2-29下午1:42:09
	    * @param :
	    * @return :
	    * @description :
	    */
	@RequestMapping(value="/addMenu ")
	public String addMenu(){
		return "/systemMenu/addMenuPage";
	}

   /**
    * 
    * @author : Yang Jiyu
    * @date : 2016-2-29下午1:42:09
    * @param :
    * @return :
    * @description :
    */
	@RequestMapping(value = "/ajax/loadAllFatherMenu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")	
	public @ResponseBody String loadAllFatherMenu() {
		List<SysMenu> fatherMenu= mms.getAllFatherMenu();
		JsonMapper jsonMapper = new JsonMapper();
		return jsonMapper.toJson(fatherMenu);
	}
	
	/**
	 * 
	 * @author : Yang Jiyu
	 * @date : 2016-3-1下午4:05:03
	 * @param :
	 * @return :
	 * @description :根据sysId加载所有部门及ID
	 */
	@RequestMapping(value = "/ajax/loadAllDept", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")	
	public @ResponseBody String loadAllDept(String sysId) {
		List<Department> deptMenu= mms.getAllDeptInfo(sysId);
		JsonMapper jsonMapper = new JsonMapper();
		return jsonMapper.toJson(deptMenu);
	}
	
	/**
	 * @author Yang Jiyu
	 * @time 2015-11-12
	 * @param String ParametersId
	 * @return Entity DataSouse Json Type
	 * @description
	 */
	@RequestMapping(value = "/ajax/loadAllSystem", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public 	@ResponseBody String loadAllSystem() {
		List<Map<String,String>> systemInfo= mms.getSystemTable();
		JsonMapper jsonMapper = new JsonMapper();
		return jsonMapper.toJson(systemInfo);
	}
	
	/**
	 * @author Yang Jiyu
	 * @time 2015-11-16	
	 * @param SysMenu Entity HttpReuqest
	 * @return Result Json;
	 * @description
	 */
	@RequestMapping(value="/ajax/createLeftMenu")
	public 	@ResponseBody String createMenu(HttpServletRequest request,SysMenu menu){
		String deptAllowId =menu.getDept_id();
		String result = "{\"message\":\"error\"}";
		menu.setColumn_id(UUID.randomUUID().toString());
		menu.setFlag("1");
		boolean rs = mms.EntitySave(menu);
		boolean RoleRs=false;
		if (rs){		
			/**
			 * sysid唯一,dept_id为多选,查询dept下的所有人员id进行存储;
			 * deptId为英文逗号拼接字符串或者为空,如果为空则默认选择该系统下的全部门人员都可见;
			 */
			String deptId [] = deptAllowId.split(",");	
			for(int i = 0 ; i < deptId.length; i ++){
				//菜单栏目权限对应关系存储;
				RoleRs= mms.Role_ColumnSave(menu.getColumn_id(),deptId[i].toString());
			}if(RoleRs){
				result = "{\"message\":\"success\"}";
			}else{
				result = "{\"message\":\"error\"}";
			}
		}else{
			return result;
		 }
		return result;
		}
	
	/**
	 * @author Yang Jiyu
	 * @time 2015-11-17	
	 * @param request
	 * @return Page;
	 * @description  修改按钮页面
	 */
	@RequestMapping(value="/updPage")
	public ModelAndView updatePage(HttpServletRequest request){
		ModelAndView  mav = new ModelAndView("/systemMenu/updMenuPage");
		mav.addObject("map",mms.getEntityById(request.getParameter("cid")));
		mav.addObject("sysList",mms.getSystemTable());
		mav.addObject("parList",mms.getAllFatherMenu());
		return mav;
	}
	
	/**
	 * @author Edwin Yang
	 * @time 2015-11-17
	 * @param request,SysMenu entity
	 * @return Json Result ;
	 * @description
	 */
	@RequestMapping(value="/updEvent")
	public @ResponseBody String UpdMenuEvent(HttpServletRequest request,SysMenu menu){
		boolean result = mms.updMenuEvent(menu);
		if(result){
			return "{\"message\":\"success\"}";
		}else{
			return "{\"message\":\"fail\"}";
		}
	}
	/**
	 * @author Edwin Yang
	 * @time 2015-11-17
	 * @param request
	 * @return Json Result ;
	 * @description 删除菜单功能,判断是否是父菜单或者子菜单,进行区别删除;
	 */
	@RequestMapping(value="/delMenuEvent")
	public 	@ResponseBody  String delMenu(HttpServletRequest request){
		boolean result = false;
		//1.如果是父级,判断是否存在子菜单,如果有子菜单未删除,提示无法删除父级菜单,返回isF
		if("J".equals(request.getParameter("type")) && null != (request.getParameter("type"))){
		   if("notNull".equals(mms.getAllSonMenu(request.getParameter("id").toString()))){
			   return "{\"message\":\"isF\"}";
		   }else{
			   //1.1如果没有子菜单,则进行直接删除;
			  result = mms.updMenuState(request.getParameter("id"));
		   }
		}else{
		//2.如果是子菜单.则直接进行删除;
			result = mms.updMenuState(request.getParameter("id"));
		}
			if(result){
				return "{\"message\":\"success\"}";
			}else{
				return "{\"message\":\"fail\"}";
			}
	}
	/**
	 * 
	 * @author : Yang Jiyu
	 * @date : 2016-3-8上午11:06:42
	 * @param :String sys_id
	 * @return :
	 * @description :通过sysid查询系统下面的所有部门
	 * 						显示到页面上
	 */
	@RequestMapping(value="/selectDeptPage")
	 public ModelAndView selectDeptPage(HttpServletRequest request,String sysId){
		 ModelAndView mav = new ModelAndView("/systemMenu/selectDeptPage");
		 String sql = MenuMangerSql.DEPT_SEL+" where sys_id = '"+sysId+"'";
		 doAllSearchList(request, "list", sql.toString(), "jdbc", 15); 
		 return  mav;
	 }
	
}
