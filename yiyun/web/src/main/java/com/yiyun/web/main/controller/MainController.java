package com.yiyun.web.main.controller;

import com.yiyun.constants.RedisConstants;
import com.yiyun.constants.SysDictConstants;
import com.yiyun.domain.DictDO;
import com.yiyun.domain.PProduct;
import com.yiyun.req.MessageReq;
import com.yiyun.resp.SendMessageResp;
import com.yiyun.utils.DataUtil;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.controller.BaseController;
import com.yiyun.web.base.service.DictService;
import com.yiyun.web.common.config.redis.RedisTemplateDAO;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.SmsUtil;
import com.yiyun.web.operation.service.ProductService;
import com.yiyun.web.rabbitMQ.util.SendMqUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title 首页Controller
 * @description
 * @author Xingbz
 * @createDate 2018/3/12
 * @version 1.0
 */
@Controller
@RequestMapping("/main")
public class MainController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private DictService dictService;


    @Autowired
    private RedisTemplateDAO redisTemplateDAO;

    @GetMapping("/index")
    public String main(Model model) {
        Map<String, Object> cityMap = new HashMap<>();//支持的城市列表
        addSelect(model);
        return "main/index";
    }

    private void addSelect(Model model) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("type", SysDictConstants.DICT_TYPE_CITYS);
        List<DictDO> cityList = dictService.list(paramMap);

        Map<String, Object> productMap = new HashMap<>();//支持的产品列表
        productMap.put("type", SysDictConstants.NEW_TYPE_PRODUCT_TYPE);
        List<DictDO> productList = dictService.list(productMap);

        model.addAttribute("cityList", cityList);
        model.addAttribute("productList", productList);
    }

    @GetMapping("/product")
    public String product(Model model) {
        Map<String, Object> cityMap = new HashMap<>();//支持的城市列表
        addSelect(model);
        return "main/product";
    }

    @ResponseBody
    @RequestMapping(value = "/sendSms", produces = "application/json;charset=utf-8")
    public String sendMessage(MessageReq req) {
        String phone = req.getPhone();
        if (!DataUtil.isMobile(phone)) {
            return returnFail("手机号有误");
        }

        final String SMS_KEY = RedisConstants.CACHE_KEY_SMS_CODE + phone;
        final String COUNT_KEY = RedisConstants.CACHE_KEY_SMS_CODE_COUNT + phone;

        if (!SmsUtil.getSmsConfigurationProperties().isSmsSwitch()) {//短信开关
            redisTemplateDAO.put(SMS_KEY, "1111", 10, 1800L);
            return returnSucess("短信发送成功");
        }

        String count = redisTemplateDAO.get(COUNT_KEY, 10);//发送短信的次数
        if (StringUtils.isNotBlank(count) && Integer.valueOf(count) >= 20) {
            return returnFail("短信发送失败 , 该号码已达今日上限");
        }

        int code = (int) ((Math.random() * 9 + 1) * 1000);//随机4位验证码
        logger.info("XInfO.[发送短信] 验证码 : " + code + " , redis key : " + SMS_KEY);
        SendMessageResp resp = SendMqUtil.sendShortMessageMine(phone, String.valueOf(code));
        if (resp.isFlag()) {
            redisTemplateDAO.put(SMS_KEY, String.valueOf(code), 10, 1800L);
            String smsCount = StringUtils.isBlank(count) ? "1" : Integer.valueOf(count) + 1 + "";
            redisTemplateDAO.put(COUNT_KEY, smsCount, 10, RedisConstants.OUT_TIME_ONE_DAY);//24h限制
            return returnSucess("短信发送成功");
        } else {
            return returnFail("短信发送失败");
        }
    }


    /*
       跳转到产品页
         */
    @GetMapping("/toProduct")
    public String product(@RequestParam Map<String, Object> params, Model model) {
        Map<String, Object> cityMap = new HashMap<>();//支持的城市列表
        cityMap.put("type", SysDictConstants.DICT_TYPE_CITYS);
        List<DictDO> cityList = dictService.list(cityMap);

        Map<String, Object> productMap = new HashMap<>();//支持的产品列表
        productMap.put("type", SysDictConstants.DICT_TYPE_PRODUCT_TYPE);
        List<DictDO> productTypeList = dictService.list(productMap);

        model.addAttribute("cityList", cityList);
        model.addAttribute("productList", productTypeList);

        //查询列表数据
        if (null == params || params.size() == 0) {
            params.put("limit", 10);
            params.put("offset", 0);
            params.put("name", null);
        }
        Query query = new Query(params);
        List<PProduct> productList = productService.list(query);
        int total = productService.count(query);
        PageUtil pageUtil = new PageUtil(productList, total);
        model.addAttribute("products", pageUtil);
        return "main/product";
    }

    /*
      跳转到关于我们
        */
    @GetMapping("/toAboutUs")
    public String toAboutUs() {
        return "main/about_us";
    }

    /*
    网站获取产品详情
     */
    @GetMapping("/getProductById")
    public String getProductById(Long id, Model model) {
        //查询列表数据
        PProduct pProduct = productService.get(id);
        String[] split = pProduct.getNeedData().split(";");
        model.addAttribute("product", pProduct);
        model.addAttribute("data", split);
        return "main/product_details";
    }
}