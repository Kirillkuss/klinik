package com.klinik.security;

import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.codec.Hex;

@Slf4j
@Configuration
public class SaltGenerator {

    @Value("${encryption.key}")
    private String encryptionKey;

    private static final String KEYSTORE_PATH = "src/main/resources/keys/encryption.key";

    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.text( Base64.getEncoder()
                                      .encodeToString( encryptionKey.getBytes()) , "dc018ad7495d789f5144e85da324b2c0");
    }

    // Метод для генерации соли для TextEncryptor
    private String generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return new String(Hex.encode(salt));
    }
    /**
     * Обновление Encryption
     */
    public void updateEncryptionKey(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32]; 
        secureRandom.nextBytes(keyBytes);
        log.info("Generated Encryption Key");
        try (FileWriter writer = new FileWriter( KEYSTORE_PATH )) {
            writer.write( Base64.getEncoder().encodeToString( keyBytes ));
        } catch (IOException e) {
            log.error( "Error writing encryption key to file: {}", KEYSTORE_PATH, e );
        }
    }
    
}
