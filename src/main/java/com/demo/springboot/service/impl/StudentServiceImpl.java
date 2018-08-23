package com.demo.springboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.springboot.entity.Student;
import com.demo.springboot.mapper.StudentMapper;
import com.demo.springboot.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentMapper studentMapper;

	@Override
	public List<Student> listStudents() {
		return studentMapper.listStudents();
	}

}
