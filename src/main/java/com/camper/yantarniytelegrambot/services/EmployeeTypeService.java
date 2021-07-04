package com.camper.yantarniytelegrambot.services;

import com.camper.yantarniytelegrambot.entity.EmployeeType;
import com.camper.yantarniytelegrambot.repos.EmployeeRepo;
import com.camper.yantarniytelegrambot.repos.EmployeeTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeTypeService {
    private EmployeeTypeRepo employeeTypeRepo;

    @Transactional(readOnly = true)
    public EmployeeType findEmployeeTypeByType(String type) {
        return employeeTypeRepo.findEmployeesTypeByType(type);
    }

    @Autowired
    public void setEmployeeTypeRepo(EmployeeTypeRepo employeeTypeRepo) {
        this.employeeTypeRepo = employeeTypeRepo;
    }
}
