package com.klinik.controller.login;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.klinik.request.email.EmailRequest;
import com.klinik.rest.login.IAuthentication;
import com.klinik.service.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthenticationController implements IAuthentication  {

    private final EmailService emailService;

    @Override
    public String login() {
        return "login";
    }

    @Override
    public String index() {
        return "index";
    }

    @Override
    public String changePassword() {
        return "change-password";
    }

    @Override
    public String clearErrorMessage(HttpServletRequest request) {
        request.getSession().removeAttribute("error");
        return "redirect:/login"; 
    }
    
    @Override
    public String requestPasswordChange( String user, HttpServletRequest request,  RedirectAttributes redirectAttributes ) {
        try{
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setLogin( user );
            emailRequest.setSubject("Изменение пароля");
            emailRequest.setBody("Ваш пароль был изменен, используйте этот: ");
            emailService.sendSimpleEmailMessage(emailRequest);;
            redirectAttributes.addFlashAttribute("message", "Новый пароль отправлен на вашу почту!");
        }catch( Exception ex ){
            redirectAttributes.addFlashAttribute("error", ex.getMessage() );
        }
        return "redirect:/change-password"; 
    }

}
