package com.klinik.controller.auth;

import com.klinik.entity.Role;
import com.klinik.entity.User;
import com.klinik.repositories.UserRepository;
import com.klinik.request.AuthRequest;
import com.klinik.response.AuthResponse;
import com.klinik.rest.auth.IAuthentication;
import com.klinik.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements IAuthentication {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtEncoder encoder;

    public ResponseEntity<AuthResponse> login( AuthRequest authRequest ) {
        Optional<User> request = userRepository.findByLogin(authRequest.getLogin());
        if ( request.isPresent() && userService.checkUserPassword( authRequest.getPassword(), request.get().getSalt(), request.get().getPassword())){
            String token = generateToken(authRequest);
            HttpHeaders httpHeaders = new HttpHeaders();
            //httpHeaders.set("X-AUTH-TOKEN", token);
            return ResponseEntity.ok()
                                 .headers(httpHeaders)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body( new AuthResponse( 200,"success", token ));
        }  else {
            return ResponseEntity.status( HttpStatus.UNAUTHORIZED )
                                 .contentType( MediaType.APPLICATION_JSON )
                                 .body( new AuthResponse( 401,"Invalid username or password", null ));
        }               
    }

    private String generateToken(AuthRequest authRequest) {
        Instant now = Instant.now();
        String scopes = Arrays.stream( Role.values() )
                              .map(Role::name)
                              .collect( Collectors.joining(" "));
        return this.encoder.encode( JwtEncoderParameters.from( JwtClaimsSet.builder()
                                                                           .issuer("self")
                                                                           .issuedAt(now)
                                                                           .expiresAt(now.plusSeconds(60000L))// five minutes
                                                                           .subject(authRequest.getLogin())
                                                                           .claim("scope", scopes)
                                                                           .build())).getTokenValue();
    }
}
