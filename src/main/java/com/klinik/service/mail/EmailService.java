package com.klinik.service.mail;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.klinik.entity.User;
import com.klinik.request.email.EmailRequest;
import com.klinik.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PropertySource( value = { "classpath:email.properties"})
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserService    userService;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String email;
    /**
     * Обновленеи пароля
     * @param emailRequest - запрос на почту
     */
    public void sendSimpleEmailMessage( EmailRequest emailRequest ) {
        User user = userService.checkFindUserByLoginOrByMail( emailRequest.getLogin() );
        if ( user != null ){
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                              simpleMailMessage.setTo( user.getEmail());
                              simpleMailMessage.setSubject( emailRequest.getSubject() );
                              simpleMailMessage.setText( emailRequest.getBody() + userService.generateNewPasswordForUser( emailRequest.getLogin()) );
                              simpleMailMessage.setFrom( email );
            javaMailSender.send( simpleMailMessage );
        }
    }
    /**
     * Обновление пароля и отправка его на почту
     * @param param - логин или почта
     */
    public void sendNewPasswordToMail( String param ){
        User user = userService.checkFindUserByLoginOrByMail( param  );
        if ( user != null ){
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                              simpleMailMessage.setTo( user.getEmail());
                              simpleMailMessage.setSubject( "Изменение пароля" );
                              simpleMailMessage.setText( "Ваш пароль был изменен, используйте этот: " + userService.generateNewPasswordForUser( user.getLogin() ) );
                              simpleMailMessage.setFrom( email );
            javaMailSender.send( simpleMailMessage );
        }
    }
    /**
     * Отправка QR - кода на почту
     */
    public void sendQrCodeToMail( String username ) throws Exception{
        User user = userService.checkFindUserByLoginOrByMail(username);
       
        if (user != null) {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper( message, true );
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write( userService.generateTotpQR( username ), "png", byteArrayOutputStream );
            mimeMessageHelper.setTo( user.getEmail() );
            mimeMessageHelper.setFrom( email );
            mimeMessageHelper.setSubject("QR code для двухфакторной аутентификации через Google Authenticator");
            // через MimeMessageHelper
           // mimeMessageHelper.setText( "Ваш QR-код для аутентификации:" ); 
           // mimeMessageHelper.addAttachment( "qrcode.png", new ByteArrayResource( byteArrayOutputStream.toByteArray() ) );
            //через html - page
            String base64Image = Base64.getEncoder().encodeToString( byteArrayOutputStream.toByteArray() );
            mimeMessageHelper.setText( pageMail( base64Image ), true );
            javaMailSender.send(message);
        }
    }
    /**
     * Формирвоние страницы
     * @param base64Image - изображение 
     * @return String
     * @throws Exception
     */
    private String pageMail( String base64Image ) throws Exception{
        Context context = new Context();
        context.setVariable("Message", "Your QR - code: " );
        context.setVariable("qrImage", "data:image/png;base64," + base64Image); 
        return templateEngine.process("qrmail", context );
    }
}