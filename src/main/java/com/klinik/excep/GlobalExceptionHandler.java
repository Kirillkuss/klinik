package com.klinik.excep;

import java.util.NoSuchElementException;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.klinik.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
/**
 * Обработчик исключений для всех классов в папке controller
 * @param ex - исключение
 * @return ResponseEntity
 */
@Slf4j  
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BaseResponse> errBaseResponse( Throwable ex ){
        log.error( "Throwable >>> " + ex.getMessage());
        return ResponseEntity.internalServerError().body( BaseResponse.error( HttpStatus.INTERNAL_SERVER_ERROR.value(), ex ));
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<BaseResponse> handleSecurityException(SecurityException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( new BaseResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
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
        log.error("MyException >>> " + ex.getMessage());
        return response;
    }

    @ExceptionHandler( NoSuchElementException.class )
    public ResponseEntity<BaseResponse> errBaseResponse( NoSuchElementException ex ){
        log.error( "NoSuchElementException >>> " +  ex.getMessage() );
        return ResponseEntity.status( HttpStatus.NOT_FOUND )
                             .body( new BaseResponse<>( 404, ex.getMessage() ));
    }

    @ExceptionHandler( IllegalArgumentException.class )
    public ResponseEntity<BaseResponse> errBaseResponse( IllegalArgumentException ex ){
        log.error( "IllegalArgumentException >>> " +  ex.getMessage() );
        return ResponseEntity.status( HttpStatus.BAD_REQUEST )
                             .body( new BaseResponse<>( 400, ex.getMessage() ));
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<String> handleConflict(RuntimeException ex) {
        return new ResponseEntity<>( "ERROR", HttpStatus.OK );
    }

    /**@ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "errorpath"; // Перенаправление на страницу 404
    }*/
    
}
