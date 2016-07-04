package com.hd.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class CommonUtils
{
    public CommonUtils()
    {
    }
    /**
     * 调用System.out.println()输出
     * @param str
     */
    public static void deBug(Object str)
    {
        System.out.println(str);
    }
    public static void deBug()
    {
        System.out.println();
    }
    /**
     * 取值去空，如值等于null 匹配默认值
     * @param request
     * @param VarName
     * @param DefaultValue
     * @return
     */
    public static String getParameter(HttpServletRequest request, String VarName, String DefaultValue)
    {
        String Value = request.getParameter(VarName);
        Value = Value != null ? Value.trim() : DefaultValue;
        return Value;
    }
   
    /**
     * 取值转型（UTF-8）去空，如值等于null 匹配默认值
     * @param request
     * @param VarName
     * @param DefaultValue
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getParameterISO(HttpServletRequest request, String VarName, String DefaultValue) throws UnsupportedEncodingException
    {
        String Value =new String(request.getParameter(VarName).getBytes("ISO8859_1"),"UTF-8");
        Value = Value != null ? Value.trim() : DefaultValue;
        return Value;
    }
    /**
     * 转型（UTF-8)
     * @param str
     * @return
     */
    public static String getStr(String str)
    {
        if (str == null) return null;
        try
        {
            byte[] temp_t = str.getBytes("UTF-8");
                        return  new String(temp_t);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * TEXT 或 TEXTAREA 存库时使用
     * @param src
     * @return
     */
    public static String HTMLEncode(String src)
	{   
        StringBuffer newString = new StringBuffer();   
        if(src==null)   
        return "&nbsp";   
        char next;   
        for(int i=0;i<src.length();i++)
        {   
            next = src.charAt(i);   
            /*if(next=='>')   
                newString.append("&gt;");   
            else if(next=='<')   
                newString.append("&lt;");   
            else if(next=='&')   
                newString.append("&amp;");   
            else if(next=='"')   
                newString.append("&quot;");   
            else if(next==39)   //   The   "'"   
                newString.append("&rsquo;");*/   
             if(next==13)   
                newString.append("<br>");  
            else if(next==' ')
            	newString.append("&nbsp");
            else   
                newString.append(next);   
        }   
        return newString.toString();   
    }
    public static String HTMLEncodeAll(String src)
	{   
        StringBuffer newString = new StringBuffer();   
        if(src==null)   
        return "&nbsp";   
        char next;   
        for(int i=0;i<src.length();i++)
        {   
            next = src.charAt(i);   
            if(next=='>')   
                newString.append("&gt;");   
            else if(next=='<')   
                newString.append("&lt;");   
            else if(next=='&')   
                newString.append("&amp;");   
            else if(next=='"')   
                newString.append("&quot;");   
            else if(next==39)   //   The   "'"   
                newString.append("&rsquo;");   
             if(next==13)   
                newString.append("<br>");  
            else if(next==' ')
            	newString.append("&nbsp");
            else   
                newString.append(next);   
        }   
        return newString.toString();   
    }
    /**
     * TEXT 或 TEXTAREA 库中取值时使用
     * @param src
     * @return
     */
	public static String HTMLReverse(String src){
		if(src!=null){
			src=src.replaceAll("<br>", "");
			src=src.replaceAll("&nbsp;", " ");
		}
		return src;
	}
    public static String returnIdByAddZero(String id, int num)
    {
        int len = id.length();
        if(len >= num )
            return id;
        String newId = "";
        for(int x = 0 ; x < num - len ; x++)
        {
            newId = newId +"0";
        }
        return newId + id;
    }
    public static String getStrFour(String str){
    	String s="0";
    	for(int i=str.length();i<5;i++){
    		str=str.concat(s);
    	}
    	return str;
    }
    
    /**
     * @author 矫健
     * @time Feb 15, 2011	
     * @param e 异常
     * 方法功能:打印异常信息
     */
    public static void printE(Exception e)
    {
    	e.printStackTrace() ;
    }
    
    /**
     * 
     * @author 王闯
     * @time 2011-8-1	
     * @param obj 
     * 方法功能:入参为标准javabean，将javabean中的所有String类型属性判空，如果为空则调用该属性setMethod将其赋为空字符串。
     * 所属模块:用于所有前台页面显示，避免前台出现null列
     */
    @SuppressWarnings("unchecked")
	public static void beanTrimNull(Object obj){
		
		//获得对象的Class
		Class classType = obj.getClass();    
		
		//获得对象的所有属性    
		Field[] fields=classType.getDeclaredFields();  
		
		String returnType = "";
		try {
			for(int i=0;i<fields.length;i++){
				//获取数组中对应的属性    
	            Field field=fields[i];    
	                
	            String fieldName=field.getName();    
	            String stringLetter=fieldName.substring(0, 1).toUpperCase();    
	                
	            //获得相应属性的getXXX和setXXX方法名称    
	            String getName="get"+stringLetter+fieldName.substring(1);    
	            String setName="set"+stringLetter+fieldName.substring(1);    
	
	            //获取相应的方法    
	            Method getMethod=classType.getMethod(getName, new Class[]{});    
	            Method setMethod=classType.getMethod(setName, new Class[]{field.getType()}); 
	            
	            //调用源对象的getXXX（）方法    
	            Object value=getMethod.invoke(obj, new Object[]{});
	            
	            //如果为字符串类型,并且为Null 调用set方法置为空
	            returnType = getMethod.getReturnType().toString();
	            if("class java.lang.String".equalsIgnoreCase(returnType)){
	            	if( null == value ){
	            		setMethod.invoke(obj,new Object[]{""});
	            	}
	            }
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * @param size  保留位数
     * @param number 对应数字
     * @return 
     * 方法功能: double型数字 四舍五入
     */
    public static String formatDouble(int size,double number){
    	number=Roundoff4(number);
		if(number==0d){
			return "0";
		}
		String numStr = String.valueOf(number);
		if(numStr.indexOf(".") != -1){
			//取整数部分
			String a = numStr.substring(0, numStr.indexOf("."));
			int aint=Integer.parseInt(a);
			//取小数部分
			String b = numStr.substring(numStr.indexOf(".")+1);
			//小数长度
			int strLength = b.length();
			//小数长度大于想要取的位数
			if(strLength>size){
				String aa = b.substring(size, size+1);
				String aa1 = b.substring(0,size-1);
				int aa1int=Integer.parseInt(aa1);
				String bianwei = b.substring(size-1, size);
				int bianweiInt = Integer.parseInt(bianwei);
				String aa2 = b.substring(0, size);
				int aaa = Integer.parseInt(aa);
				if(aaa>=5){
					bianweiInt = bianweiInt+1;
					if(bianweiInt==10){
						aa1int=aa1int+1;
						bianweiInt=0;
						if(aa1int==10){
							aint=aint+1;
							aa1int=0;
						}
					}
					return aint+"."+aa1int+String.valueOf(bianweiInt);
				}
				if(aaa<5){
					return "0".equals(aa2)? String.valueOf(aint) :aint+"."+aa2;
				}
			}
			if(strLength<=size){
				if("0".equals(b)){
					return a;
				}else{
					return numStr;
				}
			}
		}
		return "0" ;
	}
    public static Double Roundoff4(Double num){
		try {
			BigDecimal b=new BigDecimal(num);         
		} catch (Exception e) {
			return num;
		}
		if(num!=null){
			BigDecimal b=new BigDecimal(num);      
			num=b.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue(); 
			return num;
		}
		return null;
	}
  //获取一个字母加数字的随机字符串： 
    public static String getCharacterAndNumber(int length) {   
    String password = "";   
        Random random = new Random();   
        for(int i = 0; i < length; i++) {   
        String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字   
            if("char".equalsIgnoreCase(charOrNum)){ // 字符串   
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母   
                password += (char) (choice + random.nextInt(26));   
            }else if("num".equalsIgnoreCase(charOrNum)) {  // 数字   
            password += String.valueOf(random.nextInt(10));   
            } 
         } 
        return password;   
    }  
    //获取一个64位字母加数字的随机字符串： 
    public static String getCharacterAndNumber() {   
    String password = "";   
        Random random = new Random();   
        for(int i = 0; i < 64; i++) {   
        String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字   
            if("char".equalsIgnoreCase(charOrNum)){ // 字符串   
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母   
                password += (char) (choice + random.nextInt(26));   
            }else if("num".equalsIgnoreCase(charOrNum)) {  // 数字   
            password += String.valueOf(random.nextInt(10));   
            } 
         } 
        return password;   
    }  
    public static void main(String[] args) {
		System.out.println(getCharacterAndNumber());
	}
    
    /**
    * 将字符串中的中文转化为拼音,其他字符不变
    *
    * @param inputString
    * @return
    */

    public static String getPingYin(String inputString) {  

    	HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();  

    	format.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
    	format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
    	format.setVCharType(HanyuPinyinVCharType.WITH_V);  
    	char[] input = inputString.trim().toCharArray();  
    	String output = "";  

    	try {  
    	 for (int i = 0; i < input.length; i++) {  
    	if (java.lang.Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {  
    	    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);  
    	    output += temp[0];  
    	  } else {
    	          output += java.lang.Character.toString(input[i]);  
    	       }  
    	 }
    	} catch (BadHanyuPinyinOutputFormatCombination e) {  
    	          e.printStackTrace();  
    	    }  
    	    return output;  

     }  
    /** 
    * 获取汉字串拼音首字母，英文字符不变 
    * @param chinese 汉字串 
    * @return 汉语拼音首字母 
    */  

    public static String getFirstSpell(String chinese) {  
            StringBuffer pybf = new StringBuffer();  
            char[] arr = chinese.toCharArray();  
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
            for (int i = 0; i < arr.length; i++) {  
                    if (arr[i] > 128) {  
                            try {  
                                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat); 
                                    if (temp != null) {  
                                            pybf.append(temp[0].charAt(0));  
                                    }  
                            } catch (BadHanyuPinyinOutputFormatCombination e) {  
                                    e.printStackTrace();  
                            }  
                    } else {  
                            pybf.append(arr[i]);  
                    }  
            }  
            return pybf.toString().replaceAll("\\W", "").trim(); 
    }  
    /** 
     * 获取汉字串拼音，英文字符不变 
     * @param chinese 汉字串 
     * @return 汉语拼音 
     */  
    public static String getFullSpell(String chinese) {  
        StringBuffer pybf = new StringBuffer();  
        char[] arr = chinese.toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < arr.length; i++) {  
                if (arr[i] > 128) {  
                        try {  
                                pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);  
                        } catch (BadHanyuPinyinOutputFormatCombination e) {  
                                e.printStackTrace();  
                        }  
                } else {  
                        pybf.append(arr[i]);  
                }  
        }  
        return pybf.toString();  
} 

}

