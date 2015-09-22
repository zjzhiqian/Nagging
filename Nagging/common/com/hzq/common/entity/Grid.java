package com.hzq.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author hzq
 *
 * 2015年9月5日 上午8:24:56 
 */
public class Grid<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<T> rows;
	private int total;
	private T footer;
	
	
	
	
	public Grid() {
		super();
	}
	public Grid(List<T> rows, int total) {
		super();
		this.rows = rows;
		this.total = total;
	}
	public Grid(List<T> rows, int total, T footer) {
		super();
		this.rows = rows;
		this.total = total;
		this.footer = footer;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public T getFooter() {
		return footer;
	}
	public void setFooter(T footer) {
		this.footer = footer;
	}
	@Override
	public String toString() {
		return "DataGridResult [rows=" + rows + ", total=" + total
				+ ", footer=" + footer + "]";
	}
	
}
