/**
 * @(#)CommonUtils.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-8 chenwei 创建版本
 */
package com.hzq.test.annotation;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.BigDecimal;

/**
 * 
 * 基础公共工具类
 * 
 * @author chenwei
 */
public class CommonUtils {
	public static int decimalLen=2;
	/**
	 * request.getParameterMap()获取到的map转换为string=string的map 支持一对一的form表单提交的数据
	 * 比如select text radio等 不支持checkbox 该方法主要是把查询条件封装在map里面，方便mybatis里使用map查询
	 * 
	 * @param map
	 *            <String, String[]>
	 * @return Map<String, String>
	 */


	/**
	 * MD5加密
	 * 
	 * @param inStr
	 * @return
	 */
	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = (md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 获取客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.indexOf("0:") != -1) {
			ip = "本地";
		}
		return ip;
	}
	/**
	 * 流转二进制
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] Stream2Byte(InputStream in) throws IOException {
		byte[] temp = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		int size = 0;
		while ((size = in.read(temp)) != -1) {
			out.write(temp, 0, size);
		}
		in.close();
		byte[] content = out.toByteArray();
		return content;
	}

	/**
	 * 日期格式转换 yyyy-MM-dd格式转换为yyyyMMdd格式
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String dateStringChange(String dateStr) throws Exception {
		String str = null;
		if (null != dateStr) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sf.parse(dateStr);
			SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
			str = sf1.format(date);
		}
		return str;
	}
	/**
	 * 日期格式转换 yyyy-MM-dd格式转换为format定义的格式
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 13, 2013
	 */
	public static String dateStringChange(String dateStr,String format) throws Exception {
		String str = null;
		if (null != dateStr) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sf.parse(dateStr);
			SimpleDateFormat sf1 = new SimpleDateFormat(format);
			str = sf1.format(date);
		}
		return str;
	}
	/**
	 * 日期格式转换 yyyyMMdd格式转换为yyyy-MM-dd格式
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static String dateStringChange1(String dateStr) throws Exception {
		String str = null;
		if (null != dateStr) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			Date date = sf.parse(dateStr);
			SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
			str = sf1.format(date);
		}
		return str;
	}

	/**
	 * 判断字符串是否属于（yyyy-mm-dd）的日期格式
	 * 
	 * @param dataStr
	 * @return
	 * @author chenwei
	 * @date 2013-1-11
	 */
	public static boolean isDataStr(String dataStr) {
		Pattern p = Pattern
				.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)");
		Matcher m = p.matcher(dataStr);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否属于 yyyy-mm或MM-dd或DD hh或h:mm或m:ss或s 的日期格式
	 * 
	 * @param datetimeStr
	 * @return
	 * @author zhanghonghui
	 * @date 2013-1-11
	 */
	public static boolean isDateTimeStr(String datetimeStr) {
		if (StringUtils.isEmpty(datetimeStr.trim())) {
			return false;
		}
		Pattern p = Pattern
				.compile("\\d{4}[-]([1-9]|[0][1-9]|1[0-2])[-]([1-9]|[0-2][0-9]|3[0-1]) ([1-9]|[01][0-9]|2[0-3]):([1-9]|[0-5][0-9]):([1-9]|[0-5][0-9])");
		Matcher m = p.matcher(datetimeStr);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断字符串是否属于 yyyy-MM-DD hh:mm:ss 的日期格式
	 * 
	 * @param datetimeStr
	 * @return
	 * @author zhanghonghui
	 * @date 2013-1-11
	 */
	public static boolean isDateTimeStandStr(String datetimeStr) {
		if (null==datetimeStr || "".equals(datetimeStr.trim())) {
			return false;
		}
		Pattern p = Pattern
				.compile("\\d{4}[-]([0][1-9]|1[0-2])[-]([0-2][0-9]|3[0-1]) ([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])");
		Matcher m = p.matcher(datetimeStr);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 时间转换成字符串格式
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            格式（yyyy-MM-dd）
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String dataToStr(Date date, String format) {
		if (null == format || "".equals(format)) {
			format = "yyyy-MM-dd";
		}
		String dataStr = "";
		SimpleDateFormat sf = new SimpleDateFormat(format);
		dataStr = sf.format(date);
		return dataStr;
	}

	/**
	 * 获取当前时间的所属月份的第一天日期
	 * 
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getNowMonthDay() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		String mon = "";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		String dateStr = year + "-" + mon + "-01";
		return dateStr;
	}
	/**
	 * 获取当前时间的所属月份的第一天日期
	 * @param date
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getNowMonthDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		String mon = "";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		String resultStr = year + "-" + mon + "-01";
		return resultStr;
	}

	/**
	 * 获取当前时间所属季度的开始日期
	 * 
	 * @return (yyyy-MM-dd)
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getQuarterBeginDate() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		if (month >= 1 && month <= 3) {
			return year + "-01-01";
		} else if (month >= 4 && month <= 6) {
			return year + "-04-01";
		} else if (month >= 7 && month <= 9) {
			return year + "-07-01";
		} else if (month >= 10 && month <= 12) {
			return year + "-10-01";
		}
		return null;
	}

	/**
	 * 获取当前时间所属季度的开始日期
	 * 
	 * @return (yyyy-MM-dd)
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getQuarterEndDate() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		if (month >= 1 && month <= 3) {
			return year + "-03-31";
		} else if (month >= 4 && month <= 6) {
			return year + "-6-30";
		} else if (month >= 7 && month <= 9) {
			return year + "-9-30";
		} else if (month >= 10 && month <= 12) {
			return year + "-12-31";
		}
		return null;
	}

	/**
	 * 表名前缀操作<br/>
	 * 处理tablename,如果tablename没有前缀t_,给tablename加上前缀
	 * 
	 * @param tablename
	 * @return
	 * @author chenwei
	 * @date 2013-1-17
	 */
	public static String tablenameDealWith(String tablename) {
		// 判断表名中是否包含前缀t_
		if (!"t_".equals(tablename.substring(0, 2)) && !"RL".equals(tablename.substring(0, 2)) && !"RT".equals(tablename.substring(0, 2))) {
			tablename = "t_" + tablename;
		}
		return tablename;
	}

	/**
	 * 字符串转时间。（yyyy-MM-dd）
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2013-1-30
	 */
	public static Date stringToDate(String str) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sf.parse(str);
		return date;
	}
	
	/**
	 * 字符串转时间,指定日期格式
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2013-1-30
	 */
	public static Date stringToDate(String str,String format) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date date = sf.parse(str);
		return date;
	}

	/**
	 * 判断当前日期是星期几<br>
	 * <br>
	 * 
	 * @param day
	 *            修要判断的时间<br>
	 * @return dayForWeek 判断结果<br>
	 * @Exception 发生异常<br>
	 */
	public static String dayForWeek(String day) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		final String dayNames[] = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"}; 
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(day));
		int dayForWeek = 0;
		dayForWeek = c.get(Calendar.DAY_OF_WEEK);
		return dayNames[dayForWeek - 1];
	}
	/**
	 * 对象克隆<br/>
	 * 对数据进行转码时，克隆数据对象 防止影响缓存的数据
	 * @param src
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @author chenwei 
	 * @date Mar 2, 2013
	 */
	public static Object  deepCopy(Object  src) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);
		out.close();
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		Object  dest =  in.readObject();
		in.close();
		return dest;
	}
	/**
	 * 把list（Map）转成tree格式
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date Mar 1, 2013
	 */
	public static List getTableMapDataTree(List<Map> list){
		for(Map map : list){
			String parentid = (String) map.get("pId");
			if(map.containsKey("url")){
				map.remove("url");
			}
			getTableMapDataTreeChild(map,list);
		}
		return list;
	}
	/**
	 * 递归查找子节点(Map)
	 * @param map
	 * @param list
	 * @author chenwei 
	 * @date Mar 1, 2013
	 */
	public static void getTableMapDataTreeChild(Map map,List<Map> list){
		String isParent = "false";
		for(Map childMap : list){
			String id = (String) map.get("id");
			String parentid = (String) childMap.get("pId");
				if(id.equals(parentid)){
					isParent = "true";
					break;
				}
		}
		if(!map.containsKey("isParent")){
			map.put("isParent", isParent);
		}
	}

	/**
	 * 获取（yyyyMMddHHmmssSSS）格式的时间字符串（带毫秒）
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public static String getDataNumber(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str = sf.format(new Date());
		return str;
	}

    /**
     *获取（yyyyMMddHHmmssSSS）格式的时间字符串（带随机数）
     * @return
     */
    public static String getDataNumberWithRand(){
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String str = sf.format(new Date());
        long i = Math.round(Math.random()*899+100);
        str += i;
        return str;
    }
	/**
	 * 获取（yyMMddHHmmssSSS）格式的时间字符串（带毫秒）
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public static String getDataNumber1(){
		SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmssSSS");
		String str = sf.format(new Date());
		return str;
	}
	/**
	 * 获取（yyyyMMddHHmmss）格式的时间字符串（带秒）
	 * @return
	 * @author chenwei 
	 * @date May 24, 2013
	 */
	public static String getDataNumberSeconds(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = sf.format(new Date());
		return str;
	}
	/**
	 * 获取当期毫秒数字符串 + 3位随机数
	 * @return
	 * @author chenwei 
	 * @date May 30, 2013
	 */
	public static String getDataNumberSendsWithRand(){
//		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//		String str = sf.format(new Date());
		String str = System.currentTimeMillis()+"";
		long i = Math.round(Math.random()*899+100);
		str  = str + i;
		return str;
	}
	/**
	 * 获取（yyyyMMddHHmmssSSS）格式的时间字符串（带毫秒）+3位随机数
	 * @return
	 * @author chenwei 
	 * @date 2015年2月7日
	 */
	public static String getDateNowTimeWithRand(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str = sf.format(new Date());
		long i = Math.round(Math.random()*899+100);
		str  = str + i;
		return str;
	}
	/**
	 * 获取今天日期
	 * @return
	 * @author chenwei 
	 * @date Jul 11, 2013
	 */
	public static String getTodayDataStr(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sf.format(new Date());
		return str;
	}
	/**
	 * 获取当前年月（yyyy-MM）
	 * @return
	 * @author chenwei 
	 * @date Apr 1, 2014
	 */
	public static String getTodayMonStr(){
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		String str = smf.format(now);
		return str;
	}
	/**
	 * 获取当前年第一个月
	 * @return
	 * @author chenwei 
	 * @date Apr 1, 2014
	 */
	public static String getCurrentYearFirstMonStr(){
		SimpleDateFormat smf = new SimpleDateFormat("yyyy");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		String str = smf.format(now);
		if(null!=str && !"".equals(str)){
			str=str+"-01";
		}
		return str;
	}

	/**
	 * 获取上一个月的日期，格式年月
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static String getCurrentYearLastMonStrMonStr(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MONTH,-1);
		date=calendar.getTime();
		String str = sf.format(date);
		return str;
	}
	/**
	 * 当前年份
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-4
	 */
	public static String getCurrentYearStr(){
		SimpleDateFormat smf = new SimpleDateFormat("yyyy");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		String str = smf.format(now);
		return str;
	}
	/**
	 * 获取当前月份
	 * @return
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	public static String getCurrentMonthStr(){
		SimpleDateFormat smf = new SimpleDateFormat("MM");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		String str = smf.format(now);
		return str;
	}
    /**
     * 根据日期字符串获取该日期的月份
     * @param datestr
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    public static String getMonthStr(String datestr){
        String str = "";
        try {
            SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
            Date date =smf.parse(datestr);
            SimpleDateFormat smf1 = new SimpleDateFormat("MM");
            str = smf1.format(date);
        }catch (Exception e){
            return "";
        }
        return str;
    }

    /**
     * 根据日期字符串获取该日期的年
     * @param datestr
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    public static String getYearStr(String datestr){
        String str = "";
        try {
            SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
            Date date =smf.parse(datestr);
            SimpleDateFormat smf1 = new SimpleDateFormat("yyyy");
            str = smf1.format(date);
        }catch (Exception e){
            return "";
        }
        return str;
    }

	/**
	 * 获取昨天日期
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static String getYestodayDateStr(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime();
		String str = sf.format(date);
		return str;
	}
	/**
	 * 获取昨天日期
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static Date getYestodayDate(){
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime();
		return date;
	}
	/**
	 * 获取传入的时间的前一天
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static Date getYestodayDate(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime();
		return date;
	}
	/**
	 * 获取特定日期的前一天日期
	 * @param date
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public static String getYestodayByDate(String date){
		String str = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date dateObject = sf.parse(date);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateObject);
			calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
			dateObject=calendar.getTime();
			str = sf.format(dateObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 将字符串型业务日期转为日期型
	 * @param dateStr
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-7
	 */
	public static Date getBusinessdateByString(String dateStr){
		Date date=null;
		if(null!=dateStr && !"".equals(dateStr.trim())){
			try {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				date = sf.parse(dateStr.trim());
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		return date;
	}
	/**
	 * 获取特定日期的下一天日期
	 * @param date
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public static String getNextDayByDate(String date){
		String str = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date dateObject = sf.parse(date);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateObject);
			calendar.add(calendar.DATE,+1);//把日期往后增加一天.整数往后推,负数往前移动
			dateObject=calendar.getTime();
			str = sf.format(dateObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 获取当前日期 前几天的日期
	 * @param days
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public static String getBeforeDateInDays(int days){
		String str = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date dateObject = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateObject);
			//把日期往后增加一天.整数往后推,负数往前移动
			calendar.add(calendar.DATE,-days);
			dateObject=calendar.getTime();
			str = sf.format(dateObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
    /**
     * 获取当前日期 前几个月的日期
     * @param month
     * @return
     * @author chenwei
     * @date Aug 16, 2013
     */
    public static String getBeforeDateInMonth(int month){
        String str = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateObject = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(dateObject);
            //把日期往后增加一天.整数往后推,负数往前移动
            calendar.add(calendar.MONTH,-month);
            dateObject=calendar.getTime();
            str = sf.format(dateObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
	/**
	 * 获取当前日期 前几天的日期
	 * @param date
	 * @param days
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月17日
	 */
	public static String getBeforeDateInDays(Date date,int days){
		String str = null;
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			//把日期往后增加一天.整数往后推,负数往前移动
			calendar.add(calendar.DATE,-days);
			Date afDate=calendar.getTime();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			str = sf.format(afDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 获取当前日期 前几天的日期 ,返回类型为Date
	 * @param date
	 * @param days
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月17日
	 */
	public static Date getBeforeTheDateInDays(Date date,int days){
		Date theDate = null;
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			//把日期往后增加一天.整数往后推,负数往前移动
			calendar.add(calendar.DATE,-days);
			theDate=calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theDate;
	}
	
	/**
	 * 获取本月第一天
	 * @return
	 * @author chenwei 
	 * @date Jul 18, 2013
	 */
	public static String getMonthDateStr(){
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar c = Calendar.getInstance();
//		c.add(Calendar.MONTH, -1);    //得到前一个月
//		Date date = c.getTime();
//		String str = sf.format(date);
		return getNowMonthDay();
	}
	/**
	 * 过滤html标签
	 * @param html
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-15
	 */
	public static String htmlFilter(String html){
		if(StringUtils.isEmpty(html)){
			return "";
		}
		String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>      
        String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>      
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式      
        String regEx_html1 = "<[^>]+";      
        Pattern pattern =null; 
        Matcher matcher =null;
        pattern= Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE); 
        if(pattern!=null){
        	matcher = pattern.matcher(html);
        	if(matcher!=null){
        		html = matcher.replaceAll(""); // 过滤script标签
        	}
        }
        pattern = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        if(pattern!=null){
        	matcher = pattern.matcher(html);
        	if(matcher!=null){
        		html = matcher.replaceAll(""); // 过滤style标签  
        	}
        }
        pattern = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        if(pattern!=null){
        	matcher = pattern.matcher(html);
        	if(matcher!=null){
        		html = matcher.replaceAll(""); // 过滤html标签
        	}
        }        
        pattern = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
        if(pattern!=null){
        	matcher = pattern.matcher(html);
        	if(matcher!=null){
        		html = matcher.replaceAll(""); // 过滤html标签
        	}
        }
        return html;
	}
	/**
	 * 根据时间获取quartz表达式.
	 * 精确到分钟
	 * @param date
	 * @return
	 * @author chenwei 
	 * @date Mar 19, 2013
	 */
	public static String getQuartzCronExpression(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String year = ""+calendar.get(Calendar.YEAR);
		String month = ""+ (calendar.get(Calendar.MONTH)+1);
		String day = "" + calendar.get(Calendar.DAY_OF_MONTH);
		String hour = "" +calendar.get(Calendar.HOUR_OF_DAY);
		String min = "" + calendar.get(Calendar.MINUTE);
		String sen = "" + calendar.get(Calendar.SECOND);
		String con = sen +" "+min+" "+hour+" "+day+" "+month+" ? "+year;
		return con;
	}
	
	/**
	 * 判断表达式是否验证通过
	 * 
	 * @param pattern
	 * @param validstr
	 * @return
	 * @author zhanghonghui
	 * @date 2013-1-11
	 */
	public static boolean isPatternValid(String pattern,String validstr) {
		if (StringUtils.isEmpty(pattern) || StringUtils.isEmpty(validstr)) {
			return false;
		}
		Pattern p = Pattern
				.compile(pattern);
		Matcher m = p.matcher(validstr);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * 获取当前时间的前一个年度的本月第一天日期
	 * 
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getPrevYearFirstMonthDay() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		year=year-1;
		c.set(Calendar.YEAR,year);
		int month = c.get(Calendar.MONTH)+1;
		String mon = "";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		String dateStr = year + "-" + mon + "-01";
		return dateStr;
	}
	/**
	 *  获取当前时间的前一个年度的本月第一天日期
	 * @param date
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-15
	 */
	public static String getPrevYearFirstMonthDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		year=year-1;
		c.set(Calendar.YEAR,year);
		int month = c.get(Calendar.MONTH)+1;
		String mon = "";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		String dateStr = year + "-" + mon + "-01";
		return dateStr;
	}
	/**
	 * 获取当前时间的前一个年度的本月同一天日期
	 * 
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getPrevYearCurrentMonthDay() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		year=year-1;
		c.set(Calendar.YEAR,year);
		int month = c.get(Calendar.MONTH)+1;
		int day=c.get(Calendar.DATE);
		String mon = "";
		String days="";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		if(day<10){
			days="0"+day;
		}else{
			days=day+"";
		}
		String dateStr = year + "-" + mon + "-"+days;
		return dateStr;
	}
	/**
	 * 获取当前时间的前一个年度的本月同一天日期
	 * 
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getPrevYearCurrentMonthDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		year=year-1;
		c.set(Calendar.YEAR,year);
		int month = c.get(Calendar.MONTH)+1;
		int day=c.get(Calendar.DATE);
		String mon = "";
		String days="";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		if(day<10){
			days="0"+day;
		}else{
			days=day+"";
		}
		String dateStr = year + "-" + mon + "-"+days;
		return dateStr;
	}
	/**
	 * 获取当前时间的前一个年度的本月最后一天日期
	 * 
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getPrevYearCurrentMonthLastDay() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		year=year-1;
		c.set(Calendar.YEAR,year);
		int month = c.get(Calendar.MONTH)+1;
		int day=c.getActualMaximum(Calendar.DAY_OF_MONTH);  
		String mon = "";
		String days="";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		if(day<10){
			days="0"+day;
		}else{
			days=day+"";
		}
		String dateStr = year + "-" + mon + "-"+days;
		return dateStr;
	}
	/**
	 * 获取当前时间的前一个年度的本月同一天日期
	 * 
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getCurrentMonthLastDay() {
		Calendar c = Calendar.getInstance();
		int mon = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH,mon);
		int month = c.get(Calendar.MONTH)+1;
		int year =  c.get(Calendar.YEAR);
		String monstr = "";
		if (month < 10) {
			monstr = "0" + month;
		}else{
			monstr = month+"";
		}
		int day =  c.getActualMaximum(Calendar.DAY_OF_MONTH);  
		String daystr = "";
		if(day< 10){
			daystr = "0" + day;
		}else{
			daystr = day+"";
		}
		String dateStr = year + "-" + monstr +"-"+ daystr;
		return dateStr;
	}
	/**
	 * 获取上个月第一天日期
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public static String getPreMonthFirstDay(){
		Calendar c = Calendar.getInstance();
		int mon = c.get(Calendar.MONTH);
		mon=mon-1;
		c.set(Calendar.MONTH,mon);
		int month = c.get(Calendar.MONTH)+1;
		int year =  c.get(Calendar.YEAR);
		String monstr = "";
		if (month < 10) {
			monstr = "0" + month;
		}else{
			monstr = month+"";
		}
		String dateStr = year + "-" + monstr + "-01";
		return dateStr;
	}
	/**
	 * 获取上个月第一天日期
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public static String getPreMonthFirstDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int mon = c.get(Calendar.MONTH);
		mon=mon-1;
		c.set(Calendar.MONTH,mon);
		int month = c.get(Calendar.MONTH)+1;
		int year =  c.get(Calendar.YEAR);
		String monstr = "";
		if (month < 10) {
			monstr = "0" + month;
		}else{
			monstr = month+"";
		}
		String dateStr = year + "-" + monstr + "-01";
		return dateStr;
	}
	/**
	 * 获取前一个月的日期
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static String getPrevMonthDay(Date date){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MONTH,-1);//把月往前移一个月
		date=calendar.getTime();
		String str = sf.format(date);
		return str;
	}
	/**
	 * 获取下一个月的日期
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static String getNextDateByDays(Date date,int days){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,days);//把月往后移一个月
		date=calendar.getTime();
		String str = sf.format(date);
		return str;
	}
	/**
	 * 获取下一个月的日期
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static String getNextMonthDay(Date date){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MONTH,1);//把月往后移一个月
		date=calendar.getTime();
		String str = sf.format(date);
		return str;
	}
	/**
	 * 根据月间隔，获取几个月间隔的日期,当间隔为负为向前几个月，当间隔为正时，向后几个月
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static String getPrevMonthDay(Date date,int monthinter){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MONTH,monthinter);//把月往前移一个月
		date=calendar.getTime();
		String str = sf.format(date);
		return str;
	}
	/**
	 * 获取上个月最后一天日期
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public static String getPreMonthLastDay(){
		Calendar c = Calendar.getInstance();
		int mon = c.get(Calendar.MONTH);
		mon=mon-1;
		c.set(Calendar.MONTH,mon);
		int month = c.get(Calendar.MONTH)+1;
		int year =  c.get(Calendar.YEAR);
		String monstr = "";
		if (month < 10) {
			monstr = "0" + month;
		}else{
			monstr = month+"";
		}
		int day =  c.getActualMaximum(Calendar.DAY_OF_MONTH);  
		String daystr = "";
		if(day< 10){
			daystr = "0" + day;
		}else{
			daystr = day+"";
		}
		String dateStr = year + "-" + monstr +"-"+ daystr;
		return dateStr;
	}
	/**
	 * 获取上个月的年度
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public static int getPreMonthYear(){
		Calendar c = Calendar.getInstance();
		int mon = c.get(Calendar.MONTH);
		mon=mon-1;
		c.set(Calendar.MONTH,mon);
		int month = c.get(Calendar.MONTH)+1;
		int year =  c.get(Calendar.YEAR);
		return year;
	}
	/**
	 * 获取上个月的月份
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public static int getPreMonth(){
		Calendar c = Calendar.getInstance();
		int mon = c.get(Calendar.MONTH);
		mon=mon-1;
		c.set(Calendar.MONTH,mon);
		int month = c.get(Calendar.MONTH)+1;
		int year =  c.get(Calendar.YEAR);
		return month;
	}
	/**
	 * 获取当期天数
	 * @return
	 * @author chenwei 
	 * @date Jan 6, 2014
	 */
	public static int getNowDay(){
		Calendar c = Calendar.getInstance();
		int day =  c.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	/**
	 * 获取该时间是属于当月第几天
	 * @return
	 * @author chenwei 
	 * @date Mar 22, 2014
	 */
	public static int getDataMonthDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day =  c.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	/**
	 * 判断字符串是否数字
	 * @param numberstr
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public static boolean isNumStr(String numberstr){
		return numberstr.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 获取这个月的最后一天的日期
	 * @param date
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-8-1
	 */
	public static Date getCurrentMonthLastDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.getMaximum(Calendar.DATE)); 
		return c.getTime();
	}
	
	/**
	 * 获取月的最后一天日期
	 * @param date
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 20, 2014
	 */
	public static Date getLastDayOfMonth(Date date){
		Calendar cDay1 = Calendar.getInstance();  
        cDay1.setTime(date);  
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);  
        Date lastDate = cDay1.getTime();  
        lastDate.setDate(lastDay);
        return lastDate;
	}


	  

	  

	/**
	 * 返回带时间的随即
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-10
	 */
	public static String getRandomWithTime(){
		String result="";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmssSS");
		Random rand = new Random();
		int ird=rand.nextInt();
		result=sdf.format(date)+ird;
		return result;
	}
	/**
	 * 获取指定月份天数
	 * @param mon
	 * @return
	 * @author chenwei 
	 * @date Mar 10, 2014
	 */
	public static int getDayOfMonth(int mon){
		Calendar   calendar   =   Calendar.getInstance();   
		calendar.set(calendar.get(Calendar.YEAR),mon-1,1);   
		int day= calendar.getActualMaximum(Calendar.DATE);
		return day;
	}

	/**
	 * 数字转中文金额
	 * @param n
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-3-25
	 */
	public static String AmountUnitCnChange(double n){
		String fraction[] = {"角", "分"};
	    String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	    String unit[][] = {{"元", "万", "亿"},
	                 {"", "拾", "佰", "仟"}};

	    String head = n < 0? "负": "";
	    n = Math.abs(n);
	    
	    String s = "";
	    for (int i = 0; i < fraction.length; i++) {
	    	BigDecimal tmpD=new BigDecimal(n * 10 * Math.pow(10, i));
	    	tmpD=tmpD.setScale(2, BigDecimal.ROUND_HALF_UP);
	        s += (digit[(int)(Math.floor(tmpD.doubleValue()) % 10)] + fraction[i]).replaceAll("(零.)+", "");
	    }
	    if(s.length()<1){
		    s = "整";	
	    }
	    int integerPart = (int)Math.floor(n);

	    for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
	        String p ="";
	        for (int j = 0; j < unit[1].length && n > 0; j++) {
	            p = digit[integerPart%10]+unit[1][j] + p;
	            integerPart = integerPart/10;
	        }
	        s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
	    }
	    return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}

	// 过滤特殊字符
	public static String StringFilter(String str) {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
//		String regEx = "[`~!@#$%^&*()+=|{}'\":'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		String regEx = "[`~!@#$%^&*()+=|{}'\":\\[\\]<>/?~！@#￥%……&*（）——+|{}]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

    /**
     * 过滤除数字以外的字符串
     * @param str
     */
    public static String StringFilterJustNumber(String str){
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

	/**
	 * 特殊字符串转义
	 * @param str
	 * @return
	 * @author chenwei 
	 * @date 2014年5月23日
	 */
	public static String escapeStr(String str){
		if(null!=str){
			str = StringFilter(str);
		}
		return str;
	}
	/**
	 * 判断IP地址是否在IP端内
	 * @param ipSection		ip段（192.168.1.1-192.168.1.100）
	 * @param ip			验证ip地址
	 * @return
	 * @author chenwei 
	 * @date 2014年7月25日
	 */
	public static boolean ipIsValid(String ipSection, String ip) {
		if (ipSection == null)
			throw new NullPointerException("IP段不能为空！");
		if (ip == null)
			throw new NullPointerException("IP不能为空！");
		ipSection = ipSection.trim();
		ip = ip.trim();
		final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
		final String REGX_IPB = REGX_IP + "\\-" + REGX_IP;
		if (!ipSection.matches(REGX_IPB) || !ip.matches(REGX_IP))
			return false;
		int idx = ipSection.indexOf('-');
		String[] sips = ipSection.substring(0, idx).split("\\.");
		String[] sipe = ipSection.substring(idx + 1).split("\\.");
		String[] sipt = ip.split("\\.");
		long ips = 0L, ipe = 0L, ipt = 0L;
		for (int i = 0; i < 4; ++i) {
			ips = ips << 8 | Integer.parseInt(sips[i]);
			ipe = ipe << 8 | Integer.parseInt(sipe[i]);
			ipt = ipt << 8 | Integer.parseInt(sipt[i]);
		}
		if (ips > ipe) {
			long t = ips;
			ips = ipe;
			ipe = t;
		}
        return ips <= ipt && ipt <= ipe;
	}
	
	/**
	 * 将null的字符串转换为""，如果原字符串不为null，则返回原字符串
	 * @param src 原字符串
	 * @return 非null字符串
	 * @author limin 
	 * @date 2014-10-10
	 */
	public static String nullToEmpty(String src) {
		
		if(src == null) {
			return "";
		}
		return src;
	}

	/**
	 * 将空字符串转换为null，如果原字符串不为空（""），则返回原字符串
	 * @param src 原字符串
	 * @return 非空字符串
	 * @author limin 
	 * @date 2014-11-20
	 */
	public static String emptyToNull(String src) {
		
		if("".equals(src)) {
			
			return null;
		}
		return src;
	}
	/**
	 * 计算两个日期之间 间隔的天数
	 * @param smdate
	 * @param bdate
	 * @return
	 * @author chenwei 
	 * @date 2014年12月25日
	 */
	public static int daysBetween(String smdate, String bdate){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

    /**
     * 比较两个日期的大小
     * @param datestr1
     * @param datestr2
     * @return 1：datestr1>datestr2,-1：datestr1<datestr2,0：datestr1=datestr2
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    public static int compareDate(String datestr1,String datestr2){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(datestr1);
            Date dt2 = df.parse(datestr2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * map对象转换为Object对象
     * @param map
     * @param class1
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws ParseException
     * @author limin
     * @date Apr 23, 2015
     */
    public static <T> T mapToObject(HashMap<String, Object> map, Class<T> class1)
            throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, ParseException {

        Field[] fields = class1.getDeclaredFields();
        T t = null;
        if (fields.length > 0) {

            t = class1.newInstance();
        }

        boolean flag;       // 该字段是否可访问，private/protected/public
        for (Field field : fields) {

            if (map.containsKey(field.getName()) && map.get(field.getName()) != null) {

                flag = false;
                // 不可访问？
                if (!field.isAccessible()) {

                    field.setAccessible(true);
                    flag = true;
                }

                // 时间类型的转换
                if ((field.getType() == java.util.Date.class || field.getType() == java.sql.Date.class) && map.get(field.getName()).getClass() != field.getType()) {

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    field.set(t, format.parse((String) map.get(field.getName())));

                // Timestamp转换
                } else if (field.getType() == java.sql.Timestamp.class && map.get(field.getName()).getClass() != field.getType()) {

                    field.set(t, Timestamp.valueOf((String) map.get(field.getName())));

                // Long
                } else if (field.getType() == java.lang.Long.class && map.get(field.getName()).getClass() != field.getType()) {

                    field.set(t, Long.valueOf((String) map.get(field.getName())));

                // Integer
                } else if (field.getType() == java.lang.Integer.class && map.get(field.getName()).getClass() != field.getType()) {

                    field.set(t, Integer.parseInt((String) map.get(field.getName())));

                // int
                } else if ((field.getType() == int.class || field.getType() == java.lang.Integer.class) && map.get(field.getName()).getClass() != field.getType()) {

                    field.set(t, Integer.parseInt((String) map.get(field.getName())));

                // String
                } else {

                    field.set(t, map.get((String) field.getName()));
                }

                // 还原访问属性
                if (flag) {
                    field.setAccessible(false);
                }
            }
        }

        return t;
    }

    /**
     * 将byte数组的数据转换为指定编码的字符串
     * @param src 源数据（byte[]类型）
     * @param encode 文字编码（UTF-8、GB2312等）
     * @return
     * @author limin
     * @date May 20, 2015
     */
    public static String bytes2String(byte[] src, String... encode) throws Exception {

        String encode2 = "UTF-8";

        // 参数不正确
        if(encode.length > 1) {

            throw new Exception("Parameter is illegal!");
        }

        // 源数据为空
        if(src == null || src.length == 0) {

            return "";
        }

        if(encode == null || encode.length == 0) {

        } else {

            encode2 = encode[0];
        }

        return new String(src, encode2);
    }


}
