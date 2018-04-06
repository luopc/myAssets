package com.common.framework.base;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class TableJson {
	
	private Integer code;
	
	private String msg;
	
	private Integer count;
	
	private Object data;
	
	
	public TableJson(Integer code) {
		this.code = code;
	}

	public static TableJson success(Object object, String msg,Integer count) {
		TableJson jsonData = new TableJson(0);
		jsonData.data = object;
		jsonData.msg = msg;
		jsonData.count = count;
		return jsonData;
	}

	public static TableJson success(Object object,Integer count) {
		TableJson jsonData = new TableJson(0);
		jsonData.data = object;
		jsonData.count = count;
		return jsonData;
	}
	
	
	public static TableJson fail(String msg) {
		TableJson jsonData = new TableJson(1);
		jsonData.msg = msg;
		return jsonData;
	}

	public Map<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("code", code);
		result.put("msg", msg);
		result.put("count", count);
		result.put("data", data);
		return result;
	}

}
