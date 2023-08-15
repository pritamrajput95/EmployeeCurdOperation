package com.emp.response;

import com.emp.entity.Employee;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class EmployeeResponse {
	
	private String message;
    private Employee employee;
}
