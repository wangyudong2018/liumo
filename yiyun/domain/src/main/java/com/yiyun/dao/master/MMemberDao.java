package com.yiyun.dao.master;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.MMember;

/**
 * @title 会员/用户
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface MMemberDao extends MyMapper<MMember> {
    int save(MMember mMember);

    int update(MMember mMember);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
