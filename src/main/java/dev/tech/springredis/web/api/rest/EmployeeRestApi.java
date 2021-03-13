package dev.tech.springredis.web.api.rest;

import dev.tech.springredis.domain.Employee;
import dev.tech.springredis.repository.redis.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Employee addEmployee(final @RequestBody Employee employee){
        this.employeeRepository.save(employee);
        return employee;
    }
    @GetMapping
    public List<Employee> getAllEmployees(){
        return this.employeeRepository.findAll();
    }
    @GetMapping("/{id}")
    public Employee getEmployeeById(final @PathVariable("id") Integer id){
        return this.employeeRepository.findById(id);
    }

    @PutMapping
    public void updateEmployee(final @RequestBody Employee employee){
        this.employeeRepository.update(employee);

    }
    @DeleteMapping("/{id}")
    public  void deleteEmployeeById(final @PathVariable("id") Integer id){
        this.employeeRepository.deleteById(id);
    }
}