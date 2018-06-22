package com.yiyun.app.mongodb.entity;


import org.springframework.data.annotation.Id;

public class Mobile {

	@Id
	public String id;
	public Long memberId;
	public Integer type;
	public String jsonData;

	public Mobile() {
	}


	public Mobile(Long memberId, Integer type, String jsonData) {
		super();
		this.memberId = memberId;
		this.type = type;
		this.jsonData = jsonData;
	}


	@Override
	public String toString() {
		return "Mobile [id=" + id + ", memberId=" + memberId + ", type=" + type + ", jsonData=" + jsonData + "]";
	}

}