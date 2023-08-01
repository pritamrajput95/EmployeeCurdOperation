package com.emp.service;

import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.entity.Employee;
import com.emp.exception.NoEmpFoundException;
import com.emp.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class.getName());

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        LOGGER.info("Getting all employees");
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeebyId(long id) {
        LOGGER.info("Getting employee with id: " + id);
        return employeeRepository.findById(id).orElseThrow(() -> new NoEmpFoundException("No Emp found with id: " + id));
    }

    @Override
    public Employee addEmployee(Employee employee) {
        LOGGER.info("Adding new employee");
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        long empId = employee.getId();

        if (!employeeRepository.existsById(empId)) {
            throw new NoEmpFoundException("No emp found with id: " + empId + " record cannot update");
        }

        LOGGER.info("Updating employee with id: " + empId);
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(long id) {
        LOGGER.info("Deleting employee with id: " + id);
        // check emp exist or not
        if (!employeeRepository.existsById(id)) {
            throw new NoEmpFoundException("No Emp found for delete with id: " + id);
        }
        employeeRepository.deleteById(id);
    }
}
