package com.yiyun.app.common.req;

/**
 * @Title APP v2.0接口分页参数
 * @Description APP v2.0接口分页参数
 * @author liliang
 * @createDate 2016年3月24日 下午4:33:19
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class PageParamReq {
	// 当前页码(默认第1页)
	private int pageNo = 1;
	// 每页显示条数(默认10)
	private int pageSize = 10;
	// sql分页查询时使用
	private int start;

	private int totalCount = 0;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		start = (pageNo >= 1) ? (pageNo - 1) * pageSize : 0;
		return start;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}