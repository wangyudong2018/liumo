package com.yiyun.app.common.utils;

import com.yiyun.constants.CommonConstants;

import java.util.HashMap;

public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

    private R() {
        put("flag", 1);
        put("message", "成功");
    }

	public static R error() {
        return error(0, "失败");
    }

    public static R error(String message) {
        return error(0, message);
    }

    public static R error(int flag, String message) {
        R r = new R();
        r.put("flag", flag);
        r.put("message", message);
        return r;
    }

    public static R ok(String message) {
        R r = new R();
        r.put("flag", CommonConstants.YES);
        r.put("message", message);
        return r;
    }

//	public static R ok(Map<String, Object> map) {
//		R r = new R();
//        r.put("flag" , CommonConstants.YES);
//		r.putAll(map);
//		return r;
//	}

    public static R ok(Object data) {
        R r = new R();
        r.put("flag", CommonConstants.YES);
        r.put("DATA", data);
        return r;
    }

    public static R ok(String message, Object data) {
        R r = new R();
        r.put("flag", CommonConstants.YES);
        r.put("message", message);
        r.put("DATA", data);
        return r;
    }

	public static R ok() {
        R r = new R();
        r.put("flag", CommonConstants.YES);
        return r;
    }

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
