package com.demo.springboot.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springboot.entity.MyProperties;
import com.demo.springboot.entity.Student;
import com.demo.springboot.service.StudentService;

@RestController
@RequestMapping("/hello")
public class HelloController {
	private static final Logger log = LoggerFactory.getLogger(HelloController.class);
	
	private final JdbcTemplate jdbcTemplate;
	private final MyProperties myProperties;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private StudentService studentService;
	
	@Autowired
	public HelloController(MyProperties myProperties, JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.myProperties = myProperties;
	}
	
	@GetMapping()
	public String hello() {
		return "Greeting from Spring Boot!";
	}
	
	@GetMapping("/1")
	public Object myProperties() {
		log.info("=================================");
		log.info(myProperties.toString());
		log.info("=================================");
		return myProperties;
	}
	
	@GetMapping("/2")
	public Object jdbc() {
		String sql = "SELECT * FROM `Student`";
		return jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<>(Student.class));
	}
	
	@GetMapping("/3")
	public Object redisTest() {
		Set<String> keys = stringRedisTemplate.keys("*");
		return keys;
	}
	
	@GetMapping("/4")
	public Object redisTest4() {
//		Set<String> keys = stringRedisTemplate.keys("*");
		return stringRedisTemplate.opsForHash().entries("hjc");
	}
	
	@GetMapping("/5")
	public Object redisTest5() {
		stringRedisTemplate.opsForValue().set("aaa", "111");
		return stringRedisTemplate.opsForValue().get("aaa");
	}
	@GetMapping("/6")
	public Object redisTest6() {
		Student student = studentService.getStudentById(1);
		stringRedisTemplate.opsForHash().put("student", "id", String.valueOf(student.getId()));
		stringRedisTemplate.opsForHash().put("student", "name", student.getName());
		stringRedisTemplate.opsForHash().put("student", "phone", String.valueOf(student.getPhone()));
		return stringRedisTemplate.opsForHash().get("student", "name");
	}
}
