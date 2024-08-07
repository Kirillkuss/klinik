package com.klinik.security;

import java.util.Random;
import java.util.Base64;
import java.util.Optional;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import com.klinik.entity.KeyEntity;
import com.klinik.repositories.KeyEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateKeysDataBase {

    private final KeyEntityRepository keyEntityRepository;
    private final TextEncryptor textEncryptor;
    private final String alice = "admin";

    @Value("${jwt.public.key}")
    RSAPublicKey publicKey;

    @Value("${jwt.private.key}")
    RSAPrivateKey privateKey;

    @PostConstruct
    public void init() throws Exception{
        if( keyEntityRepository.findByKeyAlias( alice ).isEmpty()){
            saveKey( alice, getValue( publicKey.getEncoded() ),
                            getValue( privateKey.getEncoded() ));
            log.info( "init keys to database!");                
        }
    }

   public void updateKeyToDataBaseFromPem(){
        Optional<KeyEntity> keyEntity = keyEntityRepository.findByKeyAlias( alice );
        if( keyEntity.isEmpty()){
            saveKey( alice, getValue( publicKey.getEncoded() ),
                            getValue( privateKey.getEncoded() ));
            log.info( "add keys to database!");                
        }else{
            KeyEntity request = keyEntity.get();
            request.setPrivateKey( textEncryptor.encrypt( getValue( privateKey.getEncoded() )));
            request.setPublicKey( textEncryptor.encrypt( getValue( publicKey.getEncoded() )));
            request.setDateCreate( LocalDateTime.now() );
            keyEntityRepository.save( request );
            log.info( "updates keys to database froom pem files!");   
        }
    }

    public void updateKeyToDataBase() throws Exception{
        Optional<KeyEntity> keyEntity = keyEntityRepository.findByKeyAlias( alice );
        KeyPair keyPair = getKeyPair();
        if( keyEntity.isEmpty()){
            saveKey( alice, getValue( publicKey.getEncoded() ),
                            getValue( privateKey.getEncoded() ));
            log.info( "add keys to database!");                
        }else{
            KeyEntity request = keyEntity.get();
            request.setPublicKey( textEncryptor.encrypt( getValue(keyPair.getPublic().getEncoded() )));
            request.setPrivateKey(textEncryptor.encrypt( getValue(keyPair.getPrivate().getEncoded() )));
            request.setDateCreate( LocalDateTime.now() );
            keyEntityRepository.save( request );
            log.info( "updates keys to database from KeyPairGenerator!");   
        }
    }

    private KeyPair getKeyPair() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }


    private String getValue( byte[] encode ){
        return Base64.getEncoder().encodeToString( encode );
    }


    private KeyEntity saveKey(String keyAlias, String publicKey, String privateKey) {
        return keyEntityRepository.save( new KeyEntity( new Random().nextLong(),
                                                        keyAlias,
                                                        LocalDateTime.now(),
                                                        textEncryptor.encrypt( publicKey ),
                                                        textEncryptor.encrypt( privateKey )));
    }

    public KeyEntity getKey(String keyAlias) {
        KeyEntity keyEntity = keyEntityRepository.findByKeyAlias(keyAlias).orElseThrow();
        if (keyEntity != null) {
            keyEntity.setPublicKey( textEncryptor.decrypt(keyEntity.getPublicKey()));
            keyEntity.setPrivateKey(textEncryptor.decrypt(keyEntity.getPrivateKey()));
        }
        return keyEntity;
    }

    private String getPublicKey(){
        return getKey( alice ).getPublicKey();
    }  

    private String getPrivateKey(){
        return getKey( alice ).getPrivateKey();
    }

    public Optional<RSAPublicKey> getRSAPublicKey(){
        try{
            return Optional.of(( RSAPublicKey ) KeyFactory.getInstance("RSA")
                                                          .generatePublic( new X509EncodedKeySpec( Base64.getDecoder()
                                                                                                         .decode( getPublicKey() ))));
        }catch( Exception ex ){
            log.error( "ERROR getRSAPublicKey >>> " + ex.getMessage());
            return Optional.empty();
        }
    }

    public Optional<RSAPrivateKey> getRSAPrivateKey() {
        try {
            return Optional.of((RSAPrivateKey) KeyFactory.getInstance("RSA")
                                                         .generatePrivate( new PKCS8EncodedKeySpec( Base64.getDecoder()
                                                                                                          .decode( getPrivateKey() ))));
        } catch (Exception ex ) {
            log.error( "ERROR getRSAPrivateKey >>> " + ex.getMessage());
            return Optional.empty();
        }
    }
}
