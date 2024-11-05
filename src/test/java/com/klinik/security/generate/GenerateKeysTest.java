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
public class GenerateKeysTest {

    private GenerateKeys generateKeys;
    
    @BeforeEach
    public void setUp() {
        generateKeys = new GenerateKeys();
    }

    @Test
    @DisplayName("Гененирование ключей")
    public void testgeneratePemKeys() throws Exception{
        generateKeys.generatePemKeys();

    }
    
}
