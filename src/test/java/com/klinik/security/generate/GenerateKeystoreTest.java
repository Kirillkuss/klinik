package com.klinik.security.generate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;

//@Disabled
@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса GenerateKeys")
@DisplayName( value = "Тестирование сервиса GenerateKeys")
public class GenerateKeystoreTest {

    private GenerateKeystore generateKeystore ;

    @BeforeEach
    public void setUp() {
        generateKeystore = new GenerateKeystore();
    }

    @Test
    @DisplayName("Генерация ключей")
    public void testUpdateKeyToKeystore() throws Exception{
        generateKeystore.updateKeyToKeystore();
    }


    
}
