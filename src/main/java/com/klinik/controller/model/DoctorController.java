package com.klinik.controller.model;

import com.klinik.entity.Doctor;
import com.klinik.rest.model.IDoctor;
import com.klinik.service.DoctorService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DoctorController implements IDoctor{

    private final DoctorService doctorService;
    private final KafkaTemplate<String,String> kafkaTemplate;

    private void sendMessage( String message ){
        kafkaTemplate.send("topicKlinikFirst", message);
    }

    public ResponseEntity<List<Doctor>> findByFIO( String word, int page, int size  ) throws Exception{
        sendMessage( "Klinik > DoctorController > findByFIO");
        return new ResponseEntity<>( doctorService.findByFIO( word, page, size ), HttpStatus.OK); 
    }
    public ResponseEntity<Doctor> addDoctor( Doctor doctor ) throws Exception{
        sendMessage( "Klinik > DoctorController > addDoctor");
        return new ResponseEntity<>(  doctorService.saveDoctor( doctor ), HttpStatus.OK );
    }
    @Override
    public ResponseEntity<List<Doctor>> getLazyDoctors(int page, int size) {
        sendMessage( "Klinik > DoctorController > findByFIO");
        return new ResponseEntity<>( doctorService.getLazyDoctor( page, size ), HttpStatus.OK );
    }
    @Override
    public ResponseEntity<Long> getCountDoctors() {
        sendMessage( "Klinik > DoctorController > getCountDoctors");
        return new ResponseEntity<>( doctorService.getCountDoctors(), HttpStatus.OK );
    }
}
