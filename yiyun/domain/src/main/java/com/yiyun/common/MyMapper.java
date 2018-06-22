package com.yiyun.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @title 通用mapper , tkmapper只能指定一个通用mapper , 读写分离在service中控制
 * @description
 * @author Xingbz
 * @createDate 2017/12/20
 * @version 1.0
 */
public interface MyMapper<T> extends Mapper<T> , MySqlMapper<T> {
}
