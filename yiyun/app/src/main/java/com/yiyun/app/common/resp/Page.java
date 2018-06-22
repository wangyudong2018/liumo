package com.yiyun.app.common.resp;


/**
 * @Title  APP v2.0 公共分页参数
 * @Description  APP v2.0 公共分页参数
 * @author liliang
 * @createDate 2016年3月24日 下午5:21:26
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class Page {
	private int pageNo;		//当前页码
	private int pageSize;	//每页显示条数
	private int totalCount;	//总记录数
	private int totalPage;	//总页数
	
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
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		if(pageSize == 0){
			return 0;
		}else{
			return totalCount%pageSize == 0?totalCount/pageSize:totalCount/pageSize+1;
		}
	}
}
