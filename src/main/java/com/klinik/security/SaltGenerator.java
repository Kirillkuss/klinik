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
    private static final String ALPHABET      = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateSalt(int length) {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(randomIndex));
        }
        return sb.toString();
    }

    @Bean
    public TextEncryptor textEncryptor() {
        System.out.println( encryptionKey );
        return Encryptors.text(encryptionKey, generateSalt());
    }

    // Метод для генерации соли
    private String generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return new String(Hex.encode(salt));
    }
    public void addKeys(){
           SecureRandom secureRandom = new SecureRandom();
            byte[] keyBytes = new byte[32]; 
            secureRandom.nextBytes(keyBytes);
            String encryptionKey = Base64.getEncoder().encodeToString(keyBytes);
            log.info("Generated Encryption Key");
            try (FileWriter writer = new FileWriter(KEYSTORE_PATH)) {
                writer.write(encryptionKey);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }


    
}
