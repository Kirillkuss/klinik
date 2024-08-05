package com.klinik.security;

import java.util.Random;
import java.util.Base64;
import java.util.Optional;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Hex;
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
import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateKeysDataBase {

    private final KeyEntityRepository keyEntityRepository;
    private final TextEncryptor textEncryptor;

    private final String alice = "admin";

    @Value("${jwt.public.key}")
    RSAPublicKey publicKeyAppPr;

    @Value("${jwt.private.key}")
    RSAPrivateKey privateKeyAppPr;

    @PostConstruct
    public void init() throws Exception{
        if( keyEntityRepository.findByKeyAlias( alice ).isEmpty()){
            saveKey( alice, getValue( publicKeyAppPr.getEncoded() ),
                            getValue( privateKeyAppPr.getEncoded() ));
            log.info( "add keys to database!");                
        }
    }
    /**
     * обновление ключа через pem файлы
     */
    public void updateKeyToDataBaseFromPem(){
        Optional<KeyEntity> keyEntity = keyEntityRepository.findByKeyAlias( alice );
        if( keyEntity.isEmpty()){
            saveKey( alice, getValue( publicKeyAppPr.getEncoded() ),
                            getValue( privateKeyAppPr.getEncoded() ));
            log.info( "add keys to database!");                
        }else{
            KeyEntity request = keyEntity.get();
            request.setPrivateKey( textEncryptor.encrypt( getValue( privateKeyAppPr.getEncoded() )));
            request.setPublicKey( textEncryptor.encrypt( getValue( publicKeyAppPr.getEncoded() )));
            request.setDateCreate( LocalDateTime.now() );
            keyEntityRepository.save( request );
            log.info( "updates keys to database!");   
        }
    }
    /**
     * Обновлениче ключей в БД
     * @throws Exception
     */
    public void updateKeyToDataBase() throws Exception{
        Optional<KeyEntity> keyEntity = keyEntityRepository.findByKeyAlias( alice );
        KeyPair keyPair = getKeyPair();
        if( keyEntity.isEmpty()){
            saveKey( alice, getValue( publicKeyAppPr.getEncoded() ),
                            getValue( privateKeyAppPr.getEncoded() ));
            log.info( "add keys to database!");                
        }else{
            KeyEntity request = keyEntity.get();
            request.setPrivateKey( textEncryptor.encrypt( getValue( new PKCS8EncodedKeySpec( keyPair.getPublic().getEncoded() ).getEncoded() )));
            request.setPublicKey( textEncryptor.encrypt( getValue( new PKCS8EncodedKeySpec( keyPair.getPrivate().getEncoded() ).getEncoded() )));
            request.setDateCreate( LocalDateTime.now() );
            keyEntityRepository.save( request );
            log.info( "updates keys to database!");   
        }
    }
    /**
     * Получение KeyPair
     * @return KeyPair
     * @throws Exception
     */
    private KeyPair getKeyPair() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * Кодирование строки 
     * @param encode - кодировка
     * @return String
     */
    private String getValue ( byte[] encode ){
        return Base64.getEncoder().encodeToString( encode );
    }

    /**
     * Сохранение ключей
     * @param keyAlias   - владелец ключа
     * @param publicKey  - публичный ключ
     * @param privateKey - приваный ключ
     * @return KeyEntity
     */
    private KeyEntity saveKey(String keyAlias, String publicKey, String privateKey) {
        return keyEntityRepository.save( new KeyEntity( new Random().nextLong(),
                                                        keyAlias,
                                                        LocalDateTime.now(),
                                                        textEncryptor.encrypt( publicKey ),
                                                        textEncryptor.encrypt( privateKey )));
    }
    /**
     * Получение ключа
     * @param keyAlias - владелец ключа
     * @return KeyEntity
     */ 
    public KeyEntity getKey(String keyAlias) {
        KeyEntity keyEntity = keyEntityRepository.findByKeyAlias(keyAlias).orElseThrow();
        if (keyEntity != null) {
            keyEntity.setPublicKey(textEncryptor.decrypt(keyEntity.getPublicKey()));
            keyEntity.setPrivateKey(textEncryptor.decrypt(keyEntity.getPrivateKey()));
        }
        return keyEntity;
    }
    /**
     * Получение публичного ключа
     * @return String
     */
    private String getPublicKey(){
        return getKey( alice ).getPublicKey();
    }  
    /**
     * Получение приватного ключа
     * @return String
     */
    private String getPrivateKey(){
        return  getKey( alice ).getPrivateKey();
    }
    /**
     * Формирование RSAPublicKey
     * @return Optional RSAPublicKey
     */
    public Optional<RSAPublicKey> getRSAPublicKey(){
        try{
            return Optional.of((RSAPublicKey) KeyFactory.getInstance("RSA")
                                                        .generatePublic( new X509EncodedKeySpec( Base64.getDecoder().decode( getPublicKey() ))));
        }catch( Exception ex ){
            log.error( "ERROR getRSAPublicKey >>> " + ex.getMessage());
            return Optional.empty();
        }
    }
    /**
     * Формирование RSAPrivateKey
     * @return Optional RSAPrivateKey
     */
    public Optional<RSAPrivateKey> getRSAPrivateKey() {
        try {
            return Optional.of((RSAPrivateKey) KeyFactory.getInstance("RSA")
                                                         .generatePrivate( new PKCS8EncodedKeySpec( Base64.getDecoder().decode( getPrivateKey() ))));
        } catch (Exception ex ) {
            log.error( "ERROR getRSAPrivateKey >>> " + ex.getMessage());
            return Optional.empty();
        }
    }

    // Метод для генерации соли для TextEncryptor
    private String generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return new String(Hex.encode(salt));
    }

}
