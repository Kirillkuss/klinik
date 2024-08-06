package com.klinik.security;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
/**
 * Генерация ключей в файлы private.pem and public.pem
 */
@Slf4j
@Service
public class GenerateKeys {

    private final String path = "src/main/resources/keys";
    /**
     * Запись ключей
     * @throws Exception
     */
    public void generateKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        savePublicKey( keyPair.getPublic(), "public.pem");
        savePrivateKey( keyPair.getPrivate(), "private.pem");
        log.info( "Update keys execute success");
    }
    /**
     * Сохранение публичного ключа в файле
     * @param publicKey - ключ
     * @param fileName  - название файла
     * @throws Exception
     */
    private void savePublicKey( PublicKey publicKey, String fileName ) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(new File( path, fileName))) {
            fos.write("-----BEGIN PUBLIC KEY-----\n".getBytes());
            String encodedKey = Base64.getEncoder().encodeToString(new PKCS8EncodedKeySpec(publicKey.getEncoded()).getEncoded());
            String[] lines = encodedKey.split("(?<=\\G.{64})");
            for (String line : lines) {
                fos.write(line.getBytes());
                fos.write("\n".getBytes()); 
            }
            fos.write("-----END PUBLIC KEY-----\n".getBytes());
        }
    }
    /**
     * Сохранение приватного ключа в файле 
     * @param privateKey - приватный ключ
     * @param fileName - название файла
     * @throws Exception
     */
    private void savePrivateKey( PrivateKey privateKey, String fileName ) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(new File(path, fileName))) {
            fos.write("-----BEGIN PRIVATE KEY-----\n".getBytes());
            String encodedKey = Base64.getEncoder().encodeToString(new PKCS8EncodedKeySpec(privateKey.getEncoded()).getEncoded());
            String[] lines = encodedKey.split("(?<=\\G.{64})");
            for (String line : lines) {
                fos.write(line.getBytes());
                fos.write("\n".getBytes()); 
            }
            fos.write("-----END PRIVATE KEY-----\n".getBytes());
        } 
    }
   
}