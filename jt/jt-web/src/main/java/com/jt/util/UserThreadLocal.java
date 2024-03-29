package com.jt.util;

import com.jt.pojo.User;

public class UserThreadLocal {
	/**
	 * 如果需要存储单个对象,写对象类型
	 * 如果需要保存多个数据 使用map集合
	 */
	private static ThreadLocal<User> thread=new ThreadLocal();
	public static void set(User user) {
		thread.set(user);
	}
	public  static User get() {
		
		return thread.get();
	}
	
	public static void remove() {
		thread.remove();
	}
}
