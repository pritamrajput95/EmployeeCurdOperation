package com.emp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emp.entity.Employee;
import com.emp.exception.NoEmpFoundException;
import com.emp.service.EmployeeService;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@GetMapping(value="/all")
	public List<Employee> getAllEmployees(){
		System.out.println("get all method call");
		return this.employeeService.getAllEmployees();
	}
	
	
	@GetMapping(value="/search",produces = {"application/json" })
	public Employee getEmployeebyId (@RequestParam("id") long id) {
		 System.out.println("get method call by id");
		return this.employeeService.getEmployeebyId(id);
	}
	
	@PostMapping(value="/add")
	public Employee addEmployee(@RequestBody Employee employee) {
		 System.out.println("add method call");
		return this.employeeService.addEmployee(employee);
	}
	
	@PutMapping(value="/update")
	public Employee updateEmployee(@RequestBody Employee updateEmployee) {
	       System.out.println("update method call");
	       
	       //step1: get employee by id
	       Employee emp= this.employeeService.getEmployeebyId(updateEmployee.getId());
	       
	       //step2: check record is available of not
	       if(emp==null) {
	           throw new NoEmpFoundException("Employee not found with ID: " + updateEmployee.getId());
	       }
	       
	       else {
	       //based on id update below details
	       emp.setFirstname(updateEmployee.getFirstname());
	       emp.setLastname(updateEmployee.getLastname());
	       emp.setEmail(updateEmployee.getEmail());
	       emp.setAge(updateEmployee.getAge());
	       }
		 return this.employeeService.updateEmployee(emp);
	}
	
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id) {
        System.out.println("delete method call by id");
        
        Employee empToDelete = employeeService.getEmployeebyId(id);
        if (empToDelete == null) {
            return new ResponseEntity<>("employee not found.", HttpStatus.NOT_FOUND);
        }

       employeeService.deleteEmployee(id);
        return new ResponseEntity<>("employee deleted successfully.", HttpStatus.OK);
    }	
        
	
}
