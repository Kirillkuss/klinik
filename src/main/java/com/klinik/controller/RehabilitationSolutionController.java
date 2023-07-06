package com.klinik.controller;

import com.klinik.entity.RehabilitationSolution;
import com.klinik.rest.IRehabilitationSolution;
import com.klinik.service.RehabilitationSolutionService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RehabilitationSolutionController implements IRehabilitationSolution {

    private final RehabilitationSolutionService rehabilitationSolutionService;
    public ResponseEntity<List<RehabilitationSolution>> getAllRehabilitationSolution() throws Exception{
        return new ResponseEntity<>( rehabilitationSolutionService.getAll(), HttpStatus.OK );
    }
    public ResponseEntity<RehabilitationSolution> findByName( String name ) throws Exception{
        return new ResponseEntity<>( rehabilitationSolutionService.findByName( name ), HttpStatus.OK );
    }
    public ResponseEntity<RehabilitationSolution> save( RehabilitationSolution solution ) throws Exception{
        return new ResponseEntity<>( rehabilitationSolutionService.saveRehabilitationSolution( solution ), HttpStatus.CREATED );
    }
}
