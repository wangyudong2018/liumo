package com.yiyun.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @Title 用户认证异常
 * @Description 用户认证异常
 * @author liliang
 * @createDate 2016年4月8日 上午9:40:42
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class AuthenticationException extends Exception{
	
	public AuthenticationException(String message){
		super(message);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}

	@Override
	public synchronized Throwable getCause() {
		return super.getCause();
	}

	@Override
	public synchronized Throwable initCause(Throwable cause) {
		return super.initCause(cause);
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

	@Override
	public void printStackTrace(PrintStream s) {
		super.printStackTrace(s);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		super.printStackTrace(s);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return super.fillInStackTrace();
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return super.getStackTrace();
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		super.setStackTrace(stackTrace);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
