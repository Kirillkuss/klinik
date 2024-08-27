package com.klinik.security.generate;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import com.klinik.entity.KeyEntity;
import com.klinik.repositories.KeyEntityRepository;

import io.qameta.allure.Epic;
import io.qameta.allure.Owner;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса GenerateEncryption")
@DisplayName( value = "Тестирование сервиса GenerateEncryption")
public class GenerateKeysDataBaseTest {
    

    @Mock private KeyEntityRepository keyEntityRepository;
    @Mock private TextEncryptor textEncryptor;
    
    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey; // Эмулируем, по сути мы их замокируем

    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey; // Эмулируем, по сути мы их замокируем

    private final String alice = "admin";


    private GenerateKeysDataBase generateKeysDataBase;
    private KeyEntity keyEntity;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
         keyEntity = new KeyEntity(1L, alice, LocalDateTime.now(), "publicKey", "privateKey");
        generateKeysDataBase = new GenerateKeysDataBase( keyEntityRepository, textEncryptor );
    }

    @Test
    @DisplayName("updateKeyToDataBase")
    public void testUpdateKeyToDataBase() throws Exception{
        Mockito.when( keyEntityRepository.findByKeyAlias( alice )).thenReturn( Optional.of( keyEntity ));
        generateKeysDataBase.updateKeyToDataBase();
        //generateKeysDataBase.updateKeyToDataBaseFromPem();
    }

    @Test
    @DisplayName("updateKeyToDataBaseFromPem")
    public void testUpdateKeyToDataBaseFromPem() throws Exception{
        generateKeysDataBase.privateKey = privateKey;
        generateKeysDataBase.publicKey = publicKey;
        Mockito.when( keyEntityRepository.findByKeyAlias( alice )).thenReturn( Optional.of( keyEntity ));
       // generateKeysDataBase.updateKeyToDataBaseFromPem();
    }

}
