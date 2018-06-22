package com.yiyun.app.mapp.service.impl;

import com.yiyun.app.common.config.redis.RedisTemplateDAO;
import com.yiyun.app.mapp.service.MMemberService;
import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.MemberConstants;
import com.yiyun.constants.RedisConstants;
import com.yiyun.dao.cluster.ClusterMMemberDao;
import com.yiyun.dao.cluster.ClusterUserDao;
import com.yiyun.dao.master.MMemberDao;
import com.yiyun.domain.MMember;
import com.yiyun.domain.UserDO;
import com.yiyun.resp.CheckSmsResp;
import com.yiyun.resp.LoginResp;
import com.yiyun.utils.DataUtil;
import com.yiyun.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @title
 * @author Xingbz
 * @createDate 2018-6-10
 */
@Service
public class MMemberServiceImpl implements MMemberService {

    @Autowired
    private MMemberDao memberDao;

    @Autowired
    private ClusterMMemberDao clusterMMemberDao;

    @Autowired
    private RedisTemplateDAO redisTemplateDAO;

    @Autowired
    private ClusterUserDao clusterUserDao;

    @Override
    public CheckSmsResp checkcode(String phone, String smsInputCode, HttpSession session) {
        CheckSmsResp resp = new CheckSmsResp();

        if (StringUtils.isAnyBlank(phone, smsInputCode)) {
            resp.setMessage("手机号或验证码为空");
            return resp;
        }

        String smsCode = redisTemplateDAO.get(RedisConstants.CACHE_KEY_LOGIN_SMS_CODE + phone, 10);//取出验证码
//        String smsCode = (String) session.getAttribute(RedisConstants.CACHE_KEY_LOGIN_SMS_CODE + phone);
        if (!Objects.equals(smsCode, smsInputCode)) {
            resp.setMessage("验证码不正确");
            return resp;
        }

        resp.setFlag(true);
        resp.setMessage("校验通过");
        return resp;
    }

    @Override
    public LoginResp login(String phone, String investCode, HttpSession session) {
        LoginResp resp = new LoginResp();
        String memberInvestCode = "000000";//当前用户自己的邀请码 , 一般都为"000000" , 内部人士才会有值

        MMember newMember = clusterMMemberDao.getByPhone(phone);
        if (newMember == null) { //新用户 , 需要注册
            Map<String, Object> map = new HashMap<>();
            map.put("mobile", phone);
            List<UserDO> userDOList = clusterUserDao.list(map);
            if (null != userDOList && !userDOList.isEmpty()) { //内部用户
                UserDO userDO = userDOList.get(0);
                if (null != userDO && !"000000".equals(userDO.getInvestCode())) {//填写的内部人员有效邀请码
                    memberInvestCode = userDO.getInvestCode();
                }
            }

            newMember = new MMember();
            newMember.setNickName(DataUtil.starsPhone(phone));
            newMember.setPhone(phone);
            newMember.setType(MemberConstants.M_TYPE_HHR);
            newMember.setStatus(CommonConstants.YES);
            newMember.setSource(CommonConstants.SOURCE_MAPP);
            newMember.setCreateTime(System.currentTimeMillis());

            newMember.setMemberInvestCode(memberInvestCode);//该会员自己的邀请码
            newMember.setInvestCount(0);//该会员成功邀请次数
            newMember.setInvestLoanCount(0);//该会员成功邀请下单的次数

            if (!"000000".equals(investCode)) {//填写了有效验证码 , 说明有人邀请他 ,查出来然后更新
                newMember.setInvesterCode(investCode);//主邀请人邀请码

                Map<String, Object> param = new HashMap<>();
                param.put("memberInvestCode", investCode);
                List<MMember> memberList = clusterMMemberDao.list(param);
                if (null != memberList && !memberList.isEmpty()) {
                    MMember investMember = memberList.get(0);//主邀请人
                    if (null != investMember) {
                        newMember.setInvesterMemberId(investMember.getId());//新人设置主邀请人ID

                        investMember.setInvestCount(null == investMember.getInvestCount() ? 1 : investMember.getInvestLoanCount() + 1);//主邀请人成功邀请次数+1
                        memberDao.update(investMember);
                    }
                }
            }
            memberDao.save(newMember);
            newMember = clusterMMemberDao.getByPhone(phone);//保存成功反查 , 用于返回前端ID
        }

        session.setAttribute(CommonConstants.SKEY_CURRENT_MEMBER_ID, newMember.getId());
        session.setAttribute(CommonConstants.SKEY_CURRENT_MEMBER_PHONE, phone);
        session.setAttribute(CommonConstants.SKEY_CURRENT_MEMBER, newMember);

        String token = phone + TokenUtil.createToken();
        redisTemplateDAO.put(RedisConstants.CACHE_KEY_MEMBER_TOKEN + phone, token, 10, 604800L);// 注册成功 , 将用户token放入缓存 ,
//        session.setAttribute(RedisConstants.CACHE_KEY_MEMBER_TOKEN + phone , token);
        resp.setMemberId(newMember.getId());
        resp.setToken(token);
        resp.setIsBindPhone(StringUtils.isBlank(newMember.getPhone()) ? "0" : "1");
        return resp;
    }
}