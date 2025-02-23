package com.example.webapp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {  //jpa repository
    public Employee findByEmail(String email); //find employee using email
}
