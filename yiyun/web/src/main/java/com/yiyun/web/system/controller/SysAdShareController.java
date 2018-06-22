package com.yiyun.web.system.controller;

import com.yiyun.constants.CommonConstants;
import com.yiyun.domain.DictDO;
import com.yiyun.domain.SysAdShareDO;
import com.yiyun.utils.DateUtil;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.controller.BaseController;
import com.yiyun.web.base.service.DictService;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.system.service.SysAdShareService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 广告分享表
 *
 * @author YaN
 * @email 1992lcg@163.com
 * @date Tue Dec 19 13:50:41 CST 2017
 */

@Controller
@RequestMapping("/system/sysAdShare")
public class SysAdShareController extends BaseController {

    private static final Logger logger = LogManager.getLogger(SysAdShareController.class.getName());

    @Autowired
    private SysAdShareService sysAdShareService;

    @Autowired
    private DictService dictService;

    @GetMapping()
    @RequiresPermissions("system:sysAdShare:sysAdShare")
    String SysAdShare() {
        return "system/sysAdShare/sysAdShare";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("system:sysAdShare:sysAdShare")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<SysAdShareDO> sysAdShareList = sysAdShareService.list(query);
        int total = sysAdShareService.count(query);
        PageUtil pageUtils = new PageUtil(sysAdShareList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("system:sysAdShare:add")
    String add(Model model) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "APP_AD");
        List<DictDO> list = dictService.list(map);
        map.put("type", "APP_AD_OPEN_STYLE");
        List<DictDO> list2 = dictService.list(map);
        map.put("type", "SYSTEM_TYPE");
        List<DictDO> list3 = dictService.list(map);
        model.addAttribute("category", list);
        model.addAttribute("openType", list2);
        model.addAttribute("sysType", list3);
        return "system/sysAdShare/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:sysAdShare:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "APP_AD");
        List<DictDO> list = dictService.list(map);
        map.put("type", "APP_AD_OPEN_STYLE");
        List<DictDO> list1 = dictService.list(map);
        map.put("type", "SYSTEM_TYPE");
        List<DictDO> list2 = dictService.list(map);
        SysAdShareDO sysAdShare = sysAdShareService.get(id);
        model.addAttribute("sysAdShare", sysAdShare);
        model.addAttribute("category", list);
        model.addAttribute("openType", list1);
        model.addAttribute("sysType", list2);
        return "system/sysAdShare/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("system:sysAdShare:add")
    public R save(String sTime, String eTime, SysAdShareDO sysAdShare) throws ParseException {
        if (StringUtils.isBlank(sysAdShare.getWxImg())) {
            sysAdShare.setWxImg(sysAdShare.getCommonImg());
        }
        if (StringUtils.isBlank(sysAdShare.getAndroidImg())) {
            sysAdShare.setAndroidImg(sysAdShare.getCommonImg());
        }
        if (StringUtils.isBlank(sysAdShare.getIosImg())) {
            sysAdShare.setIosImg(sysAdShare.getCommonImg());
        }
        sysAdShare.setStatus(CommonConstants.YES);
        if (StringUtils.isNotBlank(sTime)) {
            sysAdShare.setStartTime(DateUtil.stringToLong(sTime, DateUtil.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(eTime)) {
            sysAdShare.setEndTime(DateUtil.stringToLong(eTime, DateUtil.YYYY_MM_DD));
        }
        sysAdShare.setCreator(getUsername());
        sysAdShare.setCreateTime(System.currentTimeMillis());
        if (sysAdShareService.save(sysAdShare) > 0) {
            logger.info("广告分享保存成功" + sysAdShare.toString());
            return R.ok();
        }
        logger.info("广告分享保存失败" + sysAdShare.toString());
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("system:sysAdShare:edit")
    public R update(SysAdShareDO sysAdShare) {
        sysAdShareService.update(sysAdShare);
        logger.info("广告分享修改成功" + sysAdShare.toString());
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("system:sysAdShare:remove")
    public R remove(Long id) {
        if (sysAdShareService.remove(id) > 0) {
            logger.info("广告分享删除成功");
            return R.ok();
        }
        logger.info("广告分享删除失败");
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("system:sysAdShare:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        sysAdShareService.batchRemove(ids);
        logger.info("广告分享批量删除成功");
        return R.ok();
    }

}
