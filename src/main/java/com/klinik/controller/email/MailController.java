package com.klinik.controller.email;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.request.email.EmailRequest;
import com.klinik.response.BaseResponse;
import com.klinik.rest.email.IEmail;
import com.klinik.service.UserService;
import com.klinik.service.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;

@RestController
@RequiredArgsConstructor
public class MailController implements IEmail {

    private final EmailService emailService;
    private final UserService  userService;

    @Override
    public ResponseEntity<BaseResponse> sendMessageToEmail( EmailRequest emailRequest ) {
        emailService.sendSimpleEmailMessage( emailRequest );
        return ResponseEntity.ok( new BaseResponse( 200 , "success" ));
    }

    @Override
    public ResponseEntity<BaseResponse> updatePasswordUser( String word ) {
        return ResponseEntity.ok( new BaseResponse<>( 200, "new password: " + userService.generateNewPasswordForUser( word )));
    }

    @Override
    public void getQr(String username, HttpServletResponse response) {
        BufferedImage qrImage = userService.generateTotpQR (username );
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