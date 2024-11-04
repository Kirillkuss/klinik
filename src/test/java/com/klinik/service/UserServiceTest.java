package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.klinik.entity.Role;
import com.klinik.entity.User;
import com.klinik.repositories.UserRepository;
import com.klinik.request.UserRequest;
import com.klinik.service.mail.PasswordGenerator;
import java.util.Optional;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса UserService")
@DisplayName( value = "Тестирование сервиса UserService")
@SpringBootTest
public class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private PasswordGenerator passwordGenerator;

    private UserService userService;

    private final String TYPE     = "application/json";
    private final String RESULT   = "Результат: ";
    private final String ERROR    = "Ожидаемая ошибка :";
    private final String secret   = "123456789";
    private final String login    = "Mouse";
    private final String password = "Moti&34er";
    private final String encode   = "encodePassword";

    private User user;
    private UserRequest userRequest;
    private String salt;

    @BeforeEach
    public void setUp() {
        byte[] saltBytes = new byte[32];
        new SecureRandom().nextBytes(saltBytes);
        salt = Base64.getEncoder().encodeToString(saltBytes);
        MockitoAnnotations.openMocks(this);
        userService = new UserService( userRepository, passwordEncoder, passwordGenerator );
        user =  new User( login, passwordEncoder.encode( secret + password + salt ),Role.ADMIN,"Admin@mail.com",salt,false );
        userRequest = new UserRequest(login, password, "ADMIN", "Admin@mail.com");
    }

    @Test
    @DisplayName("Добавление пользователя")
    public void addUserSuccess() throws Exception{
        Allure.parameter("userRequest", userRequest );
        Allure.parameter("user", user );
        when(passwordEncoder.encode(any(String.class))).thenReturn( encode );
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
        assertNotNull( userService.addUser( userRequest ));
        Allure.addAttachment( RESULT, TYPE, userService.addUser( userRequest ).toString());
        verify(userRepository, times(2)).save(any(User.class));
        verify(passwordEncoder, times(3)).encode(secret + userRequest.getPassword() + any(String.class)); 
    }

    @Test
    @DisplayName("Проверка корректности ввода логина")
    public void testAddUserErrorUserName() {
        Allure.parameter("userRequest", userRequest );
        when(userRepository.findByLogin(userRequest.getLogin())).thenReturn(Optional.of( user ));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
        () -> userService.addUser(userRequest));
        assertEquals("Not unique username, please specify another!", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage());
    }

    @Test
    @DisplayName("Проверка корректности ввода почты")
    public void testAddUserInvalidEmailFormat() {
        userRequest.setEmail("test-email");
        Allure.parameter("userRequest", userRequest );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
        () -> userService.addUser(userRequest));
        assertEquals("Invalid email format!", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage());
    }

    @Test
    @DisplayName("Провекра почты на уникальность")
    public void testAddUserDuplicateEmail() {
        Allure.parameter("userRequest", userRequest );
        when(userRepository.findByLogin(userRequest.getLogin())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(user));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
        () -> userService.addUser(userRequest));
        assertEquals("Email already in use, please specify another!", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage());
    }

    @Test
    @DisplayName("Проверка корректности ввода пароля")
    public void testAddUserInvalidPassword() {
        userRequest.setPassword( "test");
        Allure.parameter("userRequest", userRequest );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
        () -> userService.addUser(userRequest));
        assertEquals("Неверный формат пароля! Пароль должен сожедржать не менее 8 символов, 1 букву верхнего и нижнего реестра, 1 цифру и 1 спец. символ ( *[@#$^&+=!№:?:%*(;_)}{]", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage());
    }

    @Test
    @DisplayName("Проверка роли")
    public void testAddUserInvalidRole() {
        userRequest.setRole( "YES");
        Allure.parameter("userRequest", userRequest );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
        () -> userService.addUser(userRequest));
        assertEquals("Invalid role!", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage());
    }

    @Test
    @DisplayName("Проверка пароля")
    public void testCheckUserPassword(){
        Allure.parameter("rawPassword", password);
        Allure.parameter( "salt", salt);
        Allure.parameter("encodePassword", encode );
        when( userService.checkUserPassword( "Moti&34er", salt, encode )).thenReturn( true );
        Allure.addAttachment( RESULT, TYPE, String.valueOf(userService.checkUserPassword( password, salt, encode)));
    }

    @Test
    @DisplayName("Проверка пароля")
    public void testBlockUserSuccess(){
        Allure.parameter( "login", login );
        when( userRepository.findByLogin( login )).thenReturn( Optional.of( user ));
        user.setStatus( true );
        when( userRepository.save( user )).thenReturn( user );
        userService.blockUser( login );
    }

    @Test
    @DisplayName("Проверка пароля - вызов ошибки")
    public void testBlockUserError(){
        Allure.parameter( "login", login );
        when( userRepository.findByLogin( login )).thenReturn( Optional.empty());
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, 
        () -> userService.blockUser( login ));
        assertEquals( "Not found user!", exception.getMessage());
    }

    @Test
    @DisplayName("Получение спика спользователей")
    public void testGetUserSuccess(){
        when( userRepository.findAll() ).thenReturn( List.of( user ));
        assertNotNull( userService.getUser());
        Allure.addAttachment(RESULT, TYPE, userService.getUser().toString());
        verify( userRepository, times(2 )).findAll();
    }


}
