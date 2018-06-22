package com.yiyun.utils;

public class ResultBean {
	
	private String error;
	
	private String message;
	
	private String DATA;

	
	public ResultBean(String error, String message, String dATA) {
		super();
		this.error = error;
		this.message = message;
		DATA = dATA;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDATA() {
		return DATA;
	}

	public void setDATA(String dATA) {
		DATA = dATA;
	}
	
	
}
