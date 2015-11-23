/**
 * @(#)Constant.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月2日 huangzhiqian 创建版本
 */
package com.hzq.system.constant;
/**
 * 
 * 记录一些系统参数 通常不会改变
 * @author huangzhiqian
 */
public class Constant {
	/**
	 * 密码最大长度
	 */
	public static  final int PSW_MAX_LENGTH=10;
	/**
	 * 密码最短长度
	 */
	public static  final int PSW_MIN_LENGTH=6;
	/**
	 * 加密方式   (修改同时 shiro配置文件也要更改)
	 */
	public static  final String ENCRYPT_TYPE="SHA-1";
	/**
	 * 加密迭代次数  (修改同时 shiro配置文件也要更改)
	 */
	public static final int ENCRYPT_TIMES=1024;
	/**
	 * 盐长度的一半
	 */
	public static final int SALT_LENGTH_HALF=16;
	
	/**
	 * 密码错误尝试的最大次数
	 */
	public static final int MAX_TIME_PSW_ERROR=5;
	/**
	 * session踢出的Key
	 */
	public  static final String SHIRO_KICK_KEY = "shiro_kicked";
	
	
	
	//********************索引相关********************//
	
	/**
	 * TianYa帖子索引储存路径
	 */
	public static final String Index_TianYaPost_Path="C:\\luceneIndex\\TianYa";
	
	/**
	 * TianYa帖子索引多目录的数量
	 */
	public static final int Index_TianYaPost_MultiPathNum=5;
	
	/**
	 * TianYa帖子索引储存多目录路径
	 */
    public static final String Index_TianYaPost_MultiPath="C:\\luceneIndex\\TianYaMulti\\index";
	
    /**
     * TianYa检索 提示内容索引存储路径
     */
    public static final String Index_TianYaSuggest_Path="C:\\luceneIndex\\TianYaSuggest";
    
    
    
    /**
     * 淘宝帖子索引储存路径
     */
    public static final String Index_TaoBaoPost_Path="C:\\luceneIndex\\TaoBao";
    
    /**
     * TaoBao帖子索引储存多目录路径
     */
    public static final String Index_TaoBaoPost_MultiPath="C:\\luceneIndex\\TaoBaoMulty\\index";
    
    /**
     * TaoBao帖子索引多目录的数量
     */
    public static final int Index_TaoBaoPost_MultiPathNum=5;
    
    /**
     * TaoBao检索 提示内容索引存储路径
     */
    public static final String Index_TaoBaoSuggest_Path="C:\\luceneIndex\\TaoBaoSuggest";
    
	
}

