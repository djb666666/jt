package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean findCheckUser(String param, Integer type) {
		//1.需要将type类型转化为具体字段
		/*
		 * switch(type) { case 1 : case 2: case 3: }
		 * 三木运算
		 */
		String colum= (type==1)? "username":(type==2?"phone":"email");
		QueryWrapper<User> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq(colum,param);
		User user=userMapper.selectOne(queryWrapper);
		//User user2=userMapper.findUserById(colum,param);
		return user==null?false:true;
	}
	
	
	
}
