package com.klinik.redis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.redis.model.Person;
import com.klinik.redis.rest.RestPerson;
import com.klinik.redis.service.PersonService;

@RestController
public class PersonController implements RestPerson {

    @Autowired
    private PersonService personService;

    @Override
    public ResponseEntity<List<Person>> getAllPerson() {
        return new ResponseEntity<List<Person>>( personService.findAllPerson(), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<Person> findByIdPerson(String id) {
        return new ResponseEntity<Person>( personService.findByIdPerson( id ) , HttpStatus.OK );
    }

    @Override
    public ResponseEntity<Void>  addPerson(Person person) {
        personService.savePerson( person);
        return new ResponseEntity<>( HttpStatus.CREATED );
    }
    
}
