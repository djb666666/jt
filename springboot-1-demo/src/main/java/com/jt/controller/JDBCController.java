package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController	//如果能够保证返回的数据都是json串则可以使用该注解
@ConfigurationProperties(prefix = "jdbc")//利用对象的set方法为属性赋值.
@PropertySource("classpath:/properties/jdbc.properties")//将制定的配置文件导入spring容器
public class JDBCController {
	/**
	 * 批量为属性赋值
	 * 规定:如果使用批量赋值操作,需要使用set方法
	 * 对比:@VALUE注解更加常用.
	 */
	
	private String driver;	//驱动名称
	private String name;	//用户名信息
	private String password;//密码
	
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}





	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 1.为对象的属性赋值.
	 * 利用@value 注解从spring容器中取值
	 * spl表达式
	 */
	/*
	 * @Value(value="${jdbc.driver}") private String driver; //驱动名称
	 * 
	 * @Value(value="${jdbc.name}") private String name; //用户名信息
	 * 
	 * @Value(value="${jdbc.password}") private String password;//密码
	 */	
	@RequestMapping("/getMsg")
	public String getMsg() {
		
		return "服务器返回数据"+driver+"|"+name+"|"+password;
				
	}
}
