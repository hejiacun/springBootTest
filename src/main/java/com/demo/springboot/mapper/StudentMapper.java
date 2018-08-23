package com.demo.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.demo.springboot.entity.Student;

//@Repository
//@Mapper
public interface StudentMapper {
	List<Student> listStudents();
}
