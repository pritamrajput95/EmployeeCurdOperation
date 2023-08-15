package com.emp.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.emp.response.EmployeeResponse;
import com.emp.response.ErrorResponse;
import com.emp.service.EmployeeService;

@CrossOrigin(origins ="http://localhost:3000/")
@RestController
@RequestMapping("/emp")
public class EmployeeController {

    private static final Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());

	@Autowired
	EmployeeService employeeService;
	
	@GetMapping(value="/employees")
	public List<Employee> getAllEmployees(){
        LOGGER.info("get all method call");
		return this.employeeService.getAllEmployees();
	}
	
	
	@GetMapping(value="/search/{id}",produces = {"application/json" })
	public Employee getEmployeebyId (@PathVariable("id") long id) {
        LOGGER.info("get method call by id");

		return this.employeeService.getEmployeebyId(id);
	}
	
	@PostMapping(value="/add")
	public Employee addEmployee(@RequestBody Employee employee) {
		LOGGER.info("add method call");
		return this.employeeService.addEmployee(employee);
	}
	
	@PutMapping(value="/update/{id}")
	public EmployeeResponse updateEmployee(@PathVariable("id") long id , @RequestBody Employee employee) {
		LOGGER.info("update method call");
	       
		 return this.employeeService.updateEmployee(id,employee);
	}
	
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") long id) {
		LOGGER.info("delete method call by id");
        
        Employee empToDelete = employeeService.getEmployeebyId(id);
        if (empToDelete == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorMessage("Employee not found with ID: " + id);

              return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

       

       EmployeeResponse employeeResponse=employeeService.deleteEmployee(id);
       
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }	
        
	
}
