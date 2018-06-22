package com.yiyun.app.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.HibernateValidator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;


/**
 * @Title 请求参数bean根据注解校验
 * @Description 请求参数bean根据注解校验
 * @author XieLinGe
 * @createDate 2016年4月5日 下午1:01:22
 * @modifier
 * @modifyDate
 * @version 1.0
 */
@Component
public class BeanValidator {

	/**
	 * 以下注释部分是对用户校验初始化service
	 */
//	@Autowired
//	private UUsersService uUsersService;

	private static BeanValidator beanValid;

//	public void setUserInfo(UUsersService service) {
//		this.uUsersService = service;
//	}

	@PostConstruct
	public void init() {
		beanValid = this;
//		beanValid.uUsersService = this.uUsersService;
		////System.out.println("初始化验证类======================================================");
	}

	private static Validator validator = Validation.byProvider( HibernateValidator.class )
	        .configure()
	        .failFast( true )
	        .buildValidatorFactory()
	        .getValidator();


	public static ValidateResult validateBean(Object bean){
		ValidateResult result = new BeanValidator().new ValidateResult();
		Set<ConstraintViolation<Object>> cvSet = validator.validate(bean);
		//对用户统一校验封装  是否是真实用户
		String jsonString = JSON.toJSONString(bean);
		JSONObject json = JSONObject.parseObject(jsonString);
		String userId = null;
		String userPhone = null;
		if(json.get("userId")!=null&&!json.get("userId").equals("")&&json.get("userPhone")!=null&&!json.get("userPhone").equals("")){
			userId = json.get("userId").toString();
			userPhone = json.get("userPhone").toString();
		}

		if(cvSet.size() > 0){
			Iterator<ConstraintViolation<Object>> iterator = cvSet.iterator();
			while(iterator.hasNext()){
				ConstraintViolation cv = iterator.next();
				result.setValid(false);
				result.setMessage(cv.getMessage());
				break;
			}
		}else if(StringUtils.isNotBlank(userId)&&StringUtils.isNotBlank(userPhone)){
			boolean checkUser = true;//beanValid.uUsersService.checkUser(Long.valueOf(userId),userPhone);
			if(checkUser){
				result.setValid(true);
				result.setMessage("");
			}else{
				result.setValid(false);
				result.setMessage("该用户不存在");
			}
		}else{
			result.setValid(true);
			result.setMessage("");
		}
		return result;

	}

	public class ValidateResult{
		private boolean isValid;
		private String message;

		public boolean isValid() {
			return isValid;
		}
		public void setValid(boolean isValid) {
			this.isValid = isValid;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}
}
