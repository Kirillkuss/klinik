package com.klinik.rest;

import com.klinik.response.AuthResponse;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@ApiResponses(value = {
        @ApiResponse( responseCode = "200" , description = "Authentication success", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = AuthResponse.class ))) }),
        @ApiResponse( responseCode = "400", description = "Bad Request",             content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "System malfunction",      content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
})
public interface IAuthentication {
    /**
     * Страница входа
     */
    @GetMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String login();
    /**
     * Обработчик по очистке ошибке и перевод на страницу авторизации
     * @param request
     * @return
     */
    @PostMapping(value = "clear-error-message", produces = MediaType.APPLICATION_JSON_VALUE)
    public String clearErrorMessage(HttpServletRequest request);
}
