package com.klinik.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import com.klinik.entity.User;
import com.klinik.repositories.UserRepository;
import com.klinik.service.UserService;
import java.util.Map;
import org.springframework.context.annotation.Lazy;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Optional;
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

    private final UserRepository userRepository;
    private final UserService userService;

    private final int maxAttempts = 3; 
    private final Map<String, Integer> failedAttempts = new HashMap<>(); 

    public CustomAuthenticationProvider(UserRepository userRepository, @Lazy UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        // Получение количества неудачных попыток для пользователя
        int currentAttempts = failedAttempts.getOrDefault(username, 0);
        System.out.println( "currentAttempts >> " + currentAttempts);
        // Проверка на превышение максимального количества попыток
        if (currentAttempts >= maxAttempts) {
            throw new BadCredentialsException("Слишком много неудачных попыток входа");
        }

        Optional<User> user = userRepository.findByLogin(username);

        if( user.isEmpty() ){
            currentAttempts++;
            failedAttempts.put(username, currentAttempts);
            throw new BadCredentialsException("Неправильное имя пользователя или пароль");
        }

        if (!userService.checkUserPassword( password, user.get().getSalt(), user.get().getPassword() )) {
            currentAttempts++;
            failedAttempts.put(username, currentAttempts);
            throw new BadCredentialsException("Неправильное имя пользователя или пароль");
        }
        failedAttempts.remove(username);
        return new UsernamePasswordAuthenticationToken( username, password, Arrays.asList( new SimpleGrantedAuthority("ROLE_" + user.get().getRole())));
    }

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
    
}
