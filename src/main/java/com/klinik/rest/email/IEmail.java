package com.klinik.rest.email;

import javax.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.request.email.EmailRequest;
import com.klinik.response.AuthResponse;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "email")
@Tag(name = "Emails ", description = "Электронная почта")
@ApiResponses(value = {
    @ApiResponse( responseCode = "200" , description = "Email sends success", content = { @Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = AuthResponse.class ))) }),
    @ApiResponse( responseCode = "400", description = "Bad Request",          content = { @Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
    @ApiResponse( responseCode = "500", description = "System malfunction",   content = { @Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
})
public interface IEmail {

    @PostMapping( value = "send", produces = MediaType.APPLICATION_JSON )
    @Operation( description = "Отрпавить письмо на почту", summary = "Отрпавить письмо на почту")
    public ResponseEntity<BaseResponse> sendMessageToEmail(@RequestBody EmailRequest emailRequest);

    @PostMapping( value = "update", produces = MediaType.APPLICATION_JSON )
    @Operation( description = "Обновить пароль для пользователя", summary = "Обновить пароль для пользователя")
    public ResponseEntity<BaseResponse> updatePasswordUser( String word );
    
}
