package com.klinik.controller.login;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.klinik.entity.User;
import com.klinik.request.UserRequest;
import com.klinik.response.UserResponse;
import com.klinik.rest.login.IAuthentication;
import com.klinik.security.auth.AuthService;
import com.klinik.security.auth.provider.KlinikaAuthenticationProvider;
import com.klinik.service.UserService;
import com.klinik.service.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthenticationController implements IAuthentication  {

    private final EmailService emailService;
    private final UserService userService;
    private final AuthService authService;
    private final KlinikaAuthenticationProvider klinikaAuthenticationProvider;

    @Override
    public String code( ) {
        return "code";
    }
    
    @Override
    public String clearErrorMessage(HttpServletRequest request) {
        request.getSession().removeAttribute("error");
        return "redirect:/code"; 
    }
    
    @Override
    public String login() {
        return "login";
    }

    @Override
    public String login( String username, String password, RedirectAttributes redirectAttributes){
        try{
            User user = authService.checkUser(username, password);
            if (user != null) {
                klinikaAuthenticationProvider.setLogin( username );
                return "redirect:/code";
            }
        }catch( Exception ex ) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage() );
        }
        return "redirect:/login";
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
    public String requestPasswordChange( String user, HttpServletRequest request,  RedirectAttributes redirectAttributes ) {
        try{
            emailService.sendNewPasswordToMail( user );
            redirectAttributes.addFlashAttribute("message", "Новый пароль отправлен на вашу почту!");
        }catch( Exception ex ){
            redirectAttributes.addFlashAttribute("error", ex.getMessage() );
        }
        return "redirect:/change-password"; 
    }

    @Override
    public String register() {
        return "register";
    }

    @Override
    public String registerGetQr( UserRequest userRequest, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        userRequest.setRole( "ADMIN");
        System.out.println( userRequest );
        try{
            UserResponse userResponse = userService.addUser( userRequest );
            redirectAttributes.addFlashAttribute("message", "Отсканируйте Qr в приложении: Google Authenticator ");
            if (userResponse != null) {
                String qrImage = "/web/image-access-qr?login=" + userResponse.getLogin();
                redirectAttributes.addFlashAttribute("qrImage", qrImage);
            }
        }catch( Exception ex ){
            redirectAttributes.addFlashAttribute("error", ex.getMessage() );
        }
        return "redirect:/register"; 
    }

    @Override
    public void getQrImage(String login, HttpServletRequest request, HttpServletResponse response) {
        BufferedImage qrImage = userService.generateTotpQR(login);
         if (qrImage != null) {
            response.setContentType(org.springframework.http.MediaType.IMAGE_PNG_VALUE);
            try (OutputStream outputStream = response.getOutputStream()) {
                ImageIO.write(qrImage, "png", outputStream);
                outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                 }
            }
    }

}