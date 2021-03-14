//package dev.tech.springredis.web.api.rest.using_redis;
//
//import dev.tech.springredis.domain.Employee;
//import dev.tech.springredis.repository.redis.EmployeeRedisRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/employees")
//public class EmployeeRestApi {
//    private final Logger logger = LoggerFactory.getLogger(EmployeeRestApi.class);
//    private final EmployeeRedisRepository employeeRedisRepository;
//
//    @Autowired
//    public EmployeeRestApi(EmployeeRedisRepository employeeRepository) {
//        this.employeeRedisRepository = employeeRepository;
//    }
//
//    @PostMapping
//    public Employee addEmployee(final @RequestBody Employee employee){
//        this.employeeRedisRepository.save(employee);
//        return employee;
//    }
//    @GetMapping
//    public List<Employee> getAllEmployees(){
//        return this.employeeRedisRepository.findAll();
//    }
//
//    @GetMapping("/{id}")
//    @Cacheable(value = "employees", key = "#id")
//    public Employee getEmployeeById(final @PathVariable("id") Integer id){
//        this.logger.info("Fetching employee data with employee ID number: " + id + " from database.");
//        return this.employeeRedisRepository.findById(id);
//    }
//
//    @PutMapping("/{id}")
//    @CachePut(value = "employees", key = "#id")
//    public void updateEmployee(final @PathVariable("id") Integer id, final @RequestBody Employee employee){
//        Employee employeeToUpdate = employeeRedisRepository.findById(id);
//        employeeToUpdate.setName(employee.getName());
//        this.employeeRedisRepository.update(employeeToUpdate);
//    }
//
//    @DeleteMapping("/{id}")
//    @CacheEvict(value = "employees", allEntries = true)
//    public  void deleteEmployeeById(final @PathVariable("id") Integer id){
//        this.employeeRedisRepository.deleteById(id);
//    }
//}