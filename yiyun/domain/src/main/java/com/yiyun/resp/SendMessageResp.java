package com.yiyun.resp;

/**
 * @Title 发送短信返回结果
 * @Description
 * @author XieLinGe
 * @createDate 2017年8月15日
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class SendMessageResp {
	// 是否成功
	private boolean flag;
	// 通道名称
	private String alleywayName;
	// 返回结果
	private String returnResult;

	public boolean isFlag() {
		return flag;
	}

	public String getAlleywayName() {
		return alleywayName;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void setAlleywayName(String alleywayName) {
		this.alleywayName = alleywayName;
	}

	public String getReturnResult() {
		return returnResult;
	}

	public void setReturnResult(String returnResult) {
		this.returnResult = returnResult;
	}

	@Override
	public String toString() {
		return "SendMessageResp [flag=" + flag + ", alleywayName=" + alleywayName + ", returnResult=" + returnResult
				+ "]";
	}

}
