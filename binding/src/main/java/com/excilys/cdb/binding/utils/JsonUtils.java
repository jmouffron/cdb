package com.excilys.cdb.binding.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	public static <T> String returnJsonFrom(T entity) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(entity);
	}

	public static <T> T returnObjectFrom(String json, Class<T> clazz) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(json, clazz);
	}
}
