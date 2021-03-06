package com.yiyun.web.query.service;

import com.yiyun.domain.MMember;

import java.util.List;
import java.util.Map;

/**
 * @title 会员/用户
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface MMemberService {

    MMember get(Long id);

    MMember getByPhone(String phone);

    List<MMember> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(MMember mMember);

    int update(MMember mMember);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<MMember> blackList(Map<String, Object> map);

    List<MMember> listClient(Map<String, Object> map);

    int countClient(Map<String, Object> map);

    MMember saveAndReturn(MMember member);

    MMember getByInvestCode(String column1);

    String createInvestCode();

    boolean checkInvestCode(String investCode);

}