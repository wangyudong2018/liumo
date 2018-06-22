package com.yiyun.web.common.utils;

import com.yiyun.domain.UserDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {
    private static Subject getSubjct() {
        return SecurityUtils.getSubject();
	}
	public static UserDO getUser() {
		return (UserDO)getSubjct().getPrincipal();
	}
	public static Long getUserId() {
		return getUser().getUserId();
	}
	public static void logout() {
		getSubjct().logout();
	}
}
