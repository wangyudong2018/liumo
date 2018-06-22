package com.yiyun.web.common.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yiyun.constants.RedisConstants;
import com.yiyun.web.common.config.redis.RedisTemplateDAO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//import com.yiyun.app.common.req.MemberCommonReq;

/**
 * @title app端登陆验证拦截器
 * @author Xingbz
 * @createDate 2017年8月2日
 * @version 1.0
 *
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /** 忽略的URL */
    private static final String[] NO_CHECK_URL = new String[]{"wechat/appMember/login", "wechat/appMember/logout", "wechat/appMember/sendSms", "wechat/appMember/toLogin", "wechat/appMember/toTemp"};
    private RedisTemplateDAO redisTemplateDAO;

    public LoginInterceptor() {
    }


    public LoginInterceptor(RedisTemplateDAO redisTemplateDAO) {
        this.redisTemplateDAO = redisTemplateDAO;
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {//如果不是映射到方法直接通过(js,css等静态文件)
            return true;
        }

        String url = request.getRequestURL().toString();
        String method = request.getMethod();//请求方式
        if (StringUtils.isNotBlank(method) && method.equals("GET") && !url.contains("/wechat/appMember") && !url.contains("/wechat/appReserve")) {
            return true;
        }
        for (String str : NO_CHECK_URL) {//不拦截上面定义的路径
            if (url.contains(str)) {
                return true;
            }
        }

        if (url.contains("/wechat/appMember") || url.contains("/wechat/appReserve")) {//只拦截微信账户下的
            return checkToken(request, response);
        }
        return true;
    }


    /**
     * @title 验证请求参数中的id, phone, token, 三者一致才可通过
     * @author Xingbz
     * @createDate 2017年8月9日
     * @param request
     * @param response
     * @return
     * @version 1.0
     */
    private boolean checkToken(HttpServletRequest request, HttpServletResponse response) {
        String phone = request.getParameter("phone");
        String token = redisTemplateDAO.get(RedisConstants.CACHE_KEY_MEMBER_TOKEN + phone, 10);
        if (token == null || token.trim().length() == 0) {//没有登录
            try {
                response.sendRedirect("/wechat/appMember/toLogin?redirectUrl=" + request.getRequestURI());
            } catch (IOException e) {
                e.printStackTrace();
                Map<String, Object> map = new HashMap<>();
                map.put("loginUrl", "");
                map.put("redirectUrl", request.getRequestURI());
                returnSignFail(map, "未登录", response);
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * @title 拦截校验的异常信息
     * @author Xingbz
     * @createDate 2017年8月9日
     * @param message
     * @param response
     * @version 1.0
     */
    @SuppressWarnings("static-access")
    protected void returnSignFail(Map<String, Object> data, String message, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("flag", "-1");
        result.put("message", message);
        result.put("DATA", data);
        try {
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue.WriteNullStringAsEmpty.DisableCircularReferenceDetect));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
