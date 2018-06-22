package com.yiyun.utils;

import java.io.Serializable;
import java.util.List;

/**
 */
public class PageUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	// 总记录数
	private int total;
	// 列表数据
	private List<?> rows;

	/**
	 * 分页
	 * 
	 * @param list
	 *            列表数据
	 * @param total
	 *            总记录数
	 */
	public PageUtil(List<?> list, int total) {
		this.rows = list;
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
