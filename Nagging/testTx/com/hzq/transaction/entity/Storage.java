package com.hzq.transaction.entity;

import java.io.Serializable;

/**
 * @author hzq
 *
 *         2015年8月22日 下午5:44:17
 */
public class Storage implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int leftnum;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLeftnum() {
		return leftnum;
	}

	public void setLeftnum(int leftnum) {
		this.leftnum = leftnum;
	}

	@Override
	public String toString() {
		return "storage [id=" + id + ", leftnum=" + leftnum + "]";
	}
}
