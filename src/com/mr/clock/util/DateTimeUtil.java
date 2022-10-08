package com.mr.clock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//日期时间工具类

public class DateTimeUtil {

public static String timeNow() {//获取当前时间
	return new SimpleDateFormat("HH:mm:ss").format(new Date());
}
public static String dateNow() {//获取当前日期
	return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
}
public static String dateTimeNow() {//获取当前日期和时间
	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
}
public static Integer[] now() {//获取由当前年、月、日、时、分、秒数字所组成的数组
	Integer now[]=new Integer[6];//保存年、月、日、时、分、秒的数组
	Calendar c = Calendar.getInstance();//日历对象
	now[0] = c.get(Calendar.YEAR);//年
	now[1] = c.get(Calendar.MONDAY)+1;//月
	now[2] = c.get(Calendar.DAY_OF_MONTH);//日
	now[3] = c.get(Calendar.HOUR_OF_DAY);//时
	now[4] = c.get(Calendar.MINUTE);//分
	now[5] = c.get(Calendar.SECOND);//秒
	return now;
}
public static int getLastDay(int year,int month) {//获取指定月份的总天数
	Calendar c = Calendar.getInstance();// 日历对象
	c.set(Calendar.YEAR, year);// 指定年
	c.set(Calendar.MONTH, month-1);// 指定月
	return c.getActualMaximum(Calendar.DAY_OF_MONTH);// 返回这月的最后一天
}
public static Date dateOf(String datetime)throws ParseException{//以yyyy-MM-dd HH:mm:ss格式将字符串转化为Date对象
	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
}
public static Date dateOf(int year,int month,int day,String time)throws ParseException{// 创建指定日期时间的Date对象
	String datetime = String.format("%4d-%02d-%02d %s",year,month,day,time);
	return dateOf(datetime);
}
public static boolean checkTimeStr(String time) {//检查时间字符串是否符合HH:mm:ss格式
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	try {
		sdf.parse(time);// 将时间字符串转为Date对象
		return true;
	} catch (ParseException e) {// 发生异常则表示字符串格式错误
		return false;
		// TODO: handle exception
	}
	
}
}
