package com.klinik.controller;

import com.klinik.entity.Rehabilitation_solution;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.rest.IRehabilitationSolution;
import com.klinik.service.RehabilitationSolutionService;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RehabilitationSolutionController implements IRehabilitationSolution {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity errBaseResponse( Throwable ex ){
        return ResponseEntity.badRequest().body( BaseResponse.error( 999, ex ));
    }

    @ExceptionHandler(MyException.class)
    public ResponseEntity errBaseResponse( MyException ex ){
        return ResponseEntity.badRequest().body( BaseResponse.error( ex.getCode(), ex ));
    }

    @Autowired
    private RehabilitationSolutionService rehabilitationSolutionService;
    public ResponseEntity<List<Rehabilitation_solution>> getAllRehabilitationSolution() throws Exception{
        return new ResponseEntity<>( rehabilitationSolutionService.getAllReha(), HttpStatus.OK );
    }
    public ResponseEntity<Rehabilitation_solution> findByName( @Parameter( description = "Наименование лечения") String name ) throws Exception{
        if( rehabilitationSolutionService.findByName( name ) == null ) throw new MyException( 999, "По данному запросу ничего не найдено");
        return new ResponseEntity<>( rehabilitationSolutionService.findByName( name ), HttpStatus.OK );
    }
    public ResponseEntity<Rehabilitation_solution> save( Rehabilitation_solution solution ) throws Exception{
        if( rehabilitationSolutionService.findByName( solution.getName() ) != null )                         throw new MyException( 461, "Ребилитационное лечение с таким наименованием уже существует");
        if( rehabilitationSolutionService.findByIdList( solution.getId_rehabilitation_solution() ) != null ) throw new MyException( 460, "Ребилитационное лечение с таким ИД уже существует");
        return new ResponseEntity<>( rehabilitationSolutionService.saveRS( solution ), HttpStatus.CREATED );
    }
}
