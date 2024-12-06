package com.klinik.controller.login;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class KlinikaErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError( HttpServletRequest request, Model model ) {
        Object status = request.getAttribute( RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute( RequestDispatcher.ERROR_MESSAGE );
        if ( status.equals( HttpStatus.NOT_FOUND.value() ) ){
            message = " Неверно указан путь! ";
        }
        if( status.equals( HttpStatus.FORBIDDEN.value() ) ){
            message = " Нет прав к доступу! ";
        }
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        return "error";
    }
    
}
