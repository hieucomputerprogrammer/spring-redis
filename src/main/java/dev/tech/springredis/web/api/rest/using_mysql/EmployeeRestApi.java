package dev.tech.springredis.web.api.rest.using_mysql;

import dev.tech.springredis.domain.Employee;
import dev.tech.springredis.exceptions.ResourceNotFoundException;
import dev.tech.springredis.repository.springdatajpa.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestApi {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeRestApi(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return this.employeeRepository.save(employee);
    }


    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(this.employeeRepository.findAll());
    }

    @GetMapping("/{id}")
    @Cacheable(value = "employees",key = "#id")
    public Employee findEmployeeById(@PathVariable(value = "id") Integer id) {
        System.out.println("Fetching employee data from MySQL database: " + id + ".");
        return this.employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with ID number: " + id + " is not found in MySQL database."));

    }


    @PutMapping("/{id}")
    @CachePut(value = "employees",key = "#id")
    public Employee updateEmployee(@PathVariable(value = "id") Integer id,
                                   @RequestBody Employee employeeDetails) {
        Employee employee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with ID number: " + id + " is not found in MySQL database."));
        employee.setName(employeeDetails.getName());
        final Employee updatedEmployee = this.employeeRepository.save(employee);
        return updatedEmployee;

    }


    @DeleteMapping("/{id}")
    @CacheEvict(value = "employees", allEntries = true)
    public void deleteEmployee(@PathVariable(value = "id") Integer id) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with ID number: " + id + " is not found in MySQL database."));
        this.employeeRepository.delete(employee);
    }
}