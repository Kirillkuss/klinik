package com.klinik.security.generate;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import com.klinik.entity.KeyEntity;
import com.klinik.repositories.KeyEntityRepository;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса GenerateEncryption")
@DisplayName( value = "Тестирование сервиса GenerateEncryption")
public class GenerateKeysDataBaseTest {
    
    @Mock private KeyEntityRepository keyEntityRepository;
    @Mock private TextEncryptor textEncryptor;

    private final String alice = "admin";
    private final String TYPE = "application/json";
    private final String REZULT = "результат: ";

    private GenerateKeysDataBase generateKeysDataBase;
    private KeyEntity keyEntity;

    @BeforeEach
    public void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        keyEntity = new KeyEntity(1L, alice, LocalDateTime.now(), "publicKey", "privateKey");
        generateKeysDataBase = new GenerateKeysDataBase( keyEntityRepository, textEncryptor );
        generateKeysDataBase.privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded()));
        generateKeysDataBase.publicKey = (RSAPublicKey) KeyFactory.getInstance( "RSA").generatePublic(new X509EncodedKeySpec( keyPair.getPublic().getEncoded()));
    }

    @Test
    @DisplayName("updateKeyToDataBase")
    public void testUpdateKeyToDataBase() throws Exception{
        Mockito.when( keyEntityRepository.findByKeyAlias( alice )).thenReturn( Optional.of( keyEntity ));
        generateKeysDataBase.updateKeyToDataBase();
        Mockito.when( keyEntityRepository.findByKeyAlias( alice )).thenReturn( Optional.empty());
        generateKeysDataBase.updateKeyToDataBase();
    }

    @Test
    @DisplayName("updateKeyToDataBaseFromPem")
    public void testUpdateKeyToDataBaseFromPem() throws Exception{
        Mockito.when( keyEntityRepository.findByKeyAlias( alice )).thenReturn( Optional.of( keyEntity ));
        generateKeysDataBase.updateKeyToDataBaseFromPem();
        Mockito.when( keyEntityRepository.findByKeyAlias( alice )).thenReturn( Optional.empty());
        generateKeysDataBase.updateKeyToDataBaseFromPem();
    }

    @Test
    @DisplayName("getKey")
    public void testSaveKey(){
        Mockito.when( keyEntityRepository.findByKeyAlias( alice )).thenReturn( Optional.of( keyEntity ) );
        assertNotNull( generateKeysDataBase.getKey( alice ));
        assertEquals(generateKeysDataBase.getKey( alice ), generateKeysDataBase.getKey( alice ));
    }

    @Test
    @DisplayName("getRSAPublicKey")
    public void testGetRSAPublicKeySuccess(){
        keyEntity.setPublicKey( Base64.getEncoder()
                                      .encodeToString( new PKCS8EncodedKeySpec( generateKeysDataBase.publicKey.getEncoded() )
                                      .getEncoded() ));
        Mockito.when( keyEntityRepository.findByKeyAlias( alice )).thenReturn( Optional.of( keyEntity ) );
        Mockito.when(textEncryptor.decrypt(keyEntity.getPublicKey())).thenReturn(keyEntity.getPublicKey());
        Allure.addAttachment(REZULT, TYPE, generateKeysDataBase.getRSAPublicKey().orElseThrow().toString());
    }

    @Test
    @DisplayName("getRSAPublicKey - Error")
    public void testGetRSAPublicKeyError(){
        Mockito.when( keyEntityRepository.findByKeyAlias( alice )).thenReturn( Optional.of( keyEntity ) );
        Mockito.when(textEncryptor.decrypt(keyEntity.getPublicKey())).thenReturn(keyEntity.getPublicKey());
        generateKeysDataBase.getRSAPublicKey();
    }


    @Test
    @DisplayName("getRSAPrivateKey")
    public void testGetRSAPrivateKeySuccess() throws Exception{
        keyEntity.setPrivateKey( Base64.getEncoder()
                                       .encodeToString( new PKCS8EncodedKeySpec( generateKeysDataBase.privateKey.getEncoded())
                                       .getEncoded() ));
        Mockito.when( keyEntityRepository.findByKeyAlias( alice )).thenReturn( Optional.of( keyEntity ) );
        Mockito.when(textEncryptor.decrypt(keyEntity.getPrivateKey())).thenReturn(keyEntity.getPrivateKey());
        Allure.addAttachment(REZULT, TYPE, generateKeysDataBase.getRSAPrivateKey().orElseThrow().toString());
    }

    @Test
    @DisplayName("getRSAPrivateKey - Error")
    public void testGetRSAPrivateKeyError() throws Exception{
        Mockito.when( keyEntityRepository.findByKeyAlias( alice )).thenReturn( Optional.of( keyEntity ) );
        Mockito.when(textEncryptor.decrypt(keyEntity.getPrivateKey())).thenReturn(keyEntity.getPrivateKey());
        generateKeysDataBase.getRSAPrivateKey();
    }

}
