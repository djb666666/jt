package com.jt.util;

import java.io.IOException;

import javax.management.RuntimeErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	private static final ObjectMapper Mapper=new ObjectMapper();
	/**
	 * 根据API将对象转化为json 同时将json转化为对象
	 */
	public static String toJson(Object obj) {
		String result=null;
		try {
			result = Mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public static <T>T toOBject(String json,Class<T> targetClass) {
		T obj=null;
		try {
			obj=Mapper.readValue(json, targetClass);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return obj;
	}
}
