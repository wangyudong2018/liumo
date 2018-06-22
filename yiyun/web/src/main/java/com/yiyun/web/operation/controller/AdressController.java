package com.yiyun.web.operation.controller;

import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.SysDictConstants;
import com.yiyun.domain.Adress;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.service.DictService;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.operation.service.AdressService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


/**
 * @title
 * @author Xing
 * @date Tue Jun 05 07:37:41 CST 2018
 */
@Controller
@RequestMapping("/operation/adress")
public class AdressController {
    @Autowired
    private AdressService adressService;

    @Autowired
    private DictService dictService;

    @GetMapping()
    @RequiresPermissions("operation:adress:adress")
    public String Adress(Model model) {
        addSelects(model);
        return "operation/adress/adress";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("operation:adress:adress")
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
        Object modifyTime = params.get("modifyTime");
        if (null != modifyTime && StringUtils.isNotBlank((String) modifyTime)) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse((String) modifyTime, dtf);
                LocalDateTime updateTimeBegin = LocalDateTime.of(localDate, LocalTime.MIN);
                LocalDateTime updateTimeEnd = updateTimeBegin.plusDays(1L);
                params.put("updateTimeBegin", updateTimeBegin.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
                params.put("updateTimeEnd", updateTimeEnd.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            } catch (Exception e) {
                System.out.println("时间转换异常");
            }
        }
        //查询列表数据
        Query query = new Query(params);
        List<Adress> adressList = adressService.list(query);
        int total = adressService.count(query);
        return new PageUtil(adressList, total);
    }

    private void addSelects(Model model) {
        Map<String, Object> param = new HashedMap();
        param.put("delFlag", CommonConstants.YES);
        param.put("type", SysDictConstants.DICT_TYPE_CITYS);
        model.addAttribute("cityList", dictService.list(param));

        param.put("type", SysDictConstants.DICT_TYPE_ADRESS_TYPE);
        model.addAttribute("typeList", dictService.list(param));
    }

    @GetMapping("/add")
    @RequiresPermissions("operation:adress:add")
    public String add(Model model) {
        addSelects(model);
        return "operation/adress/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("operation:adress:edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Adress adress = adressService.get(id);
        model.addAttribute("adress", adress);

        addSelects(model);
        return "operation/adress/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("operation:adress:add")
    public R save(Adress adress) {
        if (null == adress.getStatus()) {
            adress.setStatus(CommonConstants.NO);
        }
        if (null == adress.getCreateTime()) {
            adress.setCreateTime(Instant.now().toEpochMilli());
        }
        if (adressService.save(adress) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("operation:adress:edit")
    public R update(Adress adress) {
        if (null == adress.getStatus()) {
            adress.setStatus(CommonConstants.NO);
        }
        adress.setUpdateTime(Instant.now().toEpochMilli());
        adressService.update(adress);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("operation:adress:remove")
    public R remove(Long id) {
        if (adressService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 批量删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("operation:adress:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        adressService.batchRemove(ids);
        return R.ok();
    }
}
