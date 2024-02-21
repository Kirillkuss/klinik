package com.klinik.redis.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.klinik.redis.model.Person;
import com.klinik.redis.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor
public class PersonService {

    private RedisTemplate redisTemplate;

    private PersonService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    private PersonRepository personRepository;

    public void savePerson( Person person ){
        person.setId("1");
        redisTemplate.opsForValue()
                     .set( "1", person );
    }

    public Person findByIdPerson( String id ){
        Person person = (Person) redisTemplate.opsForValue().get( id );
        if( person == null ) throw new NoSuchElementException("Person not found with id = " + id );
        return person;
    }

    @SuppressWarnings("unchecked")
    public List<Person> findAllPerson(){
        
       /** */ List<Person> response = new ArrayList<>();
        for ( Object key : redisTemplate.keys( "*" )) {
            //redisTemplate.delete(key);
            response.add( (Person)redisTemplate.opsForValue()
                                       .get( key ));
        }
        /**Iterator<Person> iterator = personRepository.findAll().iterator();
        while (iterator.hasNext()) {
            response.add( iterator.next() );
        };*/
       // System.out.println( "repos >>> " + personRepository.findAll().iterator().next());
        //System.out.println( "KEYS >>> " + redisTemplate.opsForValue().get(null, 0, 0));
        return response;
    }

    

}
