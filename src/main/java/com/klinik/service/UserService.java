package com.klinik.service;

import java.security.SecureRandom;
import java.util.Base64;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klinik.entity.Role;
import com.klinik.entity.User;
import com.klinik.repositories.UserRepository;
import com.klinik.request.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${spring.security.secret}")
    private String secret;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    protected void init(){
        String salt = generateSalt();
        User user = new User(null,
                          "admin",
                                passwordEncoder.encode( secret + "admin" + salt ),
                                Role.ADMIN,
                          "Admin@mail.com",
                                salt );
        if ( userRepository.findByLogin( user.getLogin() ).isEmpty() ){
            userRepository.save(  user );
            log.info( "init main user ");
        } 
    }
 
    /**
     * Генерация соли
     * @return String
     */
    private String generateSalt() {
        byte[] saltBytes = new byte[32];
        new SecureRandom().nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
    /**
     * Доавление пользователя 
     * @param user - пользователь 
     * @return User
     */
    public User addUser( UserRequest userRequest   ){
        validateUsername( userRequest.getLogin() );
        validateEmail( userRequest.getEmail());
        validatePassword( userRequest.getPassword() );
        validRole(userRequest.getRole() );
        String salt = generateSalt();
        return userRepository.save( new User( userRequest.getLogin(),
                                              passwordEncoder.encode( secret + userRequest.getPassword() + salt ),
                                              Role.valueOf( userRequest.getRole() ),
                                              userRequest.getEmail(),
                                              salt ));
    }
    /**
     * Проверка пароля при авторизации
     * @param rawPassword
     * @param salt
     * @param encodedPassword
     * @return boolean
     */
    public boolean checkUserPassword(String rawPassword, String salt, String encodedPassword) {
        return passwordEncoder.matches( secret + rawPassword + salt, encodedPassword);
    }
    /**
     * Проверка размера и кол-во символов для пароля
     * @param password
     * @return boolean
     */
    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*[A-Za-z].*") && password.matches(".*\\d.*");
    }
    /**
     * Провекра на корректность ввода почты
     * @param email  - почта
     * @return  boolean
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,4}$";
        return email != null && email.matches(emailRegex);
    }
    /**
     * Валидация логина
     * @param username - логин
     */
    private void validateUsername( String username){
        if (userRepository.findByLogin(username).isPresent()) {
            throw new IllegalArgumentException("Not unique username, please specify another!");
        }
    }
    /**
     * Валидация почты
     * @param email - почта
     */
    private void validateEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format!");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already in use, please specify another!");
        }
    }
    /**
     * Валидация пароля
     * @param password - пароль
     */
    private void validatePassword(String password) {
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password does not meet complexity requirements!");
        }
    }
    /**
     * Валидация роли
     * @param value - роль
     * @return boolean
     */
    private boolean isValidRole(String value) {
        for (Role role : Role.values()) {
            if (role.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Валидация роли
     * @param role - роль
     */
    private void validRole( String role ){
        if( !isValidRole( role )){
            throw new IllegalArgumentException("Invalid role!");
        }
    }

}
