package com.klinik.security.auth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import com.klinik.entity.User;
import com.klinik.security.auth.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
@Getter
@Setter
public class KlinikaAuthenticationProvider implements AuthenticationProvider{

    private final AuthService authService;
    private String login;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String code = authentication.getCredentials().toString();
        Integer parsedCode;
        if( login == null ) throw new BadCredentialsException("User not found!!!");
        try {
            parsedCode = Integer.parseInt( code ); 
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid code!!!");
        }
        User user = authService.verifyUserTotp( login, parsedCode );
        login = null;
        return new UsernamePasswordAuthenticationToken( user, code, Arrays.asList( new SimpleGrantedAuthority("ROLE_" + user.getRole())));
    }

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}


}
