package com.klinik.controller.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.request.email.EmailRequest;
import com.klinik.response.BaseResponse;
import com.klinik.rest.email.IEmail;
import com.klinik.service.UserService;
import com.klinik.service.mail.EmailService;

@RestController
public class MailController implements IEmail {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<BaseResponse> sendMessageToEmail( EmailRequest emailRequest ) {
        emailService.sendSimpleEmailMessage( emailRequest );
        return ResponseEntity.ok( new BaseResponse( 200 , "success" ));
    }

    @Override
    public ResponseEntity<BaseResponse> updatePasswordUser(String word) {
        return ResponseEntity.ok( new BaseResponse<>( 200, "new password: " + userService.generateNewPasswordForUser( word )));
    }
}