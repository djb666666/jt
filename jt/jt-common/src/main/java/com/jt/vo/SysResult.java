package com.jt.vo;

import java.io.Serializable;

import org.apache.catalina.startup.FailedContext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 定义系统返回值对象
 * @author Administrator
 *
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -736448160628435100L;
	private Integer status;//200表示正确
	private String msg;//服务器返回的业务消息
	private Object data;//服务器返回的业务数据
	
	public static SysResult success() {

		return new SysResult(200,"服务器执行成功",null);
	}
	
	public static SysResult success(Object data) {

		return new SysResult(200,"服务器执行成功",data);
	}
	public static SysResult success(String msg,Object data) {

		return new SysResult(200,msg,data);
	}
	
	/*
	 * public static SysResult success(String msg) { return new
	 * SysResult(200,msg,null); }
	 */
	
	public static SysResult fail() {
		return new SysResult(201,"服务器执行失败",null);
	}

}













