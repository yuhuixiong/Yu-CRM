package com.fisher.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	static ObjectMapper objectMapper;

	private JsonUtils(){}
	
	public static <T> T fromJson(String content,Class<T> valueType) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		try {
			return objectMapper.readValue(content, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String toJson(Object object) {
		if(objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
