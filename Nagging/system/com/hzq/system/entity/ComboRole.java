package com.hzq.system.entity;

import java.io.Serializable;

/**
 * @author hzq
 *
 * 2015年9月14日 上午12:06:01 
 */
public class ComboRole implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "ComboRole [id=" + id + ", text=" + text + "]";
	}
	
	
}
