package org.lanqiao.springmvc.service.impl;

import org.lanqiao.springmvc.mapper.StudentMapper;
import org.lanqiao.springmvc.pojo.Student;
import org.lanqiao.springmvc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	StudentMapper studentMapper;

	@Override
	public boolean register(Student student) {
		// TODO Auto-generated method stub
		boolean flag=false;
		int result=studentMapper.register(student);
		if(result==1){
			flag=true;
		}
		return flag;
	}
	
}
