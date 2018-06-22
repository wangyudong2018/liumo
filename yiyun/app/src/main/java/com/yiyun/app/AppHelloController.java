package com.yiyun.app;

import com.yiyun.app.common.controller.BaseController;
import com.yiyun.domain.OReserve;
import org.ehcache.xml.model.Heap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.terracotta.offheapstore.concurrent.ConcurrentOffHeapClockCache;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.CountDownLatch;


@ApiIgnore
@RestController
public class AppHelloController extends BaseController {


    @GetMapping(value = "/hello")
	public void hello(HttpServletResponse response){



    	returnSuccess("AppServer启动成功...",response);
	}

	public static void main(String[] args) {
		ArrayList list=new ArrayList();
		int num=0;
		while(true){
			num++;
			list.add(new byte[2*2024]);
			System.out.println(num);
		}

	}
}
