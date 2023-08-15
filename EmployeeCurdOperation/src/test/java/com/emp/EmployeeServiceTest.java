package com.emp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import com.emp.entity.Employee;
import com.emp.repository.EmployeeRepository;
import com.emp.service.EmployeeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee();
        employee.setFirstname("John");
        employee.setLastname("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setAge(30);

        Employee savedEmployee = employeeRepository.save(employee);
        assertNotNull(savedEmployee.getId());
    }

    @Test
    public void testSaveEmployeeWithInvalidData() {
        Employee employee = new Employee(); // Empty employee object with no data

        // Try to save the empty employee, it should throw ConstraintViolationException
        assertThrows(ConstraintViolationException.class, () -> employeeRepository.save(employee));
    }

    @Test
    public void testSaveEmployeeWithInvalidEmail() {
        Employee employee = new Employee();
        employee.setFirstname("Jane");
        employee.setLastname("Smith");
        employee.setEmail("invalid_email"); // Invalid email format

        // Try to save the employee with invalid email, it should throw ConstraintViolationException
        assertThrows(ConstraintViolationException.class, () -> employeeRepository.save(employee));
    }

    @Test
    public void testFindAllEmployees() {
        Employee employee1 = new Employee(0,"John", "Doe", "john.doe@example.com", 30);
        Employee employee2 = new Employee(0,"Jane", "Smith", "jane.smith@example.com", 25);
        employeeRepository.saveAll(Arrays.asList(employee1, employee2));

        List<Employee> employees = employeeRepository.findAll();
        assertEquals(2, employees.size());
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee(0,"John", "Doe", "john.doe@example.com", 30);
        employee = employeeRepository.save(employee);

        employeeRepository.deleteById(employee.getId());

        assertFalse(employeeRepository.existsById(employee.getId()));
    }

    @Test
    public void testSaveEmployeeWithDuplicateEmail() {
        Employee employee1 = new Employee(0,"John", "Doe", "john.doe@example.com", 30);
        Employee employee2 = new Employee(0,"Jane", "Smith", "john.doe@example.com", 25); // Same email as employee1

        employeeRepository.save(employee1);

        // Try to save the second employee with the same email, it should throw DataIntegrityViolationException
        assertThrows(DataIntegrityViolationException.class, () -> employeeRepository.save(employee2));
    }
}
