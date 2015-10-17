package com.hzq.common.entity;

import java.io.Serializable;

/**
 * @author hzq
 *ajax返回通用json
 * 2015年9月3日 下午11:27:36 
 */
public class Json implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean flag=false;
	private String msg;
	private Object obj;
	
	public Json(){
		super();
	}
	/**
	 * 如果flag为false,默认添加msg为'系统出错'
	 * @param flag
	 */
	public Json(boolean flag){
		super();
		this.flag=flag;
		if(!flag){
			this.msg="系统出错";
		}
	}
	
	/**
	 * flag=false;
	 * @param msg
	 */
	public Json(String msg) {
		super();
		this.flag=false;
		this.msg=msg;
	}
	
	public Json(boolean flag, String msg) {
		super();
		this.flag = flag;
		this.msg = msg;
	}
	

	public Json(boolean flag, String msg, Object obj) {
		super();
		this.flag = flag;
		this.msg = msg;
		this.obj = obj;
	}
	
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "Json [flag=" + flag + ", msg=" + msg + ", obj=" + obj + "]";
	}
}
