package com.jt.ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache_Find {
	String key() default "";//用户可以不写,如果
	int seconds() default 0;//0表示用户设置该数据不需要超时时间，如果不等于0，则说明用户自己定义了超时时间
}	
