package com.klinik.rest;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.klinik.aspect.security.annotation.SecuredControl;
import com.klinik.request.UserRequest;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.response.UserResponse;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag( name = "USERS", description = "CRUD USERS" )
@RequestMapping( "users" )
@ApiResponses(value = {
    @ApiResponse( responseCode = "200", description = "Success",            content = { @Content( mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = UserResponse.class ))) }),
    @ApiResponse( responseCode = "400", description = "Bad request",        content = { @Content( mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
    @ApiResponse( responseCode = "500", description = "System malfunction", content = { @Content( mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
public interface IUser {

    @RequestMapping( method = RequestMethod.GET )
    @Operation( description = "List users", summary = " List Users ")
    public ResponseEntity<List<UserResponse>> getUsers();

    @RequestMapping(method = RequestMethod.POST)
    @Operation( description = "Добавить user", summary = "Добавить user")
    public ResponseEntity<UserResponse> addUser( @RequestBody UserRequest userRequest);

    @RequestMapping(method = RequestMethod.GET, path = "/keys")
    @Operation( description = "Обновить ключи public.pem and private.pem", summary = "Обновить ключи public.pem and private.pem")
    public ResponseEntity<BaseResponse> updateKeys() throws Exception;
    
    @RequestMapping(method = RequestMethod.GET, path = "/keystore")
    @Operation( description = "Обновить keystore", summary = "Обновить keystore")
    public ResponseEntity<BaseResponse> updateKeystore() throws Exception;
    
    @RequestMapping(method = RequestMethod.GET, path = "/encrypt")
    @Operation( description = "Обновить encryption", summary = "Обновить encryption")
    public ResponseEntity<BaseResponse> updateEncryption() throws Exception;
    @RequestMapping(method = RequestMethod.GET, path = "/database/keys")
    @Operation( description = "Обновить ключи в базе", summary = "Обновить ключи в базе")
    public ResponseEntity<BaseResponse> updateDataBase() throws Exception;
    
}
