package com.yiyun.app.mapp.controller;

import com.alibaba.fastjson.JSONObject;
import com.yiyun.app.common.controller.MappBaseController;
import com.yiyun.app.mapp.service.MLoanService;
import com.yiyun.constants.CommonConstants;
import com.yiyun.dao.cluster.ClusterAdressDao;
import com.yiyun.dao.cluster.ClusterLoanDao;
import com.yiyun.dao.cluster.ClusterMMemberDao;
import com.yiyun.dao.cluster.ClusterNewProductDao;
import com.yiyun.dao.master.LoanDao;
import com.yiyun.dao.master.MMemberDao;
import com.yiyun.domain.Adress;
import com.yiyun.domain.Loan;
import com.yiyun.domain.MMember;
import com.yiyun.domain.NewProduct;
import com.yiyun.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title
 * @author Xingbz
 * @createDate 2018-6-10
 */
@RestController
@RequestMapping("/appminiprog")
public class MInfoController extends MappBaseController {

    @Autowired
    private ClusterMMemberDao clusterMMemberDao;

    @Autowired
    private MMemberDao memberDao;

    @Autowired
    private MLoanService mLoanService;

    @Autowired
    private ClusterLoanDao clusterLoanDao;

    @Autowired
    private ClusterNewProductDao clusterNewProductDao;

    @Autowired
    private LoanDao loanDao;

    @Autowired
    private ClusterAdressDao clusterAdressDao;
    private static final Logger logger = LoggerFactory.getLogger(MInfoController.class);

    /**
     * 功能：产品查询
     * 输入：timestamp,v,miniid,partner_id
     * 输出：status,errorcode,messages，productlist
     * productlist：product_no,product_name,product_rate,product_advantage
     */
    @ResponseBody
    @RequestMapping(value = "/queryProductList", produces = "application/json; charset=utf-8")
    public Object queryProductList(Long partner_id) {
        logger.info("XInfO.[产品查询]请求参数 : " + partner_id);
        try {
            if (null == partner_id) {
                return returnFailCode("05", "缺少参数,partner_id不存在");
            }

            MMember member = clusterMMemberDao.get(partner_id);
            if (null == member) {
                return returnFailCode("05", "参数错误partner_id对应的信息不存在");
            }

            Map<String, Object> param = new HashMap<>();
            param.put("status", CommonConstants.YES);
            List<NewProduct> newProductList = clusterNewProductDao.list(param);
            Map<String, Object> result = new HashMap<>();
            List<JSONObject> jsonObjects = new ArrayList<>();
            if (null != newProductList && !newProductList.isEmpty()) {
                newProductList.forEach(newProduct -> {
                    JSONObject object = new JSONObject();
                    object.put("PRODUCT_NO", newProduct.getId());
                    object.put("PRODUCT_NAME", newProduct.getName());
                    object.put("PRODUCT_RATE", newProduct.getPosition());
                    object.put("PRODUCT_AREA", newProduct.getInferiority());
                    object.put("PRODUCT_ADVANTAGE", newProduct.getAdvantage());

                    object.put("PRODUCT_SMALLTYPE", newProduct.getType2());//小类
                    object.put("PRODUCT_INTRODUCE", newProduct.getContent());//简介
                    jsonObjects.add(object);
                });
            }

            result.put("productlist", jsonObjects);
            return returnData(result);
        } catch (Exception e) {
            logger.error("XErroR.[产品查询]异常", e);
            return returnFailCode("01", "产品查询时发生异常");
        }
    }

    /**
     * 功能：地址查询
     * 输入：timestamp,v,miniid,addr_city，addr_type
     * 输出：status,errorcode,messages，addrlist
     * addrlist内容：
     */
    @ResponseBody
    @RequestMapping(value = "/queryAddrList", produces = "application/json; charset=utf-8")
    public Object queryAddrList(String addr_city, String addr_type) {
        logger.info("XInfO.[地址查询]请求参数 : " + addr_city + " , " + addr_type);
        try {
            if (StringUtils.isAnyBlank(addr_city, addr_type)) {
                return returnFailCode("05", "缺少参数,addr_city,或addr_type不存在");
            }

            Map<String, Object> param = new HashMap<>();
            param.put("city", Loan.getCityByPinyin(addr_city));
            param.put("type", Adress.getTypeIdCode(addr_type));
            param.put("status", CommonConstants.YES);
            List<Adress> adresses = clusterAdressDao.list(param);
            if (null == adresses || adresses.isEmpty()) {
                return returnFailCode("05", "未查询到任何地址信息");
            }
            Map<String, Object> data = new HashMap<>();
            List<JSONObject> jsonObjects = new ArrayList<>();
            adresses.forEach(adress -> {
                JSONObject object = new JSONObject();
                object.put("ADDR_ID", adress.getId());
                object.put("ADDR_STATE", CommonConstants.YES);
                object.put("ADDR_ADDRESS", adress.getDetailAdress());
                object.put("ADDR_CITY", adress.getCity());
                object.put("ADDR_MOBILE", adress.getPhone());
                object.put("CREATE_TIME", DateUtil.long2String(adress.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                object.put("ADDR_NO", adress.getId());
                object.put("UPDATE_TIME", DateUtil.long2String(adress.getUpdateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                object.put("ADDR_AREA", adress.getArea());
                object.put("ADDR_TYPE", Adress.getCodeByType(adress.getType()));

                jsonObjects.add(object);
            });
            data.put("addrlist", jsonObjects);
            return returnData(data);
        } catch (Exception e) {
            logger.error("XErroR.[地址查询]请求异常", e);
            return returnFailCode("01", "地址查询时发生异常");
        }
    }

    /**
     * 功能：我的，显示经纪人/合伙人的成功放款的信息
     * 输入：timestamp,v,miniid,partner_id
     * 输出：status,errorcode,messages，loan_num
     * addrlist内容：
     */
    @ResponseBody
    @RequestMapping(value = "/queryMyLoan", produces = "application/json; charset=utf-8")
    public Object queryMyLoan(Long partner_id) {
        logger.info("XInfO.[个人中心]请求参数 : " + partner_id);
        try {

            if (null == partner_id) {
                return returnFailCode("05", "缺少参数,partner_id不存在");
            }

            MMember member = clusterMMemberDao.get(partner_id);
            if (null == member) {
                return returnFailCode("05", "参数错误partner_id对应的信息不存在");
            }

            Map<String, Object> param = new HashMap<>();
            param.put("partner", partner_id);
            param.put("orderStatus", Loan.LOAN_STATUS_FKCG);

            List<Loan> loans = clusterLoanDao.list(param);

            Map<String, Object> data = new HashMap<>();
            data.put("loan_num", loans.size());
            return returnData(data);
        } catch (Exception e) {
            logger.error("XErroR.[个人中心]请求异常", e);
            return returnFailCode("01", "我的信息查询时发生异常");
        }

    }
}