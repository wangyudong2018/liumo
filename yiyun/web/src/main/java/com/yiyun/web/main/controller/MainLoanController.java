package com.yiyun.web.main.controller;

import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.MemberConstants;
import com.yiyun.constants.RedisConstants;
import com.yiyun.domain.Loan;
import com.yiyun.domain.MMember;
import com.yiyun.resp.CheckSmsResp;
import com.yiyun.utils.DataUtil;
import com.yiyun.web.base.controller.BaseController;
import com.yiyun.web.common.config.redis.RedisTemplateDAO;
import com.yiyun.web.order.service.OReserveService;
import com.yiyun.web.query.service.LoanService;
import com.yiyun.web.query.service.MMemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @title 主页订单controller
 * @author Xingbz
 * @createDate 2018-6-9
 */
@RestController
@RequestMapping("/mLoan")
public class MainLoanController extends BaseController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private MMemberService memberService;

    @Autowired
    private OReserveService reserveService;

    @Autowired
    private RedisTemplateDAO redisTemplateDAO;


    @PostMapping(value = "/commitLoan", produces = "application/json;charset=utf-8")
    public String commitReserve(Loan loan, String smsCode) {
        if (StringUtils.isBlank(smsCode)) {
            return returnFail("验证码不能为空");
        }

        if (null == loan || StringUtils.isBlank(loan.getLoanMemberPhone())) {
            return returnFail("信息不正确");
        }
        CheckSmsResp resp = reserveService.checkcode(loan.getLoanMemberPhone(), smsCode);
        if (!resp.isFlag()) {//验证码校验未通过
            return returnFail(resp.getMessage());
        }

        long current = System.currentTimeMillis();//当前时间毫秒数
        long timeLow = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long timeUp = timeLow + 24 * 60 * 60 * 1000;//明天零点零分零秒的毫秒数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("loanMemberPhone", loan.getLoanMemberPhone());
        paramMap.put("timeLow", timeLow);
        paramMap.put("timeUp", timeUp);
        paramMap.put("orderStatus", Loan.LOAN_STATUS_SLZ);//受理中
        List<Loan> list = loanService.list(paramMap);
        if (list != null && !list.isEmpty()) {
            return returnFail("您今日已提交过申请 , 请等待专员联系");
        }
        saveLoan(loan);
        return returnSucess("提交成功 , 请等待专员联系您");
    }

    private void saveLoan(Loan loan) {
    /*
        step1 . 查询申请手机号是否存在用户 , 无则创建
        step2 . 录入初始订单信息 (状态未受理中 , 步骤为新建 , column1为邀请码)
        step3 . 若有邀请码 , 根据邀请码查询邀请人 , 更新成功邀请次数
     */
        MMember dbMember = memberService.getByPhone(loan.getLoanMemberPhone());
        if (null == dbMember) { //首次申请 , 没有用户记录
            MMember member = new MMember();
            member.setType(MemberConstants.M_TYPE_KH);//用户类型 , 客户
            member.setStatus(MemberConstants.RESERVE_STATUS_YGQ);//用户状态正常
            member.setPhone(loan.getLoanMemberPhone());//手机号
            member.setSource(CommonConstants.SOURCE_WEB);//来源 , 网站
            member.setCreateTime(Instant.now().toEpochMilli());//创建时间

            member.setMemberInvestCode("000000");//该会员邀请码
            member.setInvestCount(0);//该会员成功邀请次数
            dbMember = memberService.saveAndReturn(member);//保存并返回
        }
        Long loanMemberId = dbMember.getId();//借款人ID
        loan.setLoanMemberId(loanMemberId);
        loan.setOrderSn(loanService.getOrderSn());//订单号
        loan.setOrderStep(Loan.LOAN_STEP_YUNYING);//步骤 - 新建
        loan.setOrderStatus(Loan.LOAN_STATUS_SLZ);//状态 - 受理中
        loan.setSource(CommonConstants.SOURCE_WEB);//订单来源
        loan.setCreateTime(Instant.now().toEpochMilli());
        loanService.save(loan);

        //验证通过保存预约订单 , 并删除缓存中的验证码(即如果再次操作需要重新获取验证码 , 防止多次提交)
        redisTemplateDAO.delete(RedisConstants.CACHE_KEY_SMS_CODE + loan.getLoanMemberPhone(), 10);

        if (!"000000".equals(loan.getColumn1())) {//有效邀请码 , 查询邀请人信息并更新邀请次数
            MMember investMember = memberService.getByInvestCode(loan.getColumn1());
            if (null != investMember) {
                investMember.setInvestLoanCount(null == investMember.getInvestLoanCount() ? 1 : investMember.getInvestLoanCount() + 1);
                memberService.update(investMember);
            }
        }
    }

    @PostMapping(value = "/commitLoanNoCode", produces = "application/json;charset=utf-8")
    public String commitLoanNoCode(Loan loan) {

        if (loan == null || !DataUtil.isMobile(loan.getLoanMemberPhone())) {
            return returnFail("手机号码错误");
        }

        long current = System.currentTimeMillis();//当前时间毫秒数
        long timeLow = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long timeUp = timeLow + 24 * 60 * 60 * 1000;//明天零点零分零秒的毫秒数
        Map<String, Object> raram = new HashMap<>();
        raram.put("loanMemberPhone", loan.getLoanMemberPhone());
        raram.put("timeLow", timeLow);
        raram.put("timeUp", timeUp);
        raram.put("orderStatus", Loan.LOAN_STATUS_SLZ);//受理中
        List<Loan> list = loanService.list(raram);
        if (list != null && !list.isEmpty()) {
            return returnFail("您今日已提交申请 , 请等待专员联系");
        }
        saveLoan(loan);
        return returnSucess("提交成功 , 请等待专员联系您");
    }

}