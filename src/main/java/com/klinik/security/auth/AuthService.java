package com.klinik.security.auth;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import com.klinik.entity.Blocking;
import com.klinik.entity.User;
import com.klinik.repositories.BlockingRepository;
import com.klinik.repositories.UserRepository;
import com.klinik.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * Сервис для проверки пользователя при авторизации
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository     userRepository;
    private final UserService        userService;
    private final BlockingRepository blockingRepository;

    private final String messageError      = "Слишком много неудачных попыток входа, попробуйте позже!";
    private final String messageErrorCount = "Неправильное имя пользователя или пароль, количество попыток: ";

    private final int maxAttempts = 3; 
    public final Map<String, Integer> failedAttempts = new HashMap<>();
    /**
     * Проверка пользователя
     * @param login - логин
     * @param password - пароль
     * @return User
     */
    public User checkUser( String login, String password) {
        int currentAttempts = failedAttempts.getOrDefault(login, 0);
        Optional<User> user = userRepository.findByLogin(login);
        validateUserLogin( login );
        validateAttempts( currentAttempts );
        validateUserStatus( user );
        validateUserCredentials( user, login, password, currentAttempts );
        failedAttempts.remove( login );
        return user.orElseThrow();
    }
    /**
     * Проверка на количество попыток
     * @param currentAttempts - номер попытки 
     */
    private void validateAttempts(int currentAttempts) {
        if (currentAttempts >= maxAttempts) {
            throw new BadCredentialsException( messageError );
        }
    }
    /**
     * Проверка пользователя на статус блокировки
     * @param user - пользователь
     */
    private void validateUserStatus(Optional<User> user) {
        user.ifPresent(u -> {
            if (u.getStatus()) {
                throw new BadCredentialsException( messageError );
            }
        });
    }

    private void validateUserLogin( String login ){
        userRepository.findByLogin( login ).orElseThrow( () -> new BadCredentialsException( "Неправильное имя пользователя или пароль!" ));
        failedAttempts.remove( login ); 
    }
    /**
     * Проверка на наличие пользователя в БД и корректности ввода пароля
     * @param user - пользователь
     * @param login - логин
     * @param password - пароль
     * @param currentAttempts - номер попытки
     */
    private void validateUserCredentials(Optional<User> user, String login, String password, int currentAttempts) {
        if (user.isEmpty() || !userService.checkUserPassword(password, user.get().getSalt(), user.get().getPassword())) {
            handleFailedAttempt(login, currentAttempts);
        }
    }
    /**
     * Счетчик на количество попыток и блокировка если неудачное количество попыток ввода 
     * @param login - логин
     * @param currentAttempts - количество попыток
     */
    private void handleFailedAttempt(String login, int currentAttempts) {
        currentAttempts++;
        failedAttempts.put(login, currentAttempts);
        int remainingAttempts = maxAttempts - currentAttempts;
        if (remainingAttempts == 0) {
            addBlocking( login );
            failedAttempts.remove(login);
            throw new BadCredentialsException(messageError);
        } else {
            throw new BadCredentialsException(messageErrorCount + remainingAttempts);
        }
    }
    /**
     * Блокировка пользователя 
     * @param login - логин
     */
    private void addBlocking( String login ){
        userService.blockUser( login );
        User user = userRepository.findByLogin(login).orElseThrow();
        Random random = new Random();
        blockingRepository.save( new Blocking( random.nextLong(),
                                               LocalDateTime.now(),
                                               LocalDateTime.now().plusMinutes( 5 ),
                                               null,
                                               true,
                                               1,
                                               user ));
        log.info( "user blocking with id:" + user.getId());                                
    }
    /**
     * Разблокировка пользователя по таймеру
     */
    @Scheduled(initialDelay = 5000, fixedRate = 60000) 
    public void unblockUser() { 
        blockingRepository.unblockBlocking();
        List<Long> userId =  blockingRepository.getBlockStatus( LocalDateTime.now().minusMinutes( 15 ), LocalDateTime.now() );
        if( ! userId.isEmpty() ){
            userId.stream().forEach( t -> {
                Optional<User> user = userRepository.findById( t );
                if( user.isPresent() ){
                    User request = user.orElseThrow();
                    if( request.getStatus() == true ){
                        request.setStatus( false );
                        userRepository.save( request );
                        log.info("unblock user with id: " + t + " TIME >> " + LocalDateTime.now());
                    }
                    blockingRepository.unblockBlockingStatus();
                }
            });
        }
    }

}
