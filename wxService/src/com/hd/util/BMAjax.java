package com.hd.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hd.SystemInterface.SystemInterFace;
import com.hd.util.Application;
import com.hd.util.DesEncrypt;
import com.hd.util.dao.CommonDao;
import com.hd.util.dao.DBDAO;
import com.hd.util.CommonFunc;
import com.hd.util.SqlParameter;

/**
 * ajax DWR方法类
 * */
@Repository
public class BMAjax  extends DBDAO  implements SystemInterFace{

//	@Autowired
//	private DBQ db;
	
	@Autowired
	private CommonDao<Object> cd;
	
	
	Calendar dt = Calendar.getInstance();
	int iYear = dt.get(Calendar.YEAR);
	int iMonth = dt.get(Calendar.MONTH) + 1;
	int iDate = dt.get(Calendar.DATE);
	private boolean flag = false;


	/**
	 * @param map  传入map对象
	 * @return 
	 * @throws Exception 
	 * 方法功能: 将map对象 转成  JSONObject对象
	 */
	public JSONObject loadingJSONObject(Map map)
		throws Exception {
		JSONObject jobject = new JSONObject();
		jobject.putAll(map);
		return jobject;
	}
	/**
	 * @param strPartition 分隔符
	 * @return 
	 * 方法功能: //	 获取当前日期
	 */
	public String getAjaxDate(String strPartition) {
		String strTime = "";
		try {
			if (iMonth < 10) {
				if (iDate < 10) {
					strTime = iYear + strPartition + "0" + iMonth + strPartition + "0" + iDate;
				} else {
					strTime = iYear + strPartition + "0" + iMonth + strPartition + iDate;
				}
			} else {
				if (iDate < 10) {
					strTime = iYear + strPartition + iMonth + strPartition + "0" + iDate;
				} else {
					strTime = iYear + strPartition + iMonth + strPartition + iDate;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strTime;

	}
	/**
	 * 获取服务器当前时间的毫秒数
	 * @param inVal   传入一个日期 yyyy-MM-dd HH:mm:ss
	 * @return 方法功能:
	 */
	public long fromDateStringToLong(String inVal) { // 此方法计算时间毫秒
		Date date = null; // 定义时间类型
		SimpleDateFormat inputFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			date = inputFormat.parse(inVal); // 将字符型转换成日期型
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date.getTime(); // 返回毫秒数
	}
	/**
	 * @param a 位数
	 * @param b 模式
	 * @return 
	 * 方法功能: 获取随机编号 如CommonFunc.CreateRadom(16, 2)
	 */
	public String donumber(int a, int b) {
		String number = CommonFunc.CreateRadom(a, b);
		return number;
	}
	/**
	 * @param keyValue 要加密的信息
	 * @return 
	 * 方法功能: 通过固定密钥 加密信息
	 */
	public static String doEncString(String keyValue) {
		if(!"".equals(keyValue)){
			DesEncrypt des = DesEncrypt.getInstance();
			des.setKey(Application.KEY );
			String envalue = des.getEncString(keyValue);
//			System.out.println(keyValue+"+加密+"+envalue);
			return envalue;
		}else{
			return keyValue;
		}
	}
	/**
	 * @param keyValue 要解密的信息
	 * @return 
	 * 方法功能: 通过固定密钥 解密信息
	 */
	public static String doDesString(String keyValue) {
		if(!"".equals(keyValue)){
			DesEncrypt des = DesEncrypt.getInstance();
			des.setKey(Application.KEY );
			String envalue = des.getDesString(keyValue);
//			System.out.println(keyValue+"+解密+"+envalue);
			return envalue;
		}else{
			return keyValue;
		}
	}
	
	public static void main(String[] args) {
		String a ="-1.9";
		System.out.println(a.split(","));
	}
}
