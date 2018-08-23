package com.demo.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springboot.entity.MyProperties;
import com.demo.springboot.entity.Student;

@RestController
@RequestMapping("/hello")
public class HelloController {
	private static final Logger log = LoggerFactory.getLogger(HelloController.class);
	
	private final JdbcTemplate jdbcTemplate;
	private final MyProperties myProperties;
	
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
}
