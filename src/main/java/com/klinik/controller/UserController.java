package com.klinik.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.request.UserRequest;
import com.klinik.response.BaseResponse;
import com.klinik.response.UserResponse;
import com.klinik.rest.IUser;
import com.klinik.security.generate.GenerateEncryption;
import com.klinik.security.generate.GenerateKeys;
import com.klinik.security.generate.GenerateKeysDataBase;
import com.klinik.security.generate.GenerateKeystore;
import com.klinik.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements IUser {

    private final UserService          userService;
    private final GenerateKeys         generateKey;
    private final GenerateKeystore     generateKeystore;
    private final GenerateEncryption   saltGenerator;
    private final GenerateKeysDataBase generateKeysDataBase;

    @Override
    public ResponseEntity<UserResponse> addUser( UserRequest userRequest) {
        System.out.println(userRequest);
        return ResponseEntity.status(HttpStatus.OK)
                             .body( userService.addUser( userRequest ));
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResponseEntity<BaseResponse> updateKeys() throws Exception {
        generateKey.generatePemKeys();
        return ResponseEntity.status(HttpStatus.OK)
                             .body( new BaseResponse<>( 200, "success"));
    }
    @SuppressWarnings("rawtypes")
    @Override
    public ResponseEntity<BaseResponse> updateKeystore() throws Exception {
        generateKeystore.updateKeyToKeystore();
        return ResponseEntity.status(HttpStatus.OK)
                             .body( new BaseResponse<>( 200, "success"));
    }

    @Override
    public ResponseEntity<BaseResponse> updateEncryption() throws Exception {
        saltGenerator.updateEncryptionKey();
        return ResponseEntity.status(HttpStatus.OK)
                             .body( new BaseResponse<>( 200, "success"));
    }

    @Override
    public ResponseEntity<BaseResponse> updateDataBase() throws Exception {
        generateKeysDataBase.updateKeyToDataBase();
        return ResponseEntity.status(HttpStatus.OK)
                             .body( new BaseResponse<>( 200, "success"));
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.status( HttpStatus.OK ).body( userService.getUser() );
    }
    
}
