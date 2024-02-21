package com.klinik.redis.repository;

import org.springframework.data.repository.CrudRepository;
import com.klinik.redis.model.Person;

public interface PersonRepository extends CrudRepository<Person, String> {  
}
