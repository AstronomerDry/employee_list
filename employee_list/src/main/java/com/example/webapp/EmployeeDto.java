package com.example.webapp;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class EmployeeDto {
    @NotEmpty(message="The First Name is required")
    private String first_name;

    @NotEmpty(message = "The Last Name is required")
    private String last_name;

    @NotEmpty(message = "The Email is required")
    @Email
    private String email;

    private String address;

    @NotEmpty(message = "The department is required")
    private String department;

    private int salary;

    @NotEmpty(message = "The status is required")
    private String status;


}
