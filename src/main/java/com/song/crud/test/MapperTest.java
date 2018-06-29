package com.song.crud.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.song.crud.bean.Department;
import com.song.crud.bean.Employee;
import com.song.crud.dao.DepartmentMapper;
import com.song.crud.dao.EmployeeMapper;

@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MapperTest {

	@Autowired
	DepartmentMapper departmentMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Test
	public void testCRUD(){
		System.out.println(departmentMapper);
		
		//插入几个部门
//		departmentMapper.insertSelective(new Department(null, "Testing"));
//		departmentMapper.insertSelective(new Department(null, "Research"));
//		departmentMapper.insertSelective(new Department(null, "Selling"));
		
		List<Employee> employees=employeeMapper.selectByExampleWithDept(null);
		for(Employee employee:employees){
			System.out.println(employee);
		}
	}
}
