package com.klinik.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.klinik.rest.login.IAuthentication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AuthenticationController implements IAuthentication  {

    @Override
    public String login() {
        return "login";
    }

    @Override
    public String clearErrorMessage(HttpServletRequest request) {
        request.getSession().removeAttribute("error");
        return "redirect:/web/login"; 
    }

    @GetMapping(value = "reset-password", produces = MediaType.APPLICATION_JSON)
    public String resetPasswordPage() {
        return "reset-password";
    }
    

    @PostMapping(value = "reset-password")
    public String requestPasswordReset(@RequestParam("identifier") String identifier, HttpServletRequest request) {
        request.getSession().setAttribute("message", "Новый пароль отправлен на вашу почту!");
        return "redirect:/web/login"; 
    }
}
