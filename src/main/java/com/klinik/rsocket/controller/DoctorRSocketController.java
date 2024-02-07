package com.klinik.rsocket.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.klinik.entity.Doctor;
import com.klinik.repositories.DoctorRerository;
import com.klinik.response.BaseResponse;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Server
/** 
@Controller
@AllArgsConstructor
public class DoctorRSocketController {

    private final DoctorRerository doctorRerository;

    @MessageExceptionHandler
    public Mono<BaseResponse> handleException( Exception ex ){
        return Mono.just(  BaseResponse.error( HttpStatus.BAD_REQUEST.value(), ex) );
    }

    @MessageMapping( "doctorId" )
    public Mono<Doctor> currentDoctor( Long id ){
        return Mono.just(doctorRerository.findById( id )
                                         .orElseThrow( () -> new  NoSuchElementException( "Доктора с таким ИД нет")));
    }

    @MessageMapping("doctorFIO")
    public Flux<List<Doctor>> findFIODoctor( String fio ){
        return Flux.just( doctorRerository.findDoctorByFIO( fio ) );
    }
    

} */
