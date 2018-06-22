package com.yiyun.web.wechat.service.impl;

import com.yiyun.constants.MemberConstants;
import com.yiyun.constants.RedisConstants;
import com.yiyun.dao.cluster.ClusterMMemberDao;
import com.yiyun.dao.master.MMemberDao;
import com.yiyun.domain.MMember;
import com.yiyun.resp.CheckSmsResp;
import com.yiyun.resp.LoginResp;
import com.yiyun.resp.MemberResp;
import com.yiyun.utils.DataUtil;
import com.yiyun.utils.TokenUtil;
import com.yiyun.web.common.config.redis.RedisTemplateDAO;
import com.yiyun.web.wechat.service.IMemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @title
 * @description
 * @author Xingbz
 * @createDate 2018/3/20
 * @version 1.0
 */
@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private RedisTemplateDAO redisTemplateDAO;

    @Autowired
    private MMemberDao mMemberDao;

    @Autowired
    private ClusterMMemberDao clusterMMemberDao;


    @Override
    public CheckSmsResp checkcode(String phone, String smsInputCode) {
        CheckSmsResp resp = new CheckSmsResp();

        if (StringUtils.isAnyBlank(phone, smsInputCode)) {
            resp.setMessage("手机号或验证码为空");
            return resp;
        }

        String smsCode = redisTemplateDAO.get(RedisConstants.CACHE_KEY_LOGIN_SMS_CODE + phone, 10);//取出验证码

        if (!Objects.equals(smsCode, smsInputCode)) {
            resp.setMessage("验证码不正确");
            return resp;
        }

        resp.setFlag(true);
        resp.setMessage("校验通过");

        //XTODO 记录该手机号 下次弹图片验证码
//        redisTemplateDAO.put(MessageConstants.ERROR_SMS_CODE + phone, phone, null, 1200L);
        return resp;
    }


    /**
     * 1.根据方式获取用户
     * 2.存在返回信息
     * 3.不存在注册后返回信息
     */
    @Override
    public LoginResp login(String phone, String type) {
        LoginResp resp = new LoginResp();
        MMember member = null;
        if (MemberConstants.LOGIN_PHONE.equals(type)) {//0 手机号登录/注册
            member = clusterMMemberDao.selectOne(new MMember(phone));
        }

        if (member == null) {//新用户 , 需要注册
            member = new MMember();
            member.setPhone(phone);
            member.setType(MemberConstants.M_TYPE_HHR);
            member.setCreateTime(System.currentTimeMillis());
            mMemberDao.save(member);
        }

        String token = phone + TokenUtil.createToken();
        redisTemplateDAO.put(RedisConstants.CACHE_KEY_MEMBER_TOKEN + phone, token, 10, 604800L);// 注册成功 , 将用户token放入缓存 ,

        resp.setMemberId(member.getId());
        resp.setToken(token);
        resp.setIsBindPhone(StringUtils.isBlank(member.getPhone()) ? "0" : "1");
        return resp;
    }

    @Override
    public boolean logout(String phone) {
        if (StringUtils.isBlank(phone) || !DataUtil.isMobile(phone)) {
            return false;
        }

        redisTemplateDAO.delete(RedisConstants.CACHE_KEY_MEMBER_TOKEN + phone, 10);
        return true;
    }

    @Override
    public MemberResp findMember(Long memberId, String phone) {
        MMember memberParam = new MMember();
        memberParam.setId(memberId);
        memberParam.setPhone(phone);
        MMember member = clusterMMemberDao.selectOne(memberParam);

        if (member == null) {
            MemberResp resp = new MemberResp();
            resp.setMemberId(memberId);
            resp.setPhone(phone);
            return resp;
        }

        return member2Resp(member);
    }

    private MemberResp member2Resp(MMember member) {
        if (member == null) {
            return null;
        }
        MemberResp resp = new MemberResp();
        resp.setMemberId(member.getId());
        resp.setPhone(member.getPhone());
        resp.setNickName(member.getNickName());
        resp.setHeadImg(member.getHeadImg());
        return resp;
    }
}
