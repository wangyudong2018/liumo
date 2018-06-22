package com.yiyun.web.base.controller;


import com.yiyun.constants.CommonConstants;
import com.yiyun.domain.DictDO;
import com.yiyun.domain.SysHierarchyDict;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.service.DictService;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-29 18:28:07
 */
@ApiIgnore

@Controller
@RequestMapping("/common/sysDict")
public class DictController extends BaseController {
    @Autowired
    private DictService sysDictService;

    @GetMapping()
    @RequiresPermissions("common:sysDict:sysDict")
    String sysDict() {
        return "common/sysDict/sysDict";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("common:sysDict:sysDict")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
        List<DictDO> sysDictList = sysDictService.list(query);
        int total = sysDictService.count(query);
        PageUtil pageUtil = new PageUtil(sysDictList, total);
        return pageUtil;
    }

    @GetMapping("/add")
    @RequiresPermissions("common:sysDict:add")
    String add() {
        return "common/sysDict/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("common:sysDict:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        DictDO sysDict = sysDictService.get(id);
        model.addAttribute("sysDict", sysDict);
        return "common/sysDict/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("common:sysDict:add")
    public R save(DictDO sysDict) {
        if(StringUtils.isBlank(sysDict.getDelFlag())){
            sysDict.setDelFlag(CommonConstants.NO + "");
        }

        if (sysDictService.save(sysDict) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("common:sysDict:edit")
    public R update(DictDO sysDict) {
        if(StringUtils.isBlank(sysDict.getDelFlag())){
            sysDict.setDelFlag(CommonConstants.NO + "");
        }
        sysDictService.update(sysDict);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("common:sysDict:remove")
    public R remove(Long id) {
        if (sysDictService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("common:sysDict:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        sysDictService.batchRemove(ids);
        return R.ok();
    }

    @GetMapping("/type")
    @ResponseBody
    public Map<String , List<DictDO>> listType() {
        Map<String , List<DictDO>> map = new HashMap<>();
        map.put("flags", sysDictService.listFlag());
        map.put("types", sysDictService.listType());
        return map;
    }

    // 类别已经指定增加
    @GetMapping("/add/{flag}/{type}/{description}")
    @RequiresPermissions("common:sysDict:add")
    String addD(Model model, @PathVariable("flag") String flag,@PathVariable("type") String type, @PathVariable("description") String description) {
        model.addAttribute("flag", flag);
        model.addAttribute("type", type);
        model.addAttribute("description", description);
        return "common/sysDict/add";
    }

    @ResponseBody
    @GetMapping("/listByType")
    public List<DictDO> listByType(String flag , String type) {
        // 查询列表数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("flag" , flag);
        map.put("type", type);
        return sysDictService.list(map);
    }


    /*====== 以下为多级菜单联动的内容 ======*/
    @ResponseBody
    @GetMapping("/getHierarchyDicts")
    public R getHierarchyDicts(Integer type, String parentCode, Integer level) {
        // 查询列表数据
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(parentCode)) {//未指定父级ID , 说明为查第一级字典项 , 需要指定type
            if (type == null) {
                return R.error("参数错误");
            }
            map.put("type", type);
        } else {
            map.put("parentCode", parentCode);
        }
        map.put("level", level);
        List<SysHierarchyDict> list = sysDictService.listHierarchyDict(map);
        map.clear();
        map.put("data", list);
        return R.ok(map);
    }
}
