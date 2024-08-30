package com.klinik.security.generate;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
/**
 * Генерация ключей в файлы private.pem and public.pem
 */
@Slf4j
@Service
public class GenerateKeys {

    @Value("${generate.keys.path}")
    private String path;
    private final String split = "(?<=\\G.{64})";

    public void generatePemKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        savePublicKey( keyPair.getPublic(), "public.pem");
        savePrivateKey( keyPair.getPrivate(), "private.pem");
        log.info( "Update keys execute success");
    }

    private void savePublicKey( PublicKey publicKey, String fileName ) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(new File( path, fileName))) {
            fos.write("-----BEGIN PUBLIC KEY-----\n".getBytes());
            String[] lines = Base64.getEncoder()
                                   .encodeToString( new PKCS8EncodedKeySpec( publicKey.getEncoded())
                                   .getEncoded())
                                   .split( split );
            for ( String line : lines ) {
                fos.write(line.getBytes());
                fos.write("\n".getBytes()); 
            }
            fos.write("-----END PUBLIC KEY-----\n".getBytes());
        }catch( Exception ex){
            log.error( "savePublicKey >>> " + ex.getMessage());
        }
    }

    private void savePrivateKey( PrivateKey privateKey, String fileName ) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(new File(path, fileName))) {
            fos.write("-----BEGIN PRIVATE KEY-----\n".getBytes());
            String[] lines = Base64.getEncoder()
                                   .encodeToString( new PKCS8EncodedKeySpec(privateKey.getEncoded())
                                   .getEncoded())
                                   .split( split );
            for (String line : lines) {
                fos.write(line.getBytes());
                fos.write("\n".getBytes()); 
            }
            fos.write("-----END PRIVATE KEY-----\n".getBytes());
        }catch( Exception ex ){
            log.error( "savePrivateKey >>> " + ex.getMessage());
        } 
    }
}
