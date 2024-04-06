package com.assignement.EmployeeManagmentSystem.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignement.EmployeeManagmentSystem.models.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeesRepository extends JpaRepository<Employee, Integer>{

    // Don't need to implement this interface because Spring JPA will implement this for us
    
    // We can use this interface to read and write the employees from the database
}
