package com.camper.yantarniytelegrambot.repos;

import com.camper.yantarniytelegrambot.entity.Employee;
import com.camper.yantarniytelegrambot.entity.EmployeeType;
import com.camper.yantarniytelegrambot.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeTypeRepo extends JpaRepository<EmployeeType,Integer> {
    EmployeeType findEmployeesTypeByType(String type);
}
