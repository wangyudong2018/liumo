package com.yiyun.web.system.controller;

import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.MemberConstants;
import com.yiyun.domain.MMember;
import com.yiyun.domain.UBlacklistDO;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.controller.BaseController;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.query.service.MMemberService;
import com.yiyun.web.system.service.UBlacklistService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author liyinq
 * @title 黑名单列表
 * @date Tue Jan 09 14:13:53 CST 2018
 */
@Controller
@RequestMapping("/system/blacklist")
public class UBlacklistController extends BaseController {

    @Autowired
    private UBlacklistService blacklistService;

    @Autowired
    private MMemberService mMemberService;

    @GetMapping()
    @RequiresPermissions("system:blacklist:blacklist")
    String Blacklist() {
        return "system/blacklist/blacklist";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("system:blacklist:blacklist")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<UBlacklistDO> blacklistList = blacklistService.list(query);
        int total = blacklistService.count(query);
        PageUtil pageUtil = new PageUtil(blacklistList, total);
        return pageUtil;
    }

    @GetMapping("/add")
    @RequiresPermissions("system:blacklist:add")
    String add() {
        return "system/blacklist/add";
    }

    @PostMapping("/edit/{id}")
    @RequiresPermissions("system:blacklist:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        /*UBlacklistDO blacklist = blacklistService.get(id);
        model.addAttribute("blacklist", blacklist);
	    return "system/blacklist/edit";*/
        Map<String, Object> result = new HashMap<String, Object>();
//		UUserDetailDO ud = new UUserDetailDO();
//		ud.setId(id);
//		result = usersService.getUserList(ud);
        //model.addAttribute("users", users);
        model.addAttribute("users", result.get("users"));
        model.addAttribute("imgs0", result.get("imgs0"));
        model.addAttribute("imgs1", result.get("imgs1"));
        return "system/users/userDetail";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("system:blacklist:add")
    public R save(UBlacklistDO blacklist) {
        if (blacklistService.save(blacklist) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("system:blacklist:edit")
    public R update(UBlacklistDO blacklist) {
        blacklistService.update(blacklist);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("system:blacklist:remove")
    public R remove(Long id) {
        if (blacklistService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("system:blacklist:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        blacklistService.batchRemove(ids);
        return R.ok();
    }

    /**
     * 移除黑名单
     */
    @PostMapping("/removeBlack")
    @ResponseBody
    @RequiresPermissions("system:blacklist:removeBlack")
    public R removeBlack(Long id) {
        try {
        /*
        用户状态设置为正常
		 */
            MMember mMember = mMemberService.get(id);
            mMember.setStatus(MemberConstants.RESERVE_STATUS_YGQ);
            mMemberService.update(mMember);
            //黑名单表更新状态为无效

            UBlacklistDO blackDo = blacklistService.get(id);
            blackDo.setBlackState(Long.valueOf(CommonConstants.NO));
            blackDo.setOpeator(getUserId().toString());
            blacklistService.update(blackDo);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();

        }
        return R.ok();
    }

}
