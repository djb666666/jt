package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

/**
 * 定义用户拦截器,实现用户权限判断
 * @author Administrator
 * spring4 必须重写拦截器方法
 * 5不需要全部重写
 */
@Component
public class UserInterceptor implements HandlerInterceptor{
	@Autowired
	private JedisCluster jedisCluster;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		String key =null;
		if(cookies.length>0) {
			for(Cookie cookie:cookies) {
				if("JT_TICKET".equals(cookie.getName())) {
					key=cookie.getValue();
					break;
				}
			}
		}
		if(!StringUtils.isEmpty(key)) {
			String userJson=jedisCluster.get(key);
			if(!StringUtils.isEmpty(userJson)) {
				User user=ObjectMapperUtil.toOBject(userJson, User.class);
				UserThreadLocal.set(user);
				return true;
			}
		}
		response.sendRedirect("/user/login.html");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	/**
	 * 删除ThreadLocal防止内存泄漏
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		UserThreadLocal.remove();
	}
	
	
	
	
	
	/**
	 * 重写prehandle方法检验用户是否登录
	 * 
	 * boolean:true 请求放行
	 * 		   false:请求拦截
	 * 			一般会有重定向
	 * 用户登录
	 */
	/*
	 * @Override public boolean preHandle(HttpServletRequest request,
	 * HttpServletResponse response, Object handler) throws Exception { Cookie[]
	 * cookies = request.getCookies(); String key=null; if(cookies.length>0) {
	 * for(Cookie cookie:cookies) { if("JT_TICKET".equals(cookie.getName())) {
	 * key=cookie.getValue(); break; } } } //判断取值是否有效,部位null时校验信息
	 * if(!StringUtils.isEmpty(key)) { String userJson = jedisCluster.get(key);
	 * if(!StringUtils.isEmpty(userJson)) { //方式1:该方式公司初级程序员必会request User user
	 * =ObjectMapperUtil.toOBject(userJson, User.class);
	 * //request.setAttribute("JT_USER", user); UserThreadLocal.set(user); return
	 * true; } } //重定向到用户登录界面 response.sendRedirect("/user/login.html"); return
	 * false; }
	 */
}
