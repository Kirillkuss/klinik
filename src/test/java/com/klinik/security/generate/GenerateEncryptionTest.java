package com.klinik.security.generate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;

@Disabled
@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса GenerateEncryption")
@DisplayName( value = "Тестирование сервиса GenerateEncryption")
public class GenerateEncryptionTest {
    
    private GenerateEncryption generateEncryption;

    @BeforeEach
    public void setUp() {
        generateEncryption = new GenerateEncryption();
    }

    @Test
    @DisplayName("updateEncryptionKey")
    public void testUpdateEncryptionKey(){
        generateEncryption.updateEncryptionKey();
    }
}
