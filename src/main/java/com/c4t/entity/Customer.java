package com.c4t.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data


public class Customer {

    @Id
    String id;
    String name;
    String email;
    String phone_no;
}
