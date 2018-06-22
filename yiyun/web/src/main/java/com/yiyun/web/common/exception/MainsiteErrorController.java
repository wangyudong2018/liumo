package com.yiyun.web.common.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainsiteErrorController implements ErrorController {
	private Logger logger = LogManager.getLogger();
	private static final String ERROR_PATH = "/error";

	@RequestMapping(value = ERROR_PATH)
	public String handleError() {
		return "error/404";
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}