package org.lanqiao.springmvc.service;

import org.lanqiao.springmvc.pojo.Student;

public interface StudentService {
	
	/**
	 * 添加  新学生
	 * @param student
	 * @return
	 */
	boolean register(Student student);
	
}
