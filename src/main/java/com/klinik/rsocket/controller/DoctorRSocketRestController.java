package com.klinik.rsocket.controller;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

//Klient
/** 
@RestController
@AllArgsConstructor
public class DoctorRSocketRestController {

    private final RSocketRequester rSocketRequester;
    
    @GetMapping( value = "/current/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public Publisher<String> current( @PathVariable("id") String id ){
        return rSocketRequester.route( "doctorId").data(id).retrieveMono( String.class );
    }

    @GetMapping( value = "/fioDoctor/{fio}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public  Publisher<String> findDoctorsByFIO( @PathVariable("fio") String fio ){
        return rSocketRequester.route( "doctorFIO").data( fio ).retrieveFlux( String.class );
    }

}*/
