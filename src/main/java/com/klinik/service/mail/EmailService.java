package com.klinik.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.klinik.request.email.EmailRequest;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmailMessage( EmailRequest emailRequest ) {
        SimpleMailMessage message = new SimpleMailMessage();
                          message.setTo( "" );
                          message.setSubject( emailRequest.getSubject() );
                          message.setText( emailRequest.getBody() );
                          message.setFrom( "" );
                          mailSender.send( message );
    }
}