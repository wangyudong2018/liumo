package com.yiyun.web.system.req;



import javax.persistence.Id;
import java.io.Serializable;


/**
 * 定制消息推送请求参数
 * 
 * @author yangliu
 * @email 1992lcg@163.com
 * @date Wed Dec 20 10:37:25 CST 2017
 */

public class SysMessagePushReq implements Serializable {
	private static final long serialVersionUID = 1L;

	//消息id
	private Long id;
	//用户性别
	private String sex;
	//渠道类型
	private String channelType;
	//注册开始时间
	private Long startTime;
	//注册结束时间
	private Long endTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
