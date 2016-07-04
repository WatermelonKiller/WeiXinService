package com.hd.util;

import java.lang.reflect.Method;



public class Application {
	
	private static Application apl = new Application();
	public static String KEY_NUMBER="1597440583";
	public static String KEY="999888";
	public static Application getInstance() {
		return apl;
	}
	//调用底层初始化key 返回uid 或ERROR
	public String initKey(String seed) {
		if(seed.length()>10){
			return "ERROR";
		}
		String uid="";
		try {
			Class inClass = Class.forName("RockeyBot");//加载类
			Class[] parameterTypes = new Class[1];
			parameterTypes [0] = String.class;
			//方法名 
			Method   cMethod   =   inClass.getMethod("InitKey",parameterTypes);  
			uid = cMethod.invoke(inClass, seed).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uid;
	}
	//调用底层写key 返回硬件id  或ERROR
	public String writeKey(String UID ,int iBlock_index,String value) {
		if(iBlock_index>4||iBlock_index<0){
			return "ERROR";
		}
		String hid="";
		try {
			Class inClass = Class.forName("RockeyBot");//加载类
			Class[] parameterTypes = new Class[3];
			parameterTypes [0] = String.class;
			parameterTypes [1] = Integer.class;
			parameterTypes [2] = String.class;
			//方法名 
			Method   cMethod   =   inClass.getMethod("WriteKey",parameterTypes);  
			hid = cMethod.invoke(inClass, UID , iBlock_index , value).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hid;
	}
	//调用底层读key 返回key中指定扇区中的内容 或ERROR
	public String readKey(String UID ,int iBlock_index) {
		if(iBlock_index>4||iBlock_index<0){
			return "ERROR";
		}
		String value="";
		try {
			Class inClass = Class.forName("RockeyBot");//加载类
			Class[] parameterTypes = new Class[2];
			parameterTypes [0] = String.class;
			parameterTypes [1] = Integer.class;
			//方法名 
			Method   cMethod   =   inClass.getMethod("ReadKey",parameterTypes);  
			value = cMethod.invoke(inClass, UID , iBlock_index).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	//调用底层读key 返回所有key中指定扇区中的内容 或ERROR
	public String[] readAllKey(int iBlock_index) {
		if(iBlock_index>4||iBlock_index<0){
			return null;
		}
		String value = "";
		String[] values = null ;
		try {
			Class inClass = Class.forName("RockeyBot");//加载类
			Class[] parameterTypes = new Class[1];
			parameterTypes [0] = Integer.class;
			//方法名 
			Method   cMethod   =   inClass.getMethod("ReadAllKey",parameterTypes);  
			value = cMethod.invoke(inClass, iBlock_index).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!"".equals(value)){
			values=value.split("||");
		}
		return values;
	}
	
	public static void main(String[] args) {
		
		DesEncrypt des = DesEncrypt.getInstance();
		Application ap =new Application();
		//String uid = ap.initKey("999888");
		String uid = Application.KEY_NUMBER; //715400947
//		System.out.println("init:"+uid);
//		String bo = ap.writeKey(uid , 4 , "delete");
//		System.out.println("write:"+bo);
		String value = ap.readKey(uid,1);//(uid);
//		String[] value =  ap.readAllKey( 0 );
//		for(int i =0;i<value.length;i++){
//			System.out.println("read:"+value[i]);
//		}
		System.out.println("read:"+des.getDesString(value));
	}
	
}
