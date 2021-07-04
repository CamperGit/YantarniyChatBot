package com.camper.yantarniytelegrambot.services;

import com.camper.yantarniytelegrambot.entity.Employee;
import com.camper.yantarniytelegrambot.entity.Location;
import com.camper.yantarniytelegrambot.repos.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private EmployeeRepo employeeRepo;

    /**
     * retrieve an entity from the database if it exists or else put employee in database
     * @param employee an example entity to retrieve from the database or a candidate to add to the database
     * @return employee from database
     */
    public Employee putIfAbsent(Employee employee) {
        return employeeRepo.findOne(Example.of(employee)).orElseGet(()-> employeeRepo.save(employee));
    }

    @Transactional(readOnly = true)
    public List<Employee> findEmployeesByLocation(Location location) {
        return employeeRepo.findAllByLocation(location);
    }

    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Autowired
    public void setEmployeeRepo(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
}
