package com.klinik.service.mail;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
    private static final int PASSWORD_LENGTH = 12; 
    private static final SecureRandom random = new SecureRandom();

    public String generateRandomPassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(randomIndex));
        }
        
        return password.toString();
    }
}