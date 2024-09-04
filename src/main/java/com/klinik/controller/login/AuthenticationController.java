package com.klinik.controller.login;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import com.klinik.rest.IAuthentication;
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
}
