package com.jt.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.User;

public interface UserMapper extends BaseMapper<User>{
	
	/*
	 * @Select ("select * from tb_user where ${colum}=#{param}") User
	 * findUserById(@Param("colum")String colum, @Param("param")String param);
	 */
	
}
