package com.hd.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

///注意：对于1970年之前的时间，不建议使用本类

@Repository
public class DateUtil {
/**    
 * 
1.获取当前时间 
        1） 默认格式yyyy-MM-dd          getTime()
        2） 任意指定格式                getTime(String aMask)
        

2.单独获取当前年、月、日、时、分、秒

    //通过静态方法，设置时间，然后用下面的方法获取其中的某一项时间<默认为系统时间>
        public static void setDate(Date date);
    
        1） 年 getYear()
        2） 月 getMonth()
        3） 日 getDay()
        4） 时 getHour()<12小时制>
        5） 时 getHourOfDay()<24小时制>
        6） 分 getMinute()    
        7） 秒 getSecond()
        
        8） 当前周在当月中是第几周 getWeekOfMonth()
        9） 当前周在当年中是第几周 getWeekOfYear()
       10） 当前日在当月中是第几天 getDayOfMonth()
       11） 当前日在当年中是第几天 getDayOfYear()
        
        
3.字符串类型转换    
        1）指定字符串类型转换为yyyy-MM-dd格式的日期类型   stringToDate(String strDate)
        2）字符串类型转换为指定格式的日期类型             stringToDate(String aMask, String strDate)
        3）日期类型转换为yyyy-MM-dd格式的字符串类型       dateToString(Date aDate)            
        4）日期类型转换为yyyy-MM-dd格式的字符串类型       dateToString(String aMask, Date aDate)  


4.日期计算
        1）获取两个日期之间的差
            <1>传入两个指定格式的字符串yyyy-MM-dd 返回它们所代表的时间差(天)<不包含当天>
               dateSubtract(String date1,String date2)
            <2>传入两个指定格式的字符串 返回它们所代表的时间差(天)<不包含当天>
               dateSubtract(String aMask,String date1,String date2)
            <3>传入两个指定格式的字符串yyyy-MM-dd 返回它们所代表的时间差(天)<不包含当天和周六周日>
               dateSubtractNo67(String date1,String date2)
        2）传入指定格式的字符串yyyy-MM-dd 和 一个天数  获取一个日期N天之后的日期字符串      
           dateLatter(String date,int days)                            
        3）传入指定格式的字符串yyyy-MM-dd 和 一个天数  获取一个日期N天之前的日期字符串                                  
           dateBefore(String date,int days)         


5.日期转换为时间数字串 <从1970至某一时间之间的时间数>
        1）默认为当前时间           longtime()
        2）传入yyyy-MM-dd格式        longtime(String date)
        3）传入指定日期格式          longtime(String aMask,String date)

*/
    //设置日期
    private static Date date;
    public static void setDate(Date date) {
        DateUtil.date = date;
    }

    /**
     * 日期格式 yyyy-MM-dd。
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_PATTERN1 = "yyyyMMdd";
    public static final String DATE_PATTERN2 = "yyyy/MM/dd";

    /**
     * 日期格式 yyyy-MM-dd HH:mm。
     */
    public static final String LONG_DATE_PATTERN = "yyyy-MM-dd HH:mm";
    
    /**
     * 日期格式 yyyy-MM-dd HH:mm:ss
     */
    public static final String NOW_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式 HH:mm。
     */
    public static final String TIME_PATTERN = "HH:mm";

    
    
    /**
    *	当前时间 yyyy-MM-dd
    * @return 当前时间
    */
    public static String getTime(){
        return getTime(DATE_PATTERN);
    }
    /**
     *	当前时间 yyyyMMdd
     * @return 当前时间
     */
     public static String getTimek(){
         return getTime(DATE_PATTERN1);
     }
    /**
     *  当前时间 yyyy/MM/dd
     * @return
     */
    public static String getTimes(){
        return getTime(DATE_PATTERN2);
    }
    /**
    * 取得指定格式的当前时间
    * @return 取得指定格式的当前时间
    */
    public static String getTime(String aMask){
        Date aDate = new Date();
        return dateToString(aMask,aDate);
    }

    
    /**
     *	当前年
     * @return 当前年
     */
    public static String getYear(){
        if(date==null)
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        else{
            Calendar calendar  =  Calendar.getInstance();
            calendar.setTime(date);
            return String.valueOf(calendar.get(Calendar.YEAR));
        }
    }
    /**
    *	当前月
    * @return 当前月
    */
    public static String getMonth(){
        if(date==null)
            return String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
            else{
                Calendar calendar  =  Calendar.getInstance();
                calendar.setTime(date);
                return String.valueOf(calendar.get(Calendar.MONTH)+1);
            }
    }
    /**
    *	当前日
    * @return 当前日
    */
    public static String getDay(){
        if(date==null)
            return String.valueOf(Calendar.getInstance().get(Calendar.DATE));
            else{
                Calendar calendar  =  Calendar.getInstance();
                calendar.setTime(date);
                return String.valueOf(calendar.get(Calendar.DATE));
            }
    }
    /**
    *	当前时
    * @return 当前时<24小时制>
    */
    public static String getHourOfDay(){
        if(date==null)
            return String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
            else{
                Calendar calendar  =  Calendar.getInstance();
                calendar.setTime(date);
                return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
            }
    }
    /**
    *	当前时
    * @return 当前时<12小时制>
    */
    public static String getHour(){
        if(date==null)
            return String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
            else{
                Calendar calendar  =  Calendar.getInstance();
                calendar.setTime(date);
                return String.valueOf(calendar.get(Calendar.HOUR));
            }
    }
    /**
    *	当前分
    * @return 当前分
    */
    public static String getMinute(){
        if(date==null)
            return String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
            else{
                Calendar calendar  =  Calendar.getInstance();
                calendar.setTime(date);
                return String.valueOf(calendar.get(Calendar.MINUTE));
            }
    }
    /**
    *	当前秒
    * @return 当前秒
    */
    public static String getSecond(){
        if(date==null)
            return String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
            else{
                Calendar calendar  =  Calendar.getInstance();
                calendar.setTime(date);
                return String.valueOf(calendar.get(Calendar.SECOND));
            }
    }
    /**
    *	当前周在当月中是第几周
    * @return 当前周在当月中是第几周
    */
    public static String getWeekOfMonth(){
        if(date==null)
            return String.valueOf(Calendar.getInstance().get(Calendar.WEEK_OF_MONTH));
            else{
                Calendar calendar  =  Calendar.getInstance();
                calendar.setTime(date);
                return String.valueOf(calendar.get(Calendar.WEEK_OF_MONTH));
            }
    }
    /**
    *	当前周在当年中是第几周
    * @return 当前周在当年中是第几周
    */
    public static String getWeekOfYear(){
        if(date==null)
            return String.valueOf(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
            else{
                Calendar calendar  =  Calendar.getInstance();
                calendar.setTime(date);
                return String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
            }
    }
    /**
    *	当前日在当年中是第几天
    * @return 当前日在当年中是第几天
    */
    public static String getDayOfYear(){
        if(date==null)
            return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
            else{
                Calendar calendar  =  Calendar.getInstance();
                calendar.setTime(date);
                return String.valueOf(calendar.get(Calendar.DAY_OF_YEAR));
            }
    }
    /**
    *	当前日在当周中是第几天
    * @return 当前日在当周中是第几天
    */
    public static String getDayOfWeek(){
        if(date==null)
            return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
            else{
                Calendar calendar  =  Calendar.getInstance();
                calendar.setTime(date);
                return String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
            }
    }
    public static String getDayOfWeek(String dates){
    	if(dates==null || "".equals(dates))
    	{
    		return null;
    	}    	
    	Date date=new Date();
		try {
			date = DateFormat.getDateInstance().parse(dates);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(date==null)
            return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
            else{
                Calendar calendar  =  Calendar.getInstance();
                calendar.setTime(date);
                return String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
            }
    }
 
    
    /**3.1
     * 指定字符串类型转换为yyyy-MM-dd格式的日期类型。如果指定字符串对象为空，则返回null。
     * 
     * @param strDate
     *            日期格式字符串。
     * @return yyyy-MM-dd格式的日期类型。
     */
    public static final Date stringToDate(String strDate) throws ParseException {
        Date aDate = null;
        try {
            aDate = stringToDate(DATE_PATTERN, strDate);
        } catch (ParseException e) {
            throw new ParseException(e.getMessage(), e.getErrorOffset());
        }
        return aDate;
    }

    /**3.2
     * 字符串类型转换为指定格式的日期类型。如果指定字符串对象为空，则返回null。如果格式不合法，则抛出一个ParseException异常。
     * 
     * @param aMask
     *            指定格式。
     * @param strDate
     *            指定字符串对象。
     * @return 指定格式的日期类型。
     * @throws ParseException
     *             指定格式不合法。
     */
    public static final Date stringToDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            throw new ParseException(e.getMessage(), e.getErrorOffset());
        }
        return date;
    }

    /**3.3
     * 指定日期类型转换为yyyy-MM-dd格式的字符串类型。如果日期类型对象为空，则返回空值。
     * 
     * @param aDate
     *            转换日期。
     * @return 转换后的字符串型日期 yyyy-MM-dd。
     */
    public static final String dateToString(Date aDate) {
        return dateToString(DATE_PATTERN, aDate);
    }

    /**3.4
     * 日期类型转换为指定格式的字符串类型。如果日期类型为空，则返回空值。
     * 
     * @param aMask
     *            指定格式。
     * @param aDate
     *            转换日期。
     * @return 指定格式的字符串类型。
     */
    public static final String dateToString(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate != null) {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }
        return returnValue;
    }
    
    /**4.11
     * 传入两个指定格式的字符串yyyy-MM-dd 返回它们所代表的时间差(天)<不包含当天>
     * 
     * @param date1 前面的时间
     * @param date2 后面的时间
     * @return
     * @throws Exception
     */
    public static long dateSubtract(String date1,String date2)throws Exception{
        return (longtime(date2)-longtime(date1))/24/60/60/1000;
    }
    /**4.12
     * 传入两个指定格式的字符串 返回它们所代表的时间差(天)<不包含当天>
     * @param aMask 指定格式。
     * @param date1 前面的时间
     * @param date2 后面的时间
     * @return
     * @throws Exception
     */
    public static long dateSubtract(String aMask,String date1,String date2)throws Exception{
        return (longtime(aMask,date2)-longtime(aMask,date1))/24/60/60/1000;
    }
    /**4.13
     * 传入两个指定格式的字符串yyyy-MM-dd 返回它们所代表的时间差(天)<不包含当天和周六周日>
     * 
     * @param date1 前面的时间
     * @param date2 后面的时间
     * @return
     * @throws Exception
     */
    public static long dateSubtractNo67(String date1,String date2)throws Exception{
        long time = longtime(date2)-longtime(date1);
       
        int times = Integer.valueOf( String.valueOf(time/24/60/60/1000));
        int time67 =0;
        for(int i=0;i<times;i++){
            String dateLater = dateLatter(date1,i);
            setDate(stringToDate(dateLater));
            String flag67 =getDayOfWeek();
          if( "1".equals(flag67) || "7".equals(flag67)  ){
               time67+=1;
           }
        }
        return times-time67;
    }
    
    /**4.2
     * 传入指定格式的字符串yyyy-MM-dd 和 一个天数  获取一个日期N天之后的日期字符串
     * @param date
     * @param days
     * @return
     * @throws Exception
     */
    public static String dateLatter(String date,int days)throws Exception{
        long time = longtime(date)+(long)days*86400000;
        String d = dateToString(new Date(time));
        return d;
    }
    
    /**4.3
     * 传入指定格式的字符串yyyy-MM-dd 和 一个天数  获取一个日期N天之前的日期字符串
     * @param date
     * @param days
     * @return
     * @throws Exception
     */
    public static String dateBefore(String date,int days)throws Exception{
        long time = longtime(date)-(long)days*24*60*60*1000;
        return dateToString(new Date(time));
    }
     
    /**
     * 日期转换为时间数字串 <从1970至某一时间之间的时间数> 传入指定日期格式 
     * @param aMask
     * @param date
     * @return
     */
    public static long longtime(String aMask,String date){
        long l =0;
        try {
        Calendar c =  Calendar.getInstance();
        c.setTime(stringToDate(aMask,date));
        l = c.getTimeInMillis();
        } catch (Exception e) {
           CommonUtils.deBug(e.getMessage());
        }
        return l;
    }
    /**
     * 日期转换为时间数字串 <从1970至某一时间之间的时间数> 传入yyyy-MM-dd格式
     * @param aMask
     * @param date
     * @return
     */
    public static long longtime(String date){
        long l =0;
        try {
        Calendar c =  Calendar.getInstance();
        c.setTime(stringToDate(date));
        l = c.getTimeInMillis();
        } catch (Exception e) {
           CommonUtils.deBug(e.getMessage());
        }
        return l;
    }
    /**
     * 日期转换为时间数字串 <从1970至某一时间之间的时间数> 默认当前日期
     * @param aMask
     * @param date
     * @return
     */
    public static long longtime(){
        long l =0;
        try {
        Calendar c =  Calendar.getInstance();
        l = c.getTimeInMillis();
        } catch (Exception e) {
           CommonUtils.deBug(e.getMessage());
        }
        return l;
    }
    /**
     * 得到系统当前时间yyyy年mm月dd日
     * @return
     */
    public static String getDate(){
    	String date = getTime(DATE_PATTERN);
        date = date.replace("-", "年");
        date = date.substring(0,7)+"月"+date.substring(8,10);
        date = date+"日";
        return date;
    }
    /**
     * 得到系统当前时间日期格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateTime(){
    	String datetime = getTime(NOW_DATE_TIME);
    	return datetime;
    }
    /**
     * @param Start 开始时间
     * @param End	结束时间
     * @return	开始结束时间段 的所有日期数组
     * @throws ParseException
     */
    public static List beforeEndDate(String Start,String End) throws ParseException{
    	List l = new ArrayList();
    	
    	String   dateStart   =   Start   ;   
        String   dateEnd   =   End   ;   

        SimpleDateFormat   sdfStart   =   new   SimpleDateFormat(DATE_PATTERN);   
        SimpleDateFormat   sdfEnd   =   new   SimpleDateFormat(DATE_PATTERN);   
          
		sdfStart.parse(dateStart);
        Calendar   clStart   =   sdfStart.getCalendar();   
        sdfEnd.parse(dateEnd);   
        Calendar   clEnd   =   sdfEnd.getCalendar();   
        while(true)   
        {   
                if   (   clStart.before   (   clEnd   )   ||   clStart.equals(clEnd))   
                {   
                		l.add(sdfStart.format(clStart.getTime()));
                        //System.out.println(sdfStart.format(clStart.getTime()));   
                        clStart.add   (   clStart.DAY_OF_MONTH   ,   1   )   ;   
                        clStart.set(clStart.DAY_OF_MONTH,clStart.get(clStart.DAY_OF_MONTH));   
                }   
                else   
                {   
                        break   ;   
                }   
        }
		return l;   
    }
    
    
    public static String formatDbDateToWebDate(String dbDate)
	{
		if (StringUtils.isBlank(dbDate))
		{
			return null;
		} else
		{
			StringBuffer sb = new StringBuffer();
			String year = dbDate.substring(0, 4);
			String month = dbDate.substring(5, 7);
			String date = dbDate.substring(8, 10);
			sb.append(year);
			sb.append("年");
			sb.append(month);
			sb.append("月");
			sb.append(date);
			sb.append("日");
			return sb.toString();
		}
	}
    
    public static String formatDbDateToWebDateTime(String dbDate)
	{
		if (StringUtils.isBlank(dbDate))
			return null;
		StringBuffer sb = new StringBuffer();
		String year = dbDate.substring(0, 4);
		String month = dbDate.substring(5, 7);
		String date = dbDate.substring(8, 10);
		String hour = dbDate.substring(11, 13);
		String mins = dbDate.substring(14, 16);
		if (StringUtils.equals(hour, "00") && StringUtils.equals(mins, "00"))
		{
			sb.append(year);
			sb.append("年");
			sb.append(month);
			sb.append("月");
			sb.append(date);
			sb.append("日");
		} else
		{
			sb.append(year);
			sb.append("年");
			sb.append(month);
			sb.append("月");
			sb.append(date);
			sb.append("日");
			sb.append(" ");
			sb.append(hour);
			sb.append(":");
			sb.append(mins);
		}
		return sb.toString();
	}
    /**
	 * @param date  日期 (如果长度大于4就视为带年月日的日期) 如果为空就为当前日期进行操作
	 * @param chars 转换格式符  如果chars 为 * 则会将日期转化成 2010年08月08日 的形式
	 * @param flag  是否去掉年 true 为去掉 false为带年份,没有年份自动加年份
	 * @return 
	 * 方法功能: 对节日 日期进行指定转换
	 */
	public static String doConvertDate(String date , String chars , boolean flag){
		String rdate="";
		String cvyear="";
		String cvmonth="";
		String cvday="";
		if("".equals(date)){
			date = getYear() + "-" + getMonth() + "-" + getDay();
		}
		if(date.length()>4){
			cvyear = date.substring(0, 4);
			cvmonth = date.substring(5, 7);
			if(date.length()>10){
				cvday = date.substring(8,10);
			}else{
				cvday = date.substring(8);
			}
		}else{
			cvyear = DateUtil.getYear();
			cvmonth = date.substring(0,2);
			cvday = date.substring(2);
		}
		if(chars.equals("*")){  //2010年08月08日 的形式
			rdate = cvyear + "年" + cvmonth + "月" + cvday + "日";
		}else{//其他特殊符号
			rdate = cvyear + chars + cvmonth + chars + cvday;
		}
		if(flag){ //不要年份的情况
			if("".equals(chars)){
				rdate = rdate.substring(4);
			}else{
				rdate = rdate.substring(5);
			}
		}
		return rdate;
	}
    
//	 判断是否为闰年 返回不同日期
	public static String isLeapYear(String year,String month) {
		String[] s = null;// 要返回的是当前月的最后日如 闰年则二月为29天，一月31，三月30天
		
		GregorianCalendar g = new GregorianCalendar();
		boolean b = g.isLeapYear(Integer.parseInt(year));// 如果为闰年返回true 否则
		if (b) {
			s = new String[] { "31", "29", "31", "30", "31", "30", "31", "31",
					"30", "31", "30", "31" };

		} else {
			s = new String[] { "31", "28", "31", "30", "31", "30", "31", "31",
					"30", "31", "30", "31" };
		}
		if(Integer.parseInt(month)>0){
			return s[Integer.parseInt(month)-1];
		}else{
			return s[0];
		}
	}
	
	
    public static void main(String[] args) throws ParseException {
    	System.out.println(stringToDate("20101212"));
    }
}

