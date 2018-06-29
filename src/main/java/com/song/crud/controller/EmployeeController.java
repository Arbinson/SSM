package com.song.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.mybatis.generator.codegen.ibatis2.dao.templates.GenericCIDAOTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.sun.MagicInstantiator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.song.crud.bean.Employee;
import com.song.crud.bean.Msg;
import com.song.crud.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 单个批量合二为一
	 * 批量删除:1-2-3
	 * 单个删除:1
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{id}",method=RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("ids") String ids){
		//批量删除
				if(ids.contains("-")){
					List<Integer> del_ids = new ArrayList<Integer>();
					String[] str_ids = ids.split("-");
					//组装id的集合
					for (String string : str_ids) {
						del_ids.add(Integer.parseInt(string));
					}
					employeeService.deleteBatch(del_ids);
				}else{
					Integer id = Integer.parseInt(ids);
					employeeService.deleteEmpById(id);
				}
				return Msg.success();
	}
	
	/**
	 * 如果直接发送ajax=PUT形式的请求
	 * 封装的数据
	 * Employee
	 * [empId=1,empName=null,gender=null,email=null,did=null]
	 * 
	 * 问题
	 * 请求体中有数据,但Employee对象封装不上
	 * update tbl_emp     where emp_id = 1;
	 * 
	 * 原因:
	 * tomcat
	 *     将请求体中数据封装为一个map
	 *     request.getParameter("email")将会从这个map中取值
	 *     SpringMVC封装POJO对象的时候,会把POJO对象的每个属性值request.getParameter("email")
	 *     
	 * AJAX发送PUT请求引发的血案
	 *       PUT请求,请求体中的数据request.getParameter("email")都拿不到,
	 *       Tomcat一看是PUT请求,就不会封装请求体中数据为map,只有POST请求的数据才封装请求体为map
	 * org.apache.catalina.connector.Request--parseParameters(3111)
	 * protected String parseBodyMethods="POST"
	 * 
	 * 解决方案
	 * 我们要能支持直接发送PUT之类的请求还需要封装请求体中的数据
	 * 1.配置:HttpPutFormContentFilter
	 * 2.作用:将请求体中的数据解析包装成一个map
	 * 3.request被重新包装:request.getParameter()被重写,将会从自己封装的map中取数据

	 * 员工更新方法
	 * @param employee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
	public Msg saveEmp(Employee employee){
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	/**
	 * 根据ID查询员工
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	public Msg getEmp(@PathVariable("id") Integer id){
		Employee employee=employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	/**
	 * 检查用户名是否可用
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName") String empName){
		//先判断用户名是否合法的表达式
		String  regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if (!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "用户名必须是6~16位数字和字母的组合或2~5位中文");
		}
		
		//数据库用户名重复校验
		boolean hasUser=employeeService.checkUser(empName);
		if (hasUser) {
			return Msg.success();
		}else{
			return Msg.fail().add("va_msg", "用户名不可用");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	public Msg saveEmp(@Valid Employee employee,BindingResult result){
		if (result.hasErrors()) {
			//校验失败,应该返回失败。在模态框中显示校验失败的错误信息
			Map<String, Object>map=new HashMap<String, Object>();
			List <FieldError> errors=result.getFieldErrors();
			for(FieldError fieldError:errors){
				System.out.println("错误的字段名:"+fieldError.getField());
				System.out.println("错误信息:"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorField", map);
		}else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}
	}
    
	@ResponseBody
	@RequestMapping("/emps")
	public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		// 这不是一个分页查询
		// 引入PageHelper分页插件
		// 在查询之前之后需要调用:传入页码以及分页每页大小
		PageHelper.startPage(pn, 5);
		//startPage后紧跟着这个查询就是分页查询
		List<Employee> emps = employeeService.getALL();
		//使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
		//封装了详细的分页信息,包括有我们查询出来的数据,传入连续显示的页数
		PageInfo page=new PageInfo(emps,5);
		return Msg.success().add("pageInfo", page);
	}
	
//	@RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn,Model model) {
		// 这不是一个分页查询
		// 引入PageHelper分页插件
		// 在查询之前之后需要调用:传入页码以及分页每页大小
		PageHelper.startPage(pn, 5);
		//startPage后紧跟着这个查询就是分页查询
		List<Employee> emps = employeeService.getALL();
		//使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
		//封装了详细的分页信息,包括有我们查询出来的数据,传入连续显示的页数
		PageInfo page=new PageInfo(emps,5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
}
