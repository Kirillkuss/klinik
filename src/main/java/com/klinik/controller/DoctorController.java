package com.klinik.controller;

import com.klinik.entity.Doctor;
import com.klinik.rest.IDoctor;
import com.klinik.service.DoctorService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DoctorController implements IDoctor{

    private final DoctorService doctorService;

    public ResponseEntity<List<Doctor>> getAllDoc( int page, int size ) throws Exception{
        return new ResponseEntity<>( doctorService.allDoctor( page, size ), HttpStatus.OK );
    }
    public ResponseEntity<List<Doctor>> findByFIO( String word, int page, int size  ) throws Exception{
        return new ResponseEntity<>( doctorService.findByFIO( word, page, size ), HttpStatus.OK); 
    }
    public ResponseEntity<Doctor> addDoctor( Doctor doctor ) throws Exception{
        return new ResponseEntity<>(  doctorService.saveDoctor( doctor ), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<List<Doctor>>  getLazyDoctors(int page, int size) {
        return new ResponseEntity<>( doctorService.getLazyDoctor( page, size ), HttpStatus.OK );
    }
    @Override
    public ResponseEntity<Long> getCountDoctors() {
        return new ResponseEntity<>( doctorService.getCountDoctors(), HttpStatus.OK );
    }
}
