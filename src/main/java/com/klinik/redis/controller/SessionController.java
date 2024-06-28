package com.klinik.redis.controller;

import java.util.Iterator;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.redis.model.Doctor;
import com.klinik.redis.model.Session;
import com.klinik.redis.repository.DoctorRepositoryRedis;
import com.klinik.redis.rest.RestSession;
import com.klinik.redis.service.SessionService;


@RestController
public class SessionController implements RestSession{
    
    @Autowired
    SessionService sessionService;

    @Autowired
    DoctorRepositoryRedis doctorRepositoryRedis;

    @Override
    public ResponseEntity<Iterator<Session>> getAllSessions() {
        /**
         * Iterator<Session> iteratorSession = sessionService.getAllSession();
         * Stream.generate( () ->  null ).takeWhile( x -> iteratorSession.hasNext() ).map( s -> iteratorSession.next() ).filter( t -> t != null ).iterator();
         */
        Iterable<Session> iterableSession = () ->  sessionService.getAllSession();
        return ResponseEntity.ok( StreamSupport.stream( iterableSession.spliterator(), false).filter( s -> s != null ).iterator());
    }

    @Override
    public ResponseEntity<Session> getFindByIdSession( String id ) {
        return ResponseEntity.ok( sessionService.getSessiontById( id ));
    }

    @Override
    public ResponseEntity<Session> getAddSession( Session session ) {
        return ResponseEntity.ok( sessionService.getAddSession( session ));
    }

    @Override
    public ResponseEntity<Iterable<Doctor>> getAllDoctors() {
        return  ResponseEntity.ok( StreamSupport.stream( doctorRepositoryRedis.findAll()
                                                                              .spliterator(), false)
                                                                              .filter( s -> s != null )
                                                                              .toList() );
    }

}
