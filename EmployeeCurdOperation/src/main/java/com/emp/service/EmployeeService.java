package com.emp.service;

import java.util.List;

import com.emp.entity.Employee;
import com.emp.response.EmployeeResponse;

public interface EmployeeService {

	List<Employee> getAllEmployees();

	Employee getEmployeebyId(long id);

	Employee addEmployee(Employee employee);

	EmployeeResponse updateEmployee(long id, Employee employee);

	EmployeeResponse deleteEmployee(long id);

}
