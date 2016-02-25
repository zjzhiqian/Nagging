/**
 * @(#)DatabaseContextHolder.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年2月25日 huangzhiqian 创建版本
 */
package com.hzq.common.base;
/**
 * 
 * 
 * @author huangzhiqian
 */
public class DatabaseContextHolder {  
    private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<String>();  
  
    public static void setDateBaseType(String database) {  
    	dataSourceHolder.set(database);  
    }
  
    public static String getDateBaseType() {  
        return dataSourceHolder.get();  
    }  
  
    public static void clearDatabaseType() {  
    	dataSourceHolder.remove();  
    }
}  

