package com.yiyun.web.system.service;

import com.yiyun.domain.UserOnline;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public interface SessionService {
	List<UserOnline> list();

	Collection<Session> sessionList();
	
	boolean forceLogout(String sessionId);
}
