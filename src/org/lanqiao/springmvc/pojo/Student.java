package org.lanqiao.springmvc.pojo;

import java.util.Date;

public class Student {
	
	private String sno;		//学生学号
	private String sname;	//学生姓名
	private Date sage;	//学生出生日期
	private String ssex;	//学生性别
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getSname() {
		return sname;
	}
	public Date getSage() {
		return sage;
	}
	public void setSage(Date sage) {
		this.sage = sage;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	
	@Override
	public String toString() {
		return "Student [sno=" + sno + ", sname=" + sname + ", ssex=" + ssex
				+ "]";
	}
	public String getSsex() {
		return ssex;
	}
	public void setSsex(String ssex) {
		this.ssex = ssex;
	}
	
}
