package com.demo.springboot.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:constants.properties")//自定义配置资源文件
@ConfigurationProperties(prefix = "my1")
public class MyProperties {

	private int age;
	private String name;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "MyProperties [age=" + age + ", name=" + name + "]";
	}
	
}
