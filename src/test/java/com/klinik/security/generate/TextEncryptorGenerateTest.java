package com.klinik.security.generate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса TextEncryptorGenerate")
@DisplayName( value = "Тестирование сервиса TextEncryptorGenerate")
public class TextEncryptorGenerateTest {

    @Mock private TextEncryptor         textEncryptor; 
          private TextEncryptorGenerate textEncryptorGenerate; 

    private String encryptionKey = "mEoZj1PunAz47uanpimKn27oRou8/prrZVaLAL2f/bI=";
    private String secret        = "12343576";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        textEncryptorGenerate = new TextEncryptorGenerate(); 
        setField(textEncryptorGenerate, "encryptionKey", encryptionKey);
        setField(textEncryptorGenerate, "secret", secret);
    }

    @Test
    @DisplayName("Тестирование метода textEncryptor")
    public void testTextEncryptorBean() {
        assertNotNull(textEncryptorGenerate.textEncryptor()); 
    }
    /**
     * Изменение свойств полей
     * @param object - объект
     * @param fieldName - название поля
     * @param value - значение 
     */
    private void setField(Object object, String fieldName, Object value) {
        try {
            var field = object.getClass()
                              .getDeclaredField( fieldName );
                field.setAccessible(true);
                field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

/**@SpringBootTest
public class TextEncryptorGenerateTest {

    @Autowired 
    TextEncryptorGenerate textEncryptorGenerate;

    @Test
    @DisplayName("textEncryptor")
    public void testTextEncryptor(){
        textEncryptorGenerate.textEncryptor();
    }
    
}*/
