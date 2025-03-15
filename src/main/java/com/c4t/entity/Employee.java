package com.c4t.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;


@Data

@Document(collection = "employee")
public class Employee {

    @Id
    String id;
    String name;
    int salary;
    String designation;
    String doj;

}
