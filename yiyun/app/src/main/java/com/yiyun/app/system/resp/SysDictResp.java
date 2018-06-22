package com.yiyun.app.system.resp;

import lombok.Data;

import java.util.List;

/**
 * @title 数据字典响应信息
 * @description 
 * @author Xingbz
 * @createDate 2017/12/18
 * @version 1.0
 */ 
@Data
public class SysDictResp {
    /** 是否需要更新 */
    private String isUpdate;
    /** 最新的版本号 */
    private String dictVersion;
    /** 所有字典项 */
    private List<DictVO> dicts;
}

