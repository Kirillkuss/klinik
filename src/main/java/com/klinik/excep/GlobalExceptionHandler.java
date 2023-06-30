package com.klinik.excep;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.klinik.response.BaseResponse;
/**
 * Обработчик исключений для всех классов в папке controller
 * @param ex - исключение
 * @return ResponseEntity
 */ 
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BaseResponse> errBaseResponse( Throwable ex ){
        return ResponseEntity.internalServerError().body( BaseResponse.error( HttpStatus.INTERNAL_SERVER_ERROR.value(), ex ));
    }

    @ExceptionHandler(MyException.class)
    public ResponseEntity<BaseResponse> errBaseResponse( MyException ex ){
        ResponseEntity<BaseResponse> response = null;
        switch (  ex.getCode() ){
            case 400 : {
                response = ResponseEntity.status( HttpStatus.BAD_REQUEST )
                                         .body( BaseResponse.error( ex.getCode(), ex ));
            };
            break;
            case 404 : {
                response = ResponseEntity.status( HttpStatus.NOT_FOUND )
                                         .body( BaseResponse.error( ex.getCode(), ex ));
            };
            break;
            case 409 : {
                response = ResponseEntity.status( HttpStatus.CONFLICT )
                                         .body( BaseResponse.error( ex.getCode(), ex ));
            };
            break;
        }
        return response;
    }
    
}
