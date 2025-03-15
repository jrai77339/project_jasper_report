package com.c4t.repository;

import com.c4t.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CustomerRespository extends MongoRepository<Customer,String> {
}
