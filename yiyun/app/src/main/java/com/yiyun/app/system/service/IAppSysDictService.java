package com.yiyun.app.system.service;

import com.yiyun.app.system.req.SysDictReq;
import com.yiyun.app.system.resp.SysDictResp;
import com.yiyun.domain.DictDO;

import java.util.List;
import java.util.Map;

/**
 * @title 字典数据业务层
 * @description 字典数据业务层
 * @author Xingbz
 * @createDate 2017/12/18
 * @version 1.0
 */
public interface IAppSysDictService {
    SysDictResp getAllDictsByVersion(SysDictReq req);

    List<DictDO> list(Map<String, Object> map);
}
