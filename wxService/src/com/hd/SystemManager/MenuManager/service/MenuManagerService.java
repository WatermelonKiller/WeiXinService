package com.hd.SystemManager.MenuManager.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.SystemManager.MenuManager.bean.Department;
import com.hd.SystemManager.MenuManager.bean.SysMenu;
import com.hd.SystemManager.MenuManager.sql.MenuMangerSql;
import com.hd.util.SqlParameter;
import com.hd.util.dao.CommonDao;

@Service
@Transactional
public class MenuManagerService {
	  
	@Autowired
	private CommonDao cd;
	
	//Select All the father menu,return by List<Entity>
	public List<SysMenu> getAllFatherMenu(){
		 return  cd.DBjsonList(MenuMangerSql.TABLE_SEL+" where type ='J' and flag =1");
	}
	
	public List<Department> getAllDeptInfo(String sysId){
		 return  cd.DBjsonList(MenuMangerSql.DEPT_SEL+" where sys_id ='"+sysId+"'");
	}
	
	public String getAllSonMenu(String id){
		if(cd.DBjsonList(MenuMangerSql.TABLE_SEL+" where parent_id = '"+id+"' and flag = 1").isEmpty()){
			return "null";
		}else{
			return "notNull";
		}
	}
	
	//get All system name &Id 
	public List<Map<String,String>> getSystemTable(){
		 return  cd.DBjsonList(MenuMangerSql.SYSTEM_TABLE_SEL);
	}
	//Left Menu Entity Save by Hibernate;
    public boolean EntitySave(SysMenu menu){
    	return cd.insertinfor(menu);
    }
    
    
    //user Role against to leftMenu Save By Hibernate
    public boolean Role_ColumnSave(String columnId,String deptId){
    	String sql = MenuMangerSql.ROLE_USER_SAVE;
    	return cd.executeSQL(sql, new SqlParameter(columnId,deptId));
    }
    
    
    public SysMenu getEntityById(String id){
    	 return (SysMenu) cd.hibernateGet(SysMenu.class, id);
    }
    
    
    public boolean updMenuState(String id){
    	String sql = MenuMangerSql.DEL_MENU_STATE +" where column_id =? ";
    	if(cd.executeSQL(sql, new SqlParameter(id))){
    		String sqlRole = MenuMangerSql.DEL_ROLE_DATA+" where column_id=?";
    		return cd.executeSQL(sqlRole, new SqlParameter(id));
    	}else{
    		return false;
    	}
    }
    
    //updMenu By Hibernate 
    public boolean updMenuEvent(SysMenu menu){
    	return cd.updateinfor(menu);
    }
}
