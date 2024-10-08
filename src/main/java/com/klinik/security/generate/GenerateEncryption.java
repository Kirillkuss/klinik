package com.klinik.security.generate;

import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GenerateEncryption {

    @Value("${encryption.path}")
    private String encryptionPath;

    private String generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return new String(Hex.encode(salt));
    }

    public void updateEncryptionKey(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32]; 
        secureRandom.nextBytes(keyBytes);
        log.info("Generated Encryption Key");
        try (FileWriter writer = new FileWriter( encryptionPath )) {
            writer.write( Base64.getEncoder().encodeToString( keyBytes ));
        } catch (IOException e) {
            log.error( "Error writing encryption key to file: {}", encryptionPath, e );
        }
    }
     
}
