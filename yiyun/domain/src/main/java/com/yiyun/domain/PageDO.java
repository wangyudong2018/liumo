package com.yiyun.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PageDO<T> {

	private int offset;
	private int limit;
	private int total;
	private Map<String, Object> params;
	private String param;
	private List<T> rows;
}
