package com.klinik.controller.model;

import com.klinik.entity.Doctor;
import com.klinik.kafka.message.SendMessageBroker;
import com.klinik.rest.model.IDoctor;
import com.klinik.service.DoctorService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DoctorController implements IDoctor{

    private final DoctorService doctorService;
    private final KafkaTemplate<String,SendMessageBroker> kafkaTemplate;

    private void sendMessage( SendMessageBroker sendMessageBroker ){
        kafkaTemplate.send("klinikFirst", sendMessageBroker );
    }

    public ResponseEntity<List<Doctor>> findByFIO( String word, int page, int size  ) throws Exception{
        return new ResponseEntity<>( doctorService.findByFIO( word, page, size ), HttpStatus.OK); 
    }
    public ResponseEntity<Doctor> addDoctor( Doctor doctor ) throws Exception{
        return new ResponseEntity<>(  doctorService.saveDoctor( doctor ), HttpStatus.OK );
    }
    @Override
    public ResponseEntity<List<Doctor>> getLazyDoctors(int page, int size) {
        List<Doctor> response =  doctorService.getLazyDoctor( page, size );
        response.stream()
                .forEach( doctor -> sendMessage( new SendMessageBroker<>( LocalDateTime.now(), 
                                                                 "klinik",
                                                                 "SpringPro",
                                                                          doctor )));
        return new ResponseEntity<>( response, HttpStatus.OK );
    }
    @Override
    public ResponseEntity<Long> getCountDoctors() {
        return new ResponseEntity<>( doctorService.getCountDoctors(), HttpStatus.OK );
    }
}
