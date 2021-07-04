package com.camper.yantarniytelegrambot.repos;

import com.camper.yantarniytelegrambot.entity.Employee;
import com.camper.yantarniytelegrambot.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
    List<Employee> findAllByLocation(Location location);
}
