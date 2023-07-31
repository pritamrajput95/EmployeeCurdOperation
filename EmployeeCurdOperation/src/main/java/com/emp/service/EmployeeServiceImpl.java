package com.emp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.entity.Employee;
import com.emp.exception.NoEmpFoundException;
import com.emp.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> getAllEmployees() {
		
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeebyId(long id) {
		return employeeRepository.findById(id).orElseThrow(()->new NoEmpFoundException("No Emp found with id : "+id));
	}

	@Override
	public Employee addEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		
		long empId=employee.getId();
		
		if(!employeeRepository.existsById(empId)) {
			throw new NoEmpFoundException("No emp found with id : "+empId+" record cannot update");
		}
		
		return employeeRepository.save(employee);
	}

	@Override
	@Transactional
	public void deleteEmployee(long id) {
        //check emp exist or not 
		if(!employeeRepository.existsById(id)) {
			throw new NoEmpFoundException("No Emp found for delete with id : "+id);
		}
		employeeRepository.deleteById(id);
		
	}

}
