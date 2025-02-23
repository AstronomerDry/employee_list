package com.example.webapp;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(generator = "emp-id-generator")
    @GenericGenerator(name = "emp-id-generator", strategy = "com.example.webapp.CustomIdGenerator")
    private String id;
    private String first_name;
    private String last_name;
    @Column(unique = true,nullable = false)
    private String email;
    private String department;
    private int salary;
    private String address;
    private String status;
    private Date createdAt;
}
