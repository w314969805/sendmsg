package org.lanqiao.springmvc.mapper;

import org.lanqiao.springmvc.pojo.Student;

public interface StudentMapper {
	
	/**
	 * 添加  新学生
	 * @param student
	 * @return
	 */
	int register(Student student);
	
}
