package com.yiyun.app.system.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class SysHelpDocResp implements Serializable{
    //类别
    private String helpDocType;
    //图标
    private String icon;
    //内容
    private List<HelpDoc> content;


}
