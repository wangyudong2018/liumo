package com.yiyun.web.wechat.controller;

import com.yiyun.constants.SysDictConstants;
import com.yiyun.domain.DictDO;
import com.yiyun.web.base.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title 微信公众号跳转
 * @description
 * @author Xingbz
 * @createDate 2018/3/28
 * @version 1.0
 */
@Controller
@RequestMapping("/wechat")
public class WechatPublicController {

    @Autowired
    private DictService dictService;

    @GetMapping("/index")
    public String index() {
        return "wechat/index";
    }

    @GetMapping("/fuwu")
    public String fuwu() {
        return "wechat/fuwu";
    }

    @GetMapping("/jigou")
    public String jigou() {
        return "wechat/jigou";
    }

    @GetMapping("/moshi")
    public String moshi() {
        return "wechat/moshi";
    }

    @GetMapping("/money")
    public String money() {
        return "wechat/money";
    }

    @GetMapping("/shenqing")
    public String shenqing(Model model) {
        Map<String, Object> cityMap = new HashMap<>();//支持的城市列表
        cityMap.put("type", SysDictConstants.DICT_TYPE_CITYS);
        List<DictDO> cityList = dictService.list(cityMap);

        Map<String, Object> productMap = new HashMap<>();//支持的产品列表
        productMap.put("type", SysDictConstants.DICT_TYPE_PRODUCT_TYPE);
        List<DictDO> productList = dictService.list(productMap);

        model.addAttribute("cityList", cityList);
        model.addAttribute("productList", productList);
        return "wechat/shenqing";
    }

}
