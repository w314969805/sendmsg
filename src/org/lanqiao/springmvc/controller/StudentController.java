package org.lanqiao.springmvc.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lanqiao.springmvc.pojo.Student;
import org.lanqiao.springmvc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@RequestMapping("/register")
	public ModelAndView check(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		// 防止数据传递乱码
		response.setContentType("text/html;chartset=utf-8");

		String sno = request.getParameter("sno");
		String sname = request.getParameter("sname");
		String sage = request.getParameter("sage");
		String ssex = request.getParameter("ssex");

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(sage);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Student student = new Student();
		student.setSno(sno);
		student.setSname(sname);
		student.setSage(date);
		student.setSsex(ssex);

		boolean result = studentService.register(student);
		if (result) {
			mv.setViewName("registerOk");
		}else{
			try {
				response.getWriter().println("<script>alert('学号已存在');window.location.href='register.jsp'</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mv;
	}

}
