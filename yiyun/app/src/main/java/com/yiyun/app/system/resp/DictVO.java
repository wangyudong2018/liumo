package com.yiyun.app.system.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @title 数据字典单项内容
 * @description 
 * @author Xingbz
 * @createDate 2017/12/19
 * @version 1.0
 */ 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictVO{
    private String code;

    private List<String> data;
}