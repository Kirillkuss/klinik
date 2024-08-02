package com.klinik.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.entity.User;
import com.klinik.request.UserRequest;
import com.klinik.response.BaseResponse;
import com.klinik.rest.IUser;
import com.klinik.security.GenerateKeys;
import com.klinik.security.GenerateKeystore;
import com.klinik.security.SaltGenerator;
import com.klinik.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements IUser {

    private final UserService userService;
    private final GenerateKeys generateKey;
    private final GenerateKeystore generateKeystore;
    private final SaltGenerator saltGenerator;

    @Override
    public ResponseEntity<User> addUser( UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body( userService.addUser( userRequest ));
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResponseEntity<BaseResponse> updateKeys() throws Exception {
        generateKey.generateKeys();
        return ResponseEntity.status(HttpStatus.OK)
                             .body( new BaseResponse<>( 200, "success"));
    }
    @SuppressWarnings("rawtypes")
    @Override
    public ResponseEntity<BaseResponse> updateKeystore() throws Exception {
        saltGenerator.addKeys();
        generateKeystore.updateKeyToKeystore();
        return ResponseEntity.status(HttpStatus.OK)
                             .body( new BaseResponse<>( 200, "success"));
    }
    
}
