package com.yiyun.web.manager.controller;

import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.SysDictConstants;
import com.yiyun.domain.Newfeedback;
import com.yiyun.domain.UserDO;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.service.DictService;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.common.utils.ShiroUtils;
import com.yiyun.web.manager.service.NewfeedbackService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title 用户反馈表
 * @author Xing
 * @date Fri Jun 08 13:13:45 CST 2018
 */
@Controller
@RequestMapping("/manager/newfeedback")
public class NewfeedbackController {
    @Autowired
    private NewfeedbackService newfeedbackService;

    @Autowired
    private DictService dictService;

    @GetMapping()
    @RequiresPermissions("manager:newfeedback:newfeedback")
    public String Newfeedback(Model model) {
        Map<String, Object> param = new HashedMap();
        param.put("delFlag", CommonConstants.YES);
        param.put("type", SysDictConstants.DICT_TYPE_MSOURCE);
        model.addAttribute("sourceList", dictService.list(param));
        return "manager/newfeedback/newfeedback";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("manager:newfeedback:newfeedback")
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

        //查询列表数据
        Query query = new Query(params);
        List<Newfeedback> newfeedbackList = newfeedbackService.list(query);
        int total = newfeedbackService.count(query);
        return new PageUtil(newfeedbackList, total);
    }

//    @GetMapping("/add")
//    @RequiresPermissions("manager:newfeedback:add")
//    public String add() {
//        return "manager/newfeedback/add";
//    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("manager:newfeedback:edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Newfeedback newfeedback = newfeedbackService.get(id);
        model.addAttribute("newfeedback", newfeedback);
        return "manager/newfeedback/edit";
    }

    @GetMapping("/batchEdit")
    @RequiresPermissions("manager:newfeedback:edit")
    public String batchEdit(String ids, Model model) {
        if (!StringUtil.isBlank(ids)) {
            String[] idArr = ids.split(",");
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("ids", idArr);
            paramMap.put("status", CommonConstants.NO);
            List<Newfeedback> newfeedbackList = newfeedbackService.list(paramMap);
            model.addAttribute("newfeedbackList", newfeedbackList);
        }

        return "manager/newfeedback/batchEdit";
    }

//    /**
//     * 保存
//     */
//    @ResponseBody
//    @PostMapping("/save")
//    @RequiresPermissions("manager:newfeedback:add")
//    public R save(Newfeedback newfeedback) {
//        if (newfeedbackService.save(newfeedback) > 0) {
//            return R.ok();
//        }
//        return R.error();
//    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("manager:newfeedback:edit")
    public R update(Newfeedback newfeedback) {
        if (null != newfeedback) {
            newfeedback.setStatus(CommonConstants.YES);
            newfeedback.setDealTime(Instant.now().toEpochMilli());
            UserDO loginUser = ShiroUtils.getUser();
            if (null != loginUser) {
                newfeedback.setOperator(loginUser.getName());
            }
        }
        newfeedbackService.update(newfeedback);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("manager:newfeedback:remove")
    public R remove(Long id) {
        if (newfeedbackService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

//    /**
//     * 删除
//     */
//    @PostMapping("/batchRemove")
//    @ResponseBody
//    @RequiresPermissions("manager:newfeedback:batchRemove")
//    public R remove(@RequestParam("ids[]") Long[] ids) {
//        newfeedbackService.batchRemove(ids);
//        return R.ok();
//    }
}
