// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CommonFunc.java

package com.hd.util;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hd.SystemInterface.SystemInterFace;


public final class CommonFunc implements SystemInterFace
{
	
	public static final int SAVETYPE_ONE = 0;
	public static final int SAVETYPE_MORE = 1;
	public static final int COMMIT_RESULT_TRUE = 1;
	public static final int COMMIT_RESULT_FALSE = -1;
	public static final int COMMIT_RESULT_EXPCETION = 0;
	
	public static final String [] sFtv=new String[]{
		"0101*元旦节",
		"0202 世界湿地日",
		"0210 国际气象节",
		"0214 情人节",
		"0301 国际海豹日",
		"0303 全国爱耳日",
		"0305 学雷锋纪念日",
		"0308 妇女节",
		"0312 植树节 孙中山逝世纪念日",
		"0314 国际警察日",
		"0315 消费者权益日",
		"0317 中国国医节 国际航海日",
		"0321 世界森林日 消除种族歧视国际日 世界儿歌日",
		"0322 世界水日",
		"0323 世界气象日",
		"0324 世界防治结核病日",
		"0325 全国中小学生安全教育日",
		"0330 巴勒斯坦国土日",
		"0401 愚人节 全国爱国卫生运动月(四月) 税收宣传月(四月)",
		"0407 世界卫生日",
		"0422 世界地球日",
		"0423 世界图书和版权日",
		"0424 亚非新闻工作者日",
		"0501*劳动节",
		"0504 青年节",
		"0505 碘缺乏病防治日",
		"0508 世界红十字日",
		"0512 国际护士节",
		"0515 国际家庭日",
		"0517 国际电信日",
		"0518 国际博物馆日",
		"0520 全国学生营养日",
		"0523 国际牛奶日",
		"0531 世界无烟日", 
		"0601 国际儿童节",
		"0605 世界环境保护日",
		"0606 全国爱眼日",
		"0617 防治荒漠化和干旱日",
		"0623 国际奥林匹克日",
		"0625 全国土地日",
		"0626 国际禁毒日",
		"0701 香港回归纪念日 中共诞辰 世界建筑日",
		"0702 国际体育记者日",
		"0707 抗日战争纪念日",
		"0711 世界人口日",
		"0730 非洲妇女日",
		"0801 建军节",
		"0808 中国男子节(爸爸节)",
		"0815 抗日战争胜利纪念",
		"0908 国际扫盲日 国际新闻工作者日",
		"0909 毛泽东逝世纪念",
		"0910 中国教师节", 
		"0914 世界清洁地球日",
		"0916 国际臭氧层保护日",
		"0918 九·一八事变纪念日",
		"0920 国际爱牙日",
		"0927 世界旅游日",
		"0928 孔子诞辰",
		"1001*国庆节 世界音乐日 国际老人节",
		"1002*国庆节假日 国际和平与民主自由斗争日",
		"1003*国庆节假日",
		"1004 世界动物日",
		"1006 老人节",
		"1008 全国高血压日 世界视觉日",
		"1009 世界邮政日 万国邮联日",
		"1010 辛亥革命纪念日 世界精神卫生日",
		"1013 世界保健日 国际教师节",
		"1014 世界标准日",
		"1015 国际盲人节(白手杖节)",
		"1016 世界粮食日",
		"1017 世界消除贫困日",
		"1022 世界传统医药日",
		"1024 联合国日",
		"1031 世界勤俭日",
		"1107 十月社会主义革命纪念日",
		"1108 中国记者日",
		"1109 全国消防安全宣传教育日",
		"1110 世界青年节",
		"1111 国际科学与和平周(本日所属的一周)",
		"1112 孙中山诞辰纪念日",
		"1114 世界糖尿病日",
		"1117 国际大学生节 世界学生节",
		"1120*彝族年",
		"1121*彝族年 世界问候日 世界电视日",
		"1122*彝族年",
		"1129 国际声援巴勒斯坦人民国际日",
		"1201 世界艾滋病日",
		"1203 世界残疾人日",
		"1205 国际经济和社会发展志愿人员日",
		"1208 国际儿童电视日",
		"1209 世界足球日",
		"1210 世界人权日",
		"1212 西安事变纪念日",
		"1213 南京大屠杀(1937年)纪念日！谨记血泪史！",
		"1220 澳门回归纪念",
		"1221 国际篮球日",
		"1224 平安夜",
		"1225 圣诞节",
		"1226 毛泽东诞辰纪念"};
		
	public static final String[] lFtv = new String[]{
				"0101*春节",
				"0102*初二",
				"0115 元宵节",
				"0505*端午节",
				"0707 七夕情人节",
				"0715 中元节",
				"0815*中秋节",
				"0909 重阳节",
				"1208 腊八节",
				"1223 小年",
				"0100*除夕"};
//		某月的第几个星期几
	public static final String[] wFtv = new String[]{
		"0150 世界麻风日", //一月的最后一个星期日（月倒数第一个星期日）
		"0520 国际母亲节",
		"0530 全国助残日",
		"0630 父亲节",
		"0730 被奴役国家周",
		"0932 国际和平日",
		"0940 国际聋人节 世界儿童日",
		"0950 世界海事日",
		"1011 国际住房日",
		"1013 国际减轻自然灾害日(减灾日)",
		"1144 感恩节"};
	public static final long[] tInfo=new long[]{
				0x04bd8,0x04ae0,0x0a570,0x054d5,0x0d260,0x0d950,0x16554,0x056a0,0x09ad0,0x055d2,
				0x04ae0,0x0a5b6,0x0a4d0,0x0d250,0x1d255,0x0b540,0x0d6a0,0x0ada2,0x095b0,0x14977,
				0x04970,0x0a4b0,0x0b4b5,0x06a50,0x06d40,0x1ab54,0x02b60,0x09570,0x052f2,0x04970,
				0x06566,0x0d4a0,0x0ea50,0x06e95,0x05ad0,0x02b60,0x186e3,0x092e0,0x1c8d7,0x0c950,
				0x0d4a0,0x1d8a6,0x0b550,0x056a0,0x1a5b4,0x025d0,0x092d0,0x0d2b2,0x0a950,0x0b557,
				0x06ca0,0x0b550,0x15355,0x04da0,0x0a5b0,0x14573,0x052b0,0x0a9a8,0x0e950,0x06aa0,
				0x0aea6,0x0ab50,0x04b60,0x0aae4,0x0a570,0x05260,0x0f263,0x0d950,0x05b57,0x056a0,
				0x096d0,0x04dd5,0x04ad0,0x0a4d0,0x0d4d4,0x0d250,0x0d558,0x0b540,0x0b6a0,0x195a6,
				0x095b0,0x049b0,0x0a974,0x0a4b0,0x0b27a,0x06a50,0x06d40,0x0af46,0x0ab60,0x09570,
				0x04af5,0x04970,0x064b0,0x074a3,0x0ea50,0x06b58,0x055c0,0x0ab60,0x096d5,0x092e0,
				0x0c960,0x0d954,0x0d4a0,0x0da50,0x07552,0x056a0,0x0abb7,0x025d0,0x092d0,0x0cab5,
				0x0a950,0x0b4a0,0x0baa4,0x0ad50,0x055d9,0x04ba0,0x0a5b0,0x15176,0x052b0,0x0a930,
				0x07954,0x06aa0,0x0ad50,0x05b52,0x04b60,0x0a6e6,0x0a4e0,0x0d260,0x0ea65,0x0d530,
				0x05aa0,0x076a3,0x096d0,0x04bd7,0x04ad0,0x0a4d0,0x1d0b6,0x0d250,0x0d520,0x0dd45,
				0x0b5a0,0x056d0,0x055b2,0x049b0,0x0a577,0x0a4b0,0x0aa50,0x1b255,0x06d20,0x0ada0,
				0x14b63};
	public CommonFunc()
	{
	}

	public static final int createIntRandom()
	{
		return (int)(1000000000D * Math.random());
	}

	public static String dateToString(Calendar cal, String mid)
	{
		String strdate = null;
		try
		{
			Date currentDate = new Date();
			SimpleDateFormat formatter;
			if (mid.equals("-"))
				formatter = new SimpleDateFormat("yyyy-MM-dd");
			else
			if (mid.equals(""))
				formatter = new SimpleDateFormat("yyyyMMdd");
			else
				formatter = new SimpleDateFormat("yyyy年MM月dd日");
			currentDate = cal.getTime();
			strdate = formatter.format(currentDate);
		}
		catch (Exception exception) { }
		return strdate;
	}

	public static final String CurrentTime()
	{
		String curTime = "";
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 kk:mm:ss");
		currentDate = Calendar.getInstance().getTime();
		curTime = formatter.format(currentDate);
		return curTime;
	}

	public static final String CurrentDate()
	{
		String curTime = "";
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		currentDate = Calendar.getInstance().getTime();
		curTime = formatter.format(currentDate);
		return curTime;
	}

	public static final String CurrentTimeyyyyMMddkkmm()
	{
		String curTime = "";
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddkkmm");
		currentDate = Calendar.getInstance().getTime();
		curTime = formatter.format(currentDate);
		return curTime;
	}

	public static String getNowTime()
	{
		return Calendar.getInstance().getTime().toLocaleString();
	}

	public static final String CurrentDate(String curTime)
	{
		String curDate = "";
		return curDate;
	}

	public static final String CurrentTime(String curTime)
	{
		String curDate = "";
		return curDate;
	}

	public static final String getGUID()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddkmmssSSS");
		Date currentDate = Calendar.getInstance().getTime();
		String curTime = formatter.format(currentDate);
		return curTime + createIntRandom();
	}
	/**
	 * @param iLen
	 * @param iType
	 * @return 
	 * 方法功能: 生成随机码
	 */
	public static final String CreateRadom(int iLen, int iType)
	{
		String strRandom = "";
		Random rnd = new Random();
		if (iLen < 0)
			iLen = 5;
		if (iType > 2 || iType < 0)
			iType = 2;
		switch (iType)
		{
		default:
			break;

		case 0: // '\0'
			for (int iLoop = 0; iLoop < iLen; iLoop++)
				strRandom = strRandom + Integer.toString(rnd.nextInt(10));

			break;

		case 1: // '\001'
			for (int iLoop = 0; iLoop < iLen; iLoop++)
				strRandom = strRandom + Integer.toString(35 - rnd.nextInt(10), 36);

			break;

		case 2: // '\002'
			for (int iLoop = 0; iLoop < iLen; iLoop++)
				strRandom = strRandom + Integer.toString(rnd.nextInt(36), 36);

			break;
		}
		return strRandom;
	}

	public static final String getWeekName(String date)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		try
		{
			Date tempDate = df.parse(date);
			cal.setTime(tempDate);
			df = new SimpleDateFormat("EEEE");
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return df.format(cal.getTime());
	}

	public static final String getAddDate(String date, int days)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		Date tempDate = null;
		try
		{
			tempDate = df.parse(date);
			long time = tempDate.getTime();
			time += days * 24 * 60 * 60 * 1000;
			tempDate.setTime(time);
			cal.setTime(tempDate);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return df.format(cal.getTime());
	}

	private void writeObject(ObjectOutputStream oos)
		throws IOException
	{
		oos.defaultWriteObject();
	}

	private void readObject(ObjectInputStream ois)
		throws ClassNotFoundException, IOException
	{
		ois.defaultReadObject();
	}
	public static void main(String args1[])
	{
		System.out.println(new CommonFunc().CreateRadom(11, 2));
		System.out.println(new CommonFunc().CurrentTimeyyyyMMddkkmm());
		System.out.println(new CommonFunc().getGUID());
		System.out.println(new CommonFunc().getNowTime());
	}
}
