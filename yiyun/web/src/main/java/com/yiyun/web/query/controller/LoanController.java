package com.yiyun.web.query.controller;

import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.MemberConstants;
import com.yiyun.constants.SysDictConstants;
import com.yiyun.domain.DictDO;
import com.yiyun.domain.Loan;
import com.yiyun.domain.UserDO;
import com.yiyun.utils.DataUtil;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.service.DictService;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.ShiroUtils;
import com.yiyun.web.query.service.LoanService;
import com.yiyun.web.query.service.MMemberService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @title 订单
 * @author Xing
 * @date Fri Jun 08 20:54:55 CST 2018
 */
@Controller
@RequestMapping("/query/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @Autowired
    private DictService dictService;

    @Autowired
    private MMemberService memberService;

    @GetMapping()
    @RequiresPermissions("query:loan:loan")
    public String Loan(Long memberId, Integer mtype, Model model) {

        model.addAttribute("memberId", memberId);
        model.addAttribute("mtype", mtype);

        Map<String, Object> param = new HashMap<>();
        param.put("delFlag", CommonConstants.YES);
        param.put("type", SysDictConstants.NEW_TYPE_PRODUCT_TYPE);
        model.addAttribute("typeList", dictService.list(param));

        List<DictDO> statusList = new ArrayList<>();
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_SLZ), Loan.LOAN_STATUS_SLZ + ""));
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_SLCG), Loan.LOAN_STATUS_SLCG + ""));
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_DBCZL), Loan.LOAN_STATUS_DBCZL + ""));
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_ZLBCZ), Loan.LOAN_STATUS_ZLBCZ + ""));
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_FWJXZ), Loan.LOAN_STATUS_FWJXZ + ""));
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_FKCG), Loan.LOAN_STATUS_FKCG + ""));
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_SLSB), Loan.LOAN_STATUS_SLSB + ""));
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_FKSB), Loan.LOAN_STATUS_FKSB + ""));

        model.addAttribute("statusList", statusList);
        return "query/loan/loan";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("query:loan:loan")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isNotBlank(params.get("mtype") + "") && !Objects.equals("null", params.get("mtype") + "") && StringUtils.isNotBlank(params.get("memberId") + "")) {
            Integer mtype = Integer.valueOf(params.get("mtype") + "");
            if (MemberConstants.M_TYPE_KH.equals(mtype)) {//如果是客户
                params.put("loanMemberId", params.get("memberId"));
            } else if (MemberConstants.M_TYPE_HHR.equals(mtype)) {//如果是合伙人 , 根据合伙人id查询
                params.put("partner", params.get("memberId"));
            }
        }

        /* 当前查询角色 */
        UserDO user = ShiroUtils.getUser();
        if (null != user && StringUtils.isNotBlank(user.getCity())) {//只看同属城市的
            params.put("city", user.getCity());
        }

        //查询列表数据
        Query query = new Query(params);
        List<Loan> loanList = loanService.list(query);
        if (null != loanList && !loanList.isEmpty()) {
            loanList.forEach(entity -> {
                entity.setColumn1(StringUtils.isBlank(entity.getColumn1()) ? "000000" : entity.getColumn1());
                entity.setIdcard(DataUtil.starsIdcard(entity.getIdcard()));
                entity.setLoanMemberPhone(DataUtil.starsPhone(entity.getLoanMemberPhone()));
            });
        }
        int total = loanService.count(query);
        return new PageUtil(loanList, total);
    }
}