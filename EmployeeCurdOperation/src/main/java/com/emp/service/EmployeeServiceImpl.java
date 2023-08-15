package com.emp.service;

import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.entity.Employee;
import com.emp.exception.NoEmpFoundException;
import com.emp.repository.EmployeeRepository;
import com.emp.response.EmployeeResponse;

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
        //add debbug log to track db query execution time
        long startTime=System.currentTimeMillis();
        Employee emp=employeeRepository.findById(id).orElseThrow(() -> new NoEmpFoundException("No Emp found with id: " + id));
        long endTime=System.currentTimeMillis();
        
        long executiontime=endTime-startTime;
        LOGGER.info("Query execution time: "+executiontime+"ms");
        return emp; 
    }

    @Override
    public Employee addEmployee(Employee employee) {
        LOGGER.info("Adding new employee");
        
        LOGGER.info("Employee details received: " + employee);
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponse updateEmployee(long id,Employee employee) {
       

         Employee empDetails=employeeRepository.findById(id).orElseThrow(()-> 
            new NoEmpFoundException("No emp found with id: " + id + " record cannot update"));
        
         empDetails.setFirstname(employee.getFirstname());
         empDetails.setLastname(employee.getLastname());
         empDetails.setEmail(employee.getEmail());
         empDetails.setAge(employee.getAge());
        
         Employee updatEmpDetails=employeeRepository.save(empDetails);
         
        EmployeeResponse response= new EmployeeResponse();
        response.setMessage("employee updated successfully...!!!");
        response.setEmployee(updatEmpDetails);
        

        return response;
    }

    @Override
    @Transactional
    public EmployeeResponse deleteEmployee(long id) {
        LOGGER.info("Deleting employee with id: " + id);
        // check emp exist or not
  
        Employee empToDelete = employeeRepository.findById(id)
                .orElseThrow(() -> new NoEmpFoundException("No Emp found for delete with id: " + id));

        employeeRepository.deleteById(id);
        EmployeeResponse response= new EmployeeResponse();
        response.setMessage("employee deleted successfully...!!!");
        response.setEmployee(empToDelete);
        
        return response;
    }
}
