package com.klinik.redis.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    public ResponseEntity<Iterator<Session>> getAllStudents() {
        return ResponseEntity.ok( sessionService.getAllSession() );
    }

    @Override
    public ResponseEntity<Session> getMethodName( String id ) {
        return ResponseEntity.ok( sessionService.getSessiontById( id ));
    }

    @Override
    public ResponseEntity<Session> postMethodName( Session session ) {
        return ResponseEntity.ok( sessionService.getAddSession( session ));
    }

    @Override
    public ResponseEntity<Iterable<Doctor>> getAllDoctors() {
        return  ResponseEntity.ok( doctorRepositoryRedis.findAll());
    }

}