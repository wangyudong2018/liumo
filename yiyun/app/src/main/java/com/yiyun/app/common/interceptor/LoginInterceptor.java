package com.yiyun.app.common.interceptor;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yiyun.app.common.config.redis.RedisTemplateDAO;
import com.yiyun.app.common.req.CommonParamReq;
import com.yiyun.utils.JsonPluginsUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class LoginInterceptor implements HandlerInterceptor{

    /** 忽略的URL */
    private static final String[] NO_CHECK_URL = new String[]{"appMember/login", "appMember/logout", "appMember/sendSms", "appMember/register", "appMember/resetPassword", "appDict/getDictItems",
            "appImgCode/getImgCode", "appImgCode/checkImgCode", "appSys/addSysBreakdownInfo", "appFeedback/addFeedback", "appHelp/getHelpDoc", "callback"};
    //	@Resource(name="redisTemplateDAO")
    private RedisTemplateDAO redisTemplateDAO;

    public LoginInterceptor() {
    }


    public LoginInterceptor(RedisTemplateDAO redisTemplateDAO) {
        this.redisTemplateDAO = redisTemplateDAO;
    }

    @Override
	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request , HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {//如果不是映射到方法直接通过(js,css等静态文件)
			return true;
		}
		
		String url = request.getRequestURL().toString();
		String method = request.getMethod();//请求方式
		if(StringUtils.isNotBlank(method)&&method.equals("GET")){
			return true;
		}
	        for (String str : NO_CHECK_URL) {//不拦截上面定义的路径
	            if (url.contains(str)) {
	            	return true;
	            }
	        }
		
//	      if(url.contains("appCredit/getCreditInfo")){//获取额度信息时单独校验
//	  		String dataStr = request.getParameter("data");
//	  		MemberCommonReq req = JsonPluginsUtil.jsonToBean(dataStr, MemberCommonReq.class);
//	  		if(StringUtils.isBlank(req.getMemberId()) && StringUtils.isBlank(req.getPhone())){//如果用户编号和手机号均为空 , 则为未登录状态 , 跳过验证
//	  			return true;
//	  		}
//	      }

        return checkToken(request, response);
    }
	

	/**
	 * @title 验证请求参数中的id,phone,token,三者一致才可通过
	 * @author Xingbz
	 * @createDate 2017年8月9日
	 * @param request
	 * @param response
	 * @return
	 * @version 1.0
	 */
	private boolean checkToken(HttpServletRequest request, HttpServletResponse response) {
//		String token = checkCommon(request, response);
//		if(token == null){
//			return false;
//		}

//        String phone = request.getParameter("phone");
//        String token = redisTemplateDAO.get(RedisConstants.CACHE_KEY_MEMBER_TOKEN + phone, 10);
//        if (token == null || token.trim().length() == 0) {//没有登录
//            Map<String, Object> map = new HashMap<>();
//            map.put("redirectUrl", request.getRequestURI());
//            returnSignFail(map, "未登录", response);
//            return false;
//		}

		/* 缓存信息验证 */
//		String dataStr = request.getParameter("data");
//		MemberCommonReq req = JsonPluginsUtil.jsonToBean(dataStr, MemberCommonReq.class);
//		String cacheKey = req.getMemberId() + req.getPhone();
//
//		String val = redisTemplateDAO.get(cacheKey , 10);

//		if(val == null){//没有登录
//			returnSignFail("未登录" , response);
//			return false;
//		}
//
//		if(!token.equals(val)){
//			returnSignFail("重复登录，请重新登录！" , response);
//			return false;
//		}

		return true;
	}

	/**
	 * @title 校验公共参数信息 
	 * @author Xingbz
	 * @createDate 2017年8月10日
	 * @param request
	 * @param response
	 * @return
	 * @version 1.0
	 */
	private String checkCommon(HttpServletRequest request, HttpServletResponse response) {
		/* 公共信息验证 */
		String commonStr = request.getParameter("common");
		if(commonStr == null){
            returnSignFail(null, "公共参数不完整", response);
            return null;
		}
		
		CommonParamReq commonReq = JsonPluginsUtil.jsonToBean(commonStr, CommonParamReq.class);
		String token = commonReq.getToken();
		if(StringUtils.isBlank(token)){
            returnSignFail(null, "token错误", response);
            return null;
		}
		return token;
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
        result.put("DATA", JSON.toJSONString(data));
        try {
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(JSONObject.toJSONString(result,SerializerFeature.WriteMapNullValue.WriteNullStringAsEmpty.DisableCircularReferenceDetect));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
