package com.yiyun.app.common.controller;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @title 小程序基础controller
 * @author Xingbz
 * @createDate 2018-6-10
 */
public class MappBaseController {


//    public JSONObject getJsonObject(HttpServletRequest request) {
//        JSONObject jsonObject = new JSONObject();
//        String queryStr = request.getQueryString();
//        try {
//            if (StringUtils.isNotBlank(queryStr)) {
//                queryStr = URLDecoder.decode(queryStr, "UTF-8");
//                if (StringUtils.isNotBlank(queryStr)) {
//                    jsonObject = JSON.parseObject(queryStr);
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(">>> 请求参数转换异常 : " + queryStr);
//        }
//
//        return jsonObject;
//    }

    /**
     *
     * @Description 后台返回结果
     * @author XieLinGe
     * @createDate 2016年4月8日 上午11:28:06
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    public String returnData(Map<String, Object> data) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "ok");
        jsonObject.put("message", "成功");
        if (null != data && !data.isEmpty()) {
            data.forEach((key, value) -> {
                jsonObject.put(key, value);
            });
        }
        return jsonObject.toJSONString();
    }

    /**
     *
     * @Description 返回信息
     * @author XieLinGe
     * @createDate 2016年4月8日 上午11:34:48
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    public String returnSucess(String message) {
        return "{\"status\":\"ok\",\"message\":\"" + message + "\"}";
    }

    public String returnSucess() {
        return "{\"status\":\"ok\",\"message\":\"成功\"}";
    }

    public String returnFail(String message) {
        return "{\"status\":\"error\",\"message\":\"" + message + "\"}";
    }

    public String returnFailCode(String errorcode, String message) {
        return "{\"status\":\"error\",\"errorcode\":" + errorcode + ",\"message\":\"" + message + "\"}";
    }

    public static  String returnF(String errorcode, String message) {
        return "{\"status\":\"error\",\"errorcode\":" + errorcode + ",\"message\":\"" + message + "\"}";
    }
}