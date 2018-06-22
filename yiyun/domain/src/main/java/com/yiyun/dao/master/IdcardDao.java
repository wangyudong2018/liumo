package com.yiyun.dao.master;

import com.yiyun.domain.Idcard;

import java.util.List;
import java.util.Map;

import com.yiyun.common.MyMapper;

/**
 * @title 会员身份证细腻
 * @author Xing
 * @date Fri Jun 08 16:25:09 CST 2018
 */
public interface IdcardDao extends MyMapper<Idcard> {
    int save(Idcard idcard);

    int update(Idcard idcard);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
