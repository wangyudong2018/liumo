package com.yiyun.web.query.controller;

import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.SysDictConstants;
import com.yiyun.domain.Loan;
import com.yiyun.domain.MMember;
import com.yiyun.utils.DataUtil;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.controller.BaseController;
import com.yiyun.web.base.service.DictService;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.query.service.LoanService;
import com.yiyun.web.query.service.MMemberService;
import com.yiyun.web.system.service.UBlacklistService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Xingbz
 * @title 会员/用户
 * @date Thu Mar 08 16:26:18 CST 2018
 */
@Controller
@RequestMapping("/query/member")
public class MMemberController extends BaseController {
    @Autowired
    private MMemberService mMemberService;

    @Autowired
    private UBlacklistService blacklistService;
    @Autowired
    private DictService dictService;

    @Autowired
    private LoanService loanService;

    @GetMapping()
    @RequiresPermissions("query:member:member")
    public String MMember(Integer type, Model model) {
        Map<String, Object> param = new HashedMap();
        param.put("delFlag", CommonConstants.YES);
        param.put("type", SysDictConstants.DICT_TYPE_MSOURCE);
        model.addAttribute("sourceList", dictService.list(param));
        model.addAttribute("type", type);
        return "query/member/member" + type;
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("query:member:member")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        Object searchTime = params.get("searchTime");

        if (null != searchTime && StringUtils.isNotBlank((String) searchTime)) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse((String) searchTime, dtf);
                LocalDateTime createTimeBegin = LocalDateTime.of(localDate, LocalTime.MIN);
                LocalDateTime createTimeEnd = createTimeBegin.plusDays(1L);
                params.put("createTimeBegin", createTimeBegin.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
                params.put("createTimeEnd", createTimeEnd.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            } catch (Exception e) {
                System.out.println("时间转换异常");
            }
        }
        Query query = new Query(params);
        List<MMember> mMemberList = new ArrayList<>();
        int total = 0;
        if (Objects.equals(params.get("type"), "1")) {//合伙人
            mMemberList = mMemberService.list(query);
            total = mMemberService.count(query);
        } else if (Objects.equals(params.get("type"), "2")) {//客户
            mMemberList = mMemberService.listClient(query);
            total = mMemberService.countClient(query);
        }

        mMemberList.forEach(member -> {
            member.setInvesterCode(StringUtils.isBlank(member.getInvesterCode()) ? "000000" : member.getInvesterCode());
            member.setPhone(DataUtil.starsPhone(member.getPhone()));
            member.setName(DataUtil.starsName(member.getName()));
            member.setIdcard(DataUtil.starsIdcard(member.getIdcard()));
        });

        return new PageUtil(mMemberList, total);
    }

    @GetMapping("/showBasic/{id}")
    @RequiresPermissions("query:member:member")
    public String edit(@PathVariable("id") Long id, Integer type, Model model) {
        model.addAttribute("type", type);
        if (1 == type) {//合伙人
            MMember mMember = mMemberService.get(id);
            mMember.setPhone(DataUtil.starsPhone(mMember.getPhone()));
            mMember.setName(DataUtil.starsName(mMember.getName()));
            mMember.setIdcard(DataUtil.starsIdcard(mMember.getIdcard()));
            model.addAttribute("mMember", mMember);


            /* 合伙人 , 统计受理中次数/受理失败次数/已受理次数/拒单次数/放款成功次数 */
            model.addAllAttributes(countMemberLoan(id));


        } else if (2 == type) {//客户
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            List<MMember> mMemberList = mMemberService.listClient(params);
            if (null != mMemberList && !mMemberList.isEmpty()) {
                MMember mMember = mMemberList.get(0);
                mMember.setPhone(DataUtil.starsPhone(mMember.getPhone()));
                mMember.setName(DataUtil.starsName(mMember.getName()));
                mMember.setIdcard(DataUtil.starsIdcard(mMember.getIdcard()));
                model.addAttribute("mMember", mMember);
            }
        }
        return "query/member/showBasic";
    }

    private Map<String, Integer> countMemberLoan(Long id) {
        Map<String, Integer> result = new HashMap<>();
        result.put("slzCount", 0);//受理中
        result.put("wslcgCount", 0);//未受理成功
        result.put("yslCount", 0);//已受理
        result.put("jdCount", 0);//拒单
        result.put("fkcgCount", 0);//放款成功
        if (null == id) {
            return result;
        }

        Map<String, Object> paramMap = new HashMap<>();
        List<Integer> orderStatusList = new ArrayList<>();
        paramMap.put("orderStatusList", orderStatusList);

        //受理中
        orderStatusList.add(Loan.LOAN_STATUS_SLZ);
        List<Loan> loanList = loanService.list(paramMap);
        if (null != loanList && !loanList.isEmpty()) {
            result.put("slzCount", loanList.size());
        }

        //未受理成功
        orderStatusList.clear();//清除条件
        orderStatusList.add(Loan.LOAN_STATUS_SLSB);
        loanList = loanService.list(paramMap);
        if (null != loanList && !loanList.isEmpty()) {
            result.put("wslcgCount", loanList.size());
        }

        //已受理
        orderStatusList.clear();//清除条件
        orderStatusList.add(Loan.LOAN_STATUS_SLCG);
        loanList = loanService.list(paramMap);
        if (null != loanList && !loanList.isEmpty()) {
            result.put("yslCount", loanList.size());
        }

        //拒单
        orderStatusList.clear();//清除条件
        orderStatusList.add(Loan.LOAN_STATUS_SLSB);
        loanList = loanService.list(paramMap);
        if (null != loanList && !loanList.isEmpty()) {
            result.put("jdCount", loanList.size());
        }

        //放款成功
        orderStatusList.clear();//清除条件
        orderStatusList.add(Loan.LOAN_STATUS_FKCG);
        loanList = loanService.list(paramMap);
        if (null != loanList && !loanList.isEmpty()) {
            result.put("fkcgCount", loanList.size());
        }

        return result;
    }
}
