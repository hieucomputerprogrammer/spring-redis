package dev.tech.springredis.repository.redis;

import dev.tech.springredis.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {
    private final HashOperations hashOperations;
    private RedisTemplate redisTemplate;

    @Autowired
    public EmployeeRepository(RedisTemplate redisTemplate) {
        this.hashOperations = this.redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
    }

    public void save(Employee employee) {
        this.hashOperations.put("EMPLOYEE", employee.getId(), employee);
    }

    public List<Employee> findAll() {
        return this.hashOperations.values("EMPLOYEE");
    }

    public Employee findById(Integer id) {
        return (Employee) this.hashOperations.get("EMPLOYEE", id);
    }

    public void update(Employee employee) {
        this.save(employee);
    }

    public void deleteById(Integer id) {
        this.hashOperations.delete("EMPLOYEE", id);
    }
}