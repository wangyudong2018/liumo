package com.yiyun.app.user.resp;

import java.io.Serializable;

/**
 * @Title 上传身份信息图片返回参数
 * @Description
 * @author XieLinGe
 * @createDate 2017年7月28日
 * @modifier
 * @modifyDate
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ImageUploadResp implements Serializable{

	private String imageName;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
