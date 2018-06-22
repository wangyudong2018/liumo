package com.yiyun.web.query.service.impl;

import com.yiyun.dao.cluster.ClusterMMemberDao;
import com.yiyun.dao.master.MMemberDao;
import com.yiyun.domain.MMember;
import com.yiyun.domain.UserDO;
import com.yiyun.utils.DateUtil;
import com.yiyun.utils.RandomUtil;
import com.yiyun.web.query.service.MMemberService;
import com.yiyun.web.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MMemberServiceImpl implements MMemberService {
    @Autowired
    private MMemberDao mMemberDao;

    @Autowired
    private ClusterMMemberDao clusterMMemberDao;

    @Autowired
    private UserService userService;

    @Override
    public MMember get(Long id) {
        return clusterMMemberDao.get(id);
    }

    @Override
    public MMember getByPhone(String phone) {
        Map<String, Object> param = new HashMap<>();
        param.put("phone", phone);
        List<MMember> memberList = clusterMMemberDao.list(param);
        if (null != memberList && !memberList.isEmpty()) {
            return memberList.get(0);
        }
        return null;
    }

    @Override
    public List<MMember> list(Map<String, Object> map) {
        return clusterMMemberDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return clusterMMemberDao.count(map);
    }

    @Override
    public int save(MMember mMember) {
        return mMemberDao.save(mMember);
    }

    @Override
    public int update(MMember mMember) {
        return mMemberDao.update(mMember);
    }

    @Override
    public int remove(Long id) {
        return mMemberDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return mMemberDao.batchRemove(ids);
    }

    @Override
    public List<MMember> blackList(Map<String, Object> map) {
        return clusterMMemberDao.blackList(map);
    }

    @Override
    public List<MMember> listClient(Map<String, Object> map) {
        List<MMember> memberList = clusterMMemberDao.listClient(map);
        try {
            if (null != memberList && !memberList.isEmpty()) {
                memberList.forEach(member -> {
                    if (StringUtils.isNotBlank(member.getIdcard())) {
                        if (null == member.getGender()) {
                            member.setGender(DateUtil.getGenderByIdCard(member.getIdcard()));
                        }
                        if (null == member.getAge() || 0 == member.getAge()) {
                            member.setAge(DateUtil.ageFromBirthDay(DateUtil.birthdayFromIdCard(member.getIdcard())));
                        }
                    }
                });
            }
        } catch (Exception e) {
            System.out.println(">>> 根据身份证转换信息异常");
        }

        return memberList;
    }

    @Override
    public int countClient(Map<String, Object> map) {
        return clusterMMemberDao.countClient(map);
    }

    @Override
    public MMember saveAndReturn(MMember member) {
        int result = mMemberDao.save(member);
        if (result > 0) {
            MMember dbMember = getByPhone(member.getPhone());
            if (null != dbMember) {
                return dbMember;
            }
        }
        return member;
    }

    @Override
    public MMember getByInvestCode(String column1) {
        Map<String, Object> param = new HashMap<>();
        param.put("memberInvestCode", column1);
        List<MMember> memberList = clusterMMemberDao.list(param);
        if (null != memberList && !memberList.isEmpty()) {
            return memberList.get(0);
        }
        return null;
    }

    //创建不重复的6位随机数字邀请码
    @Override
    public String createInvestCode() {
        while (true) {
            String investCode = RandomUtil.randomInvestCode();
            UserDO userDO = userService.getByInvestCode(investCode);
            MMember member = getByInvestCode(investCode);
            if (null == userDO && null == member) {
                return investCode;
            }
        }
    }

    @Override
    public boolean checkInvestCode(String investCode) {
        UserDO userDO = userService.getByInvestCode(investCode);
        MMember member = getByInvestCode(investCode);
        return null == userDO && null == member;
    }
}
