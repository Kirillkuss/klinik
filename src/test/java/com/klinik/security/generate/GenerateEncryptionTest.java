package com.klinik.security.generate;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;

//@Disabled
@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса GenerateEncryption")
@DisplayName( value = "Тестирование сервиса GenerateEncryption")
@SpringBootTest
public class GenerateEncryptionTest {
    
    @Autowired
    private GenerateEncryption generateEncryption;

    @Test
    @DisplayName("updateEncryptionKey")
    public void testUpdateEncryptionKey(){
        generateEncryption.updateEncryptionKey();
    }
}
