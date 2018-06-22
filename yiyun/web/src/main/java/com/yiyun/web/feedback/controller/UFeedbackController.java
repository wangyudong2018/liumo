package com.yiyun.web.feedback.controller;

import com.yiyun.domain.UFeedbackDO;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.controller.BaseController;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.feedback.service.UFeedbackService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 用户反馈表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-12-13 16:02:47
 */

@Controller
@RequestMapping("/feedback")
public class UFeedbackController extends BaseController {
    @Autowired
    private UFeedbackService feedbackService;


    @RequiresPermissions("feedback:feedback")
    @GetMapping()
    String Feedback() {
        return "feedback/feedback";
    }

    @RequiresPermissions("feedback:feedback")
    @ResponseBody
    @GetMapping("/list")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<UFeedbackDO> feedbackList = feedbackService.list(query);
        int total = feedbackService.count(query);
        PageUtil pageUtil = new PageUtil(feedbackList, total);
        return pageUtil;
    }

    @RequiresPermissions("feedback:add")
    @GetMapping("/add")
    String add() {
        return "feedback/add";
    }

    @RequiresPermissions("feedback:edd")
    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
        UFeedbackDO feedback = feedbackService.get(id);
        model.addAttribute("feedback", feedback);
        return "feedback/edit";
    }

    /**
     * 保存
     */
    @RequiresPermissions("feedback:add")
    @ResponseBody
    @PostMapping("/save")
    public R save(UFeedbackDO feedback) {
        feedback.setCreateTime(System.currentTimeMillis());
        feedback.setOperator(this.getUsername());
        if (feedbackService.save(feedback) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @RequiresPermissions("feedback:edit")
    @ResponseBody
    @RequestMapping("/update")
    public R update(UFeedbackDO feedback) {
        feedback.setOperator(this.getUsername());
        Integer status = feedback.getStatus();
        if (status == 1) {
            feedback.setDealTime(System.currentTimeMillis());
        }
        feedbackService.update(feedback);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequiresPermissions("feedback:remove")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Long id) {
        if (feedbackService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @RequiresPermissions("feedback:remove")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") Long[] ids) {
        feedbackService.batchRemove(ids);
        return R.ok();
    }

}
