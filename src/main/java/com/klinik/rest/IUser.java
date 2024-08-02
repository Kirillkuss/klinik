package com.klinik.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.klinik.entity.User;
import com.klinik.request.UserRequest;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement( name = "Bearer Authentication" )
@Tag( name = "USERS", description = "CRUD USERS" )
@RequestMapping( "users" )
@ApiResponses(value = {
    @ApiResponse( responseCode = "200", description = "Success",            content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
    @ApiResponse( responseCode = "400", description = "Bad request",        content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
    @ApiResponse( responseCode = "500", description = "System malfunction", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
public interface IUser {

    @RequestMapping(method = RequestMethod.POST)
    @Operation( description = "Добавить user", summary = "Добавить user")
    public ResponseEntity<User> addUser( @RequestBody UserRequest userRequest);

    @RequestMapping(method = RequestMethod.GET, path = "/keys")
    @Operation( description = "Обновить ключи", summary = "Обновить ключи")
    public ResponseEntity<BaseResponse> updateKeys() throws Exception;
    
    @RequestMapping(method = RequestMethod.GET, path = "/keystore")
    @Operation( description = "Обновить keystore", summary = "Обновить keystore")
    public ResponseEntity<BaseResponse> updateKeystore() throws Exception; 
    
}
