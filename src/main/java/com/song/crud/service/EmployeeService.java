package com.song.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.song.crud.bean.Employee;
import com.song.crud.bean.EmployeeExample;
import com.song.crud.bean.EmployeeExample.Criteria;
import com.song.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeMapper employeeMapper;
	
	/**
	 *  查询所有员工
	 * @return
	 */
	public List<Employee> getALL() {
		
		return employeeMapper.selectByExampleWithDept(null);
	}

	public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);		
	}

	/**
	 * 
	 * 检验用户名是否可用
	 * @param empName
	 * @return true:代表当前姓名可用  false:不可用
	 */
	public boolean checkUser(String empName) {
		EmployeeExample employeeExample=new EmployeeExample();
		Criteria criteria=employeeExample.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count=employeeMapper.countByExample(employeeExample);
		return count==0;
	}
    
	/**
	 * 按照id查询员工
	 * @param id
	 * @return
	 */
	public Employee getEmp(Integer id) {
		Employee employee=employeeMapper.selectByPrimaryKey(id);
		return employee;
	}

	/**
	 * 员工更新
	 * @param employee
	 */
	public void updateEmp(Employee employee) {
       employeeMapper.updateByPrimaryKeySelective(employee); 		
	}

	/**
	 * 删除员工
	 * @param id
	 */
	public void deleteEmpById(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
		
	}

	public void deleteBatch(List<Integer> ids) {
		// TODO Auto-generated method stub
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		//delete from xxx where emp_id in(1,2,3)
		criteria.andEmpIdIn(ids);
		employeeMapper.deleteByExample(example);
	}

}
