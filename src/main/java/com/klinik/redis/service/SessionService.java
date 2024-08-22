package com.klinik.redis.service;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klinik.redis.model.Session;
import com.klinik.redis.repository.SessionRepository;

@Service
public class SessionService {
    
    @Autowired
    private SessionRepository studentRepository;

    
    public Iterator<Session> getAllSession(){
      return StreamSupport.stream( studentRepository.findAll().spliterator(), false)
                            .filter( f ->  f != null )
                            .iterator();
    }

    public Session getSessiontById( String id ){
        return studentRepository.findById( id )
                                .orElseThrow( () -> new NoSuchElementException("Сессия не найдена"));
    }

    public Session getAddSession( Session student ){
        return studentRepository.save( student );
    }

    public void delAllSession(){
        studentRepository.deleteAll();
    }

}
