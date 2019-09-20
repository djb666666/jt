package com.jt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
/**
 * 问题:tomcat运行服务器中 是否需要安装lombok插件
 *答案:不需要安装.
 *原因:因为tomcat服务器运行时加载.class文件信息所以服务器无需安装
 *但是 如果程序在另外的编辑器中编辑,则需要安装lombok
 * @author tarena
 *
 */

//实体对象的属性类型 统一使用包装类型
@Data	//自动生成get/set/toString
@NoArgsConstructor	//无参构造
@AllArgsConstructor	//有参构造
@Accessors(chain = true) //连续操作对象
public class User {
	private Integer id;
	private String name;
	private Integer age;
	private String sex;
}
