package com.klinik.security;

import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Configuration
public class TextEncryptorGenerate {

    @Value("${encryption.key}")
    private String encryptionKey;

    @Value("${spring.security.secret}")
    private String secret;
    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.text( Base64.getEncoder()
                                      .encodeToString( encryptionKey.getBytes()) , secret );
    }
    
    // Метод для генерации соли для TextEncryptor
    private String generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return new String(Hex.encode(salt));
    }
    
}
