package com.su.controller;

import com.su.dao.DepartmentDao;
import com.su.dao.EmployeeDao;
import com.su.pojo.Department;
import com.su.pojo.Employee;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    @RequestMapping("/emps")
    public String list(
            Model model
    ){
        Collection<Employee> employees = employeeDao.getAll();
        model.addAttribute("emps",employees);
        return "emp/list";
    }

    //跳转到员工的在增加页面
    @GetMapping("/emp")
    public String toAddpage(Model model){
        //查出所有部门的信息
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments",departments);
        return "emp/add";
    }

    @PostMapping("/emp")
    public String addEmp(Employee employee){
        System.out.println(employee);
        employeeDao.save(employee);//调用底层业务方法保存员工信息
        return "redirect:/emps";
    }

    //跳转到员工的修改页面
    @GetMapping("/emp/{id}")
    public String toUpdateEmp(@PathVariable("id") Integer id, Model model){

        System.out.println(id);
        //查出原来的数据
        Employee employee = employeeDao.getEmployeeById(id);
        System.out.println(employee);
        model.addAttribute("emp",employee);
        //查出所有部门的信息
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments",departments);
        return "emp/update";
    }

    @PostMapping("/updateEmp")
    public String updateEmp(Employee employee){
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    //删除员工
    @RequestMapping("/delemp/{id}")
    public String deleteEmp(@PathVariable Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }


}
