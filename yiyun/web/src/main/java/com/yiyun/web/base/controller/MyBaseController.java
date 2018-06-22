package com.yiyun.web.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title controller基类
 * @Description controller基类, 定义公用的返回参数格式
 * @author XieLinGe
 * @createDate 2016年3月24日 下午4:44:14
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class MyBaseController {

    /**
     * @Description 返回成功
     * @author liliang
     * @createDate 2016年3月24日 下午4:44:42
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    protected void returnSuccess(Object data, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("flag", "1");
        result.put("message", "");
        result.put("DATA", data);
        writeJSONToPage(result, response);
    }

    /**
     * @Description 返回失败
     * @author liliang
     * @createDate 2016年3月24日 下午4:48:51
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    protected void returnFail(Object data, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("flag", "0");
        result.put("message", "");
        result.put("DATA", data);
        writeJSONToPage(result, response);
    }

    /**
     * @Description 返回成功, 自定义提示信息
     * @author liliang
     * @createDate 2016年3月24日 下午4:44:42
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    protected void returnSuccess(Object data, String message, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("flag", "1");
        result.put("message", message);
        result.put("DATA", data);
        writeJSONToPage(result, response);
    }

    /**
     * @Description 返回失败
     * @author liliang
     * @createDate 2016年3月24日 下午4:48:51
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    protected void returnFail(Object data, String message, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("flag", "0");
        result.put("message", message);
        result.put("DATA", data);
        writeJSONToPage(result, response);
    }

    /**
     * @Description 返回成功, 自定义提示信息
     * @author liliang
     * @createDate 2016年3月24日 下午4:44:42
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    protected void returnSuccess(String message, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("flag", 1);
        result.put("message", message);
        result.put("DATA", new JSONObject());
        writeJSONToPage(result, response);
    }

    /**
     * @Description 返回失败
     * @author liliang
     * @createDate 2016年3月24日 下午4:48:51
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    protected void returnFail(String message, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("flag", "0");
        result.put("message", message);
        result.put("DATA", new JSONObject());
        writeJSONToPage(result, response);
    }

    protected void writeJSONToPage(JSONObject result, HttpServletResponse response) {
        try {
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Description 后台返回结果
     * @author XieLinGe
     * @createDate 2016年4月8日 上午11:28:06
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    public String returnData(String data) {
        String result = "{\"flag\":\"1\",\"DATA\":" + data + ",\"message\":\"\"}";

        return result;
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
        String result = "{\"flag\":\"1\",\"message\":\"" + message + "\"}";

        return result;
    }

    public String returnFail(String message) {
        String result = "{\"flag\":\"0\",\"message\":\"" + message + "\"}";

        return result;
    }
}
