package com.yiyun.app.common.resp;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * @Title APP V2.0 接口分页数据返回格式
 * @Description APP V2.0 接口分页数据返回格式
 * @author liliang
 * @createDate 2016年3月24日 下午5:18:54
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class CommonPageResp<T> extends JSONObject{
	private static final long serialVersionUID = 1L;
	private List<T> dataList;
	private Page page;

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
		this.put("dataList", dataList);
	}

	public void setPage(Page page) {
		this.page = page;
		this.put("page", page);
	}

	public List<T> getDataList() {
		return dataList;
	}
	
	public Page getPage(){
		return page;
	}
}
