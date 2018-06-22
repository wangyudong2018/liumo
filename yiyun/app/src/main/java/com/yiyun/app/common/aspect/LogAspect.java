package com.yiyun.app.common.aspect;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yiyun.app.common.req.CommonParamReq;
import com.yiyun.app.system.service.SysCommonService;
import com.yiyun.domain.SysCommonDO;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



/**
 * @Title 日志切面
 * @Description 记录日志
 * @author liliang
 * @createDate 2016年3月25日 上午11:32:26
 * @modifier
 * @modifyDate
 * @version 1.0
 */
@Aspect
@Component
@Order(1)
public class LogAspect {
	private static final Logger logger = LogManager.getLogger(LogAspect.class.getName());
	private ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();

	@Autowired
	private SysCommonService sysCommonService;
	
	/**
	 * @Description controller日志切入点
	 * @author liliang
	 * @createDate 2016年3月25日 下午1:15:46
	 * @modifier
	 * @modifyDate
	 * @version 1.0
	 */
	@Pointcut("execution(* com.yiyun.app.*.controller.*.*(..))")
	public void accessController() {
	}

	/**
	 * @Description controller访问前记录日志
	 * @param joinPoint 切入点
	 * @author liliang
	 * @createDate 2016年3月25日 下午1:16:16
	 * @modifier
	 * @modifyDate
	 * @version 1.0
	 * @throws Exception 
	 */
	@Before("accessController()")
	public void doBeforeController(JoinPoint joinPoint) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String remoteAddr = request.getRemoteAddr();
		try {
			String method = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName()
					+ "()";
			StringBuilder params = new StringBuilder();
			if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
				for (int i = 0; i < joinPoint.getArgs().length; i++) {
					if (!(joinPoint.getArgs()[i] instanceof HttpServletResponse)
							&&!(joinPoint.getArgs()[i] instanceof HttpServletRequest)
							) {
						if(joinPoint.getArgs()[i] != null){
							params.append(joinPoint.getArgs()[i].toString()).append(",");
						}
					}
				}
			}
			
			//记录日之后 , 将common信息保存至数据库
			saveCommonInfo(joinPoint);
			
			threadLocal.set(System.currentTimeMillis());
			logger.info("请求开始->" + method + ",IP:" + remoteAddr + ",参数:" + params.toString() + "时间戳:"
					+ System.currentTimeMillis() + ",线程ID:" + Thread.currentThread().getId());
			
		}catch (Exception e) {
			logger.error("controller访问前日志记录异常", e);
			e.printStackTrace();
		}
	}

	/**
	 * @title 保存通用参数到数据库 
	 * @description
	 * @author Xingbz
	 * @createDate 2017年8月15日
	 * @param joinPoint
	 * @version 1.0
	 */
	private void saveCommonInfo(JoinPoint joinPoint) {
		
		Object []args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			for(int i=0;i<args.length;i++){
				if(args[i] instanceof CommonParamReq){
					CommonParamReq commonReq = ((CommonParamReq)joinPoint.getArgs()[i]);//获取通用common
					SysCommonDO commonInfo = new SysCommonDO();
					String memberId = commonReq.getMemberId();
					if(StringUtils.isNotBlank(memberId)){
						commonInfo.setMemberId(Long.valueOf(memberId));
					}
					commonInfo.setPhone(commonReq.getPhone());
					commonInfo.setChlId(commonReq.getChlId());
					commonInfo.setDeviceNo(commonReq.getDeviceNO());
					commonInfo.setCltType(commonReq.getCltType());
					commonInfo.setImsi(commonReq.getImsi());
					commonInfo.setImei(commonReq.getImei());
					commonInfo.setPhoneModel(commonReq.getPhoneModel());
					commonInfo.setPhoneBrand(commonReq.getPhoneBrand());
					commonInfo.setOsVersion(commonReq.getOsVersion());
					commonInfo.setMacAdd(commonReq.getMacAdd());
					commonInfo.setCpuModel(commonReq.getCpuModel());
					commonInfo.setCpuFrequency(commonReq.getCpuFrequency());
					commonInfo.setVersion(commonReq.getVersion());
					commonInfo.setTimestamp(commonReq.getTimestamp());
					commonInfo.setSign(commonReq.getSign());
					commonInfo.setToken(commonReq.getToken());
					commonInfo.setCreateTime(System.currentTimeMillis());
					sysCommonService.save(commonInfo);
				}
			}
		}
	}

	/**
	 * @Description controller访问后记录日志
	 * @param joinPoint 切入点
	 * @author liliang
	 * @createDate 2016年3月25日 下午1:17:11
	 * @modifier
	 * @modifyDate
	 * @version 1.0
	 */
	@After("accessController()")
	public void doAfterController(JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String remoteAddr = request.getRemoteAddr();
		try {
			String method = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
			logger.info("请求结束->" + method + ",IP:" + remoteAddr + ",时间戳:" + new Date().getTime() + ",线程ID:"
					+ Thread.currentThread().getId());
			
			long startTime = threadLocal.get();
			long endTime = System.currentTimeMillis();
			
			logger.info(method + "执行时间:" + (endTime-startTime));
		} catch (Exception e) {
			logger.error("controller访问完成日志记录异常", e);
		}
	}

	
	/**
	 * @Description 抛出异常后记录异常日志
	 * @param joinPoint
	 * @param e
	 * @author liliang
	 * @createDate 2016年4月5日 上午9:52:07
	 * @modifier
	 * @modifyDate
	 * @version 1.0
	 */
	@AfterThrowing(pointcut = "accessController()", throwing = "e")
	public void doAfterControllerThrowing(JoinPoint joinPoint, Throwable e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String remoteAddr = request.getRemoteAddr();
		try {
			String method = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
			logger.error("请求异常->" + method + ",IP:" + remoteAddr + ",时间戳:" + new Date().getTime() + ",线程ID:"
					+ Thread.currentThread().getId(),e);
		} catch (Exception ex) {
			logger.error("controller访问完成日志记录异常", ex);
		}
	}
}