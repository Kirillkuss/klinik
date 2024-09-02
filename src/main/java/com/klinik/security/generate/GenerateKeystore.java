package com.klinik.security.generate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Date;
import java.util.Optional;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
/**
 * Сервис для геренрации и получение ключей в Keystore
 */
@Slf4j
@Service
public class GenerateKeystore {
    /**
     * for docker-compose
     */
    //private static final String KEYSTORE_PATH     = "/app/keystore";
    private static final String KEYSTORE_PATH     = "src/main/resources/keystore";
    private static final String KEYSTORE_FILE     = "klinika.jks";
    private static final String KEYSTORE_PASSWORD = "klinika";
     /**
     * KeyStore
     * @return KeyStore
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     */
    private KeyStore getKeyStore() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        return loadKeyStore( KEYSTORE_PATH, KEYSTORE_FILE, KEYSTORE_PASSWORD );
    }
    /**
     * Загрузка KeyStore
     * @param path - путь
     * @param filename - название файла
     * @param password - пароль
     * @return KeyStore
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     */
    private KeyStore loadKeyStore(String path, String filename, String password) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
         KeyStore keystore = KeyStore.getInstance("JKS");
         try (FileInputStream fis = new FileInputStream(new File(path, filename))) {
             keystore.load(fis, password.toCharArray());
         }
         return keystore;
     }
    /**
     * Получение публичного ключа
     * @return RSAPublicKey
     */
    public Optional<RSAPublicKey> getPublicKey( ){
        try{
            return Optional.of ((RSAPublicKey) getKeyStore().getCertificate("klinikaKey").getPublicKey());
        }catch( Exception ex ){
            log.error("ERROR getPublicKey > " + ex.getMessage() );
            return Optional.empty();
        }
    }
    /**
     * Получение приватного ключа 
     * @return
     */
    public Optional<RSAPrivateKey> getPrivateKey(){
        try{
            return Optional.of( (RSAPrivateKey) getKeyStore().getKey("klinikaKey", "klinika".toCharArray()));
        }catch( Exception ex ){
            log.error("ERROR getPrivateKey > " + ex.getMessage() );
            return Optional.empty();
        }
    }

    public void updateKeyToKeystore() throws Exception{
        try{
            KeyStore keystore = getKeyStore();
            String alias = "klinikaKey";
            if (keystore.containsAlias(alias)) {
                keystore.deleteEntry(alias);
            }else{
                throw new IllegalArgumentException( "Not found keystore");
            }
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            Certificate[] chain = { getX509Certificate ( keyPair )};
            keystore.setKeyEntry(alias, privateKey, KEYSTORE_PASSWORD.toCharArray(), chain);
                try (FileOutputStream fos = new FileOutputStream( new File( KEYSTORE_PATH , KEYSTORE_FILE) )) {
                    keystore.store(fos, KEYSTORE_PASSWORD.toCharArray());
                }
            log.info( "success update keys into keystore ");
        }catch( Exception ex ){
            throw new Exception( ex.getMessage() );
        }
    }
    /**
     * Формирование X509Certificate
     * @param key - KeyPair
     * @return X509Certificate
     * @throws Exception
     */
    private X509Certificate getX509Certificate( KeyPair key ) throws Exception {
        Security.addProvider( new BouncyCastleProvider() );
        X500Principal subjectDN = new X500Principal( getX500NameBuilder().build().getEncoded() );
        JcaX509v3CertificateBuilder certBuilder = getJcaX509v3CertificateBuilder( subjectDN, key );
        return new JcaX509CertificateConverter().getCertificate( certBuilder.build( new JcaContentSignerBuilder("SHA256WithRSAEncryption")
                                                                            .build( key.getPrivate() )));
    }
    /**
     * Создание X500Name для субъекта сертификата
     * @return X500NameBuilder
     */
    private X500NameBuilder getX500NameBuilder(){
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "klinika"); 
        builder.addRDN(BCStyle.O, "Mouse");         
        builder.addRDN(BCStyle.OU, "Mouse");           
        builder.addRDN(BCStyle.L, "Minsk");            
        builder.addRDN(BCStyle.ST, "null");         
        builder.addRDN(BCStyle.C, "BYN");   
        return builder;
    }
    /**
     * Создание JcaX509v3CertificateBuilder для субъекта сертификата
     * @param subjectDN - X500Principal
     * @param keyPair   - KeyPair
     * @return JcaX509v3CertificateBuilder
     */
    private JcaX509v3CertificateBuilder getJcaX509v3CertificateBuilder( X500Principal subjectDN, KeyPair keyPair ){
        long now = System.currentTimeMillis();
        Date startDate = new Date(now);
        Date endDate = new Date(now + 365 * 24 * 60 * 60 * 1000L); // Год действия
        return new JcaX509v3CertificateBuilder( subjectDN,
                                                BigInteger.valueOf(now),
                                                startDate,
                                                endDate,
                                                subjectDN,
                                                keyPair.getPublic());
    }
    
}
