package com.klinik.service.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.klinik.entity.User;
import com.klinik.request.email.EmailRequest;
import com.klinik.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserService    userService;

    public void sendSimpleEmailMessage( EmailRequest emailRequest ) {
        User user = userService.checkFindUserByLoginOrByMail(emailRequest.getLogin() );
        if ( user != null ){
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                              simpleMailMessage.setTo( user.getEmail());
                              simpleMailMessage.setSubject( emailRequest.getSubject() );
                              simpleMailMessage.setText( emailRequest.getBody() + userService.generateNewPasswordForUser( emailRequest.getLogin()) );
                              simpleMailMessage.setFrom( "borisevich711@gmail.com" );
            javaMailSender.send( simpleMailMessage );
        }
        

    }
}