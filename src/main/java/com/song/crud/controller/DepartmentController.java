package com.song.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.song.crud.bean.Department;
import com.song.crud.bean.Msg;
import com.song.crud.service.DepartmentService;

@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping("/depts")
	@ResponseBody
	public Msg getDepts() {
		//查处的所有部门信息
		List<Department> list=departmentService.getDepts();
		return Msg.success().add("depts", list);
	}
}
