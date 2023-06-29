package com.klinik.controller;

import com.klinik.entity.Rehabilitation_solution;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.service.RehabilitationSolutionService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RehabilitationSolutionController {

    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    private RehabilitationSolutionService rehabilitationSolutionService;
    public BaseResponse getAllRehabilitationSolution() throws Exception{
        return new BaseResponse<>( 200, "success", rehabilitationSolutionService.getAllReha());
    }
    public BaseResponse findByName( @Parameter( description = "Наименование лечения") String name ) throws Exception{
        if( rehabilitationSolutionService.findByName( name ) == null ) throw new MyException( 999, "По данному запросу ничего не найдено");
        return new BaseResponse<>( 200, "success", rehabilitationSolutionService.findByName( name ));
    }
    public BaseResponse save( Rehabilitation_solution solution ) throws Exception{
        if( rehabilitationSolutionService.findByName( solution.getName() ) != null )                         throw new MyException( 461, "Ребилитационное лечение с таким наименованием уже существует");
        if( rehabilitationSolutionService.findByIdList( solution.getId_rehabilitation_solution() ) != null ) throw new MyException( 460, "Ребилитационное лечение с таким ИД уже существует");
        return new BaseResponse<>( 200, "success", rehabilitationSolutionService.saveRS( solution ));
    }
}
