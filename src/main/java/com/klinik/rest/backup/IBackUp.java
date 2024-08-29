package com.klinik.rest.backup;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.entity.CardPatient;
import com.klinik.excep.MyException;
import com.klinik.request.CoplaintRequest;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "buckup")
@Tag(name = "BuckUp", description = "BuckUp")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( array = @ArraySchema(schema = @Schema(implementation = BaseResponse.class ))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос ", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
@SecurityRequirement(name = "Bearer Authentication")
public interface IBackUp {
    @SuppressWarnings("rawtypes")
    @GetMapping(value = "/dump")
    @Operation( description = "Dump", summary = "Dump")
    public ResponseEntity<BaseResponse> getDump() throws Exception;

    @SuppressWarnings("rawtypes")
    @GetMapping(value = "/restore")
    @Operation( description = "Restore", summary = "Restore")
    public ResponseEntity<BaseResponse> getRestore() throws Exception;
    
}
