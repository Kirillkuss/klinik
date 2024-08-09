package com.klinik.rest;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.klinik.entity.RehabilitationSolution;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "rehabilitation-treatments")
@Tag(name = "9. Rehabilitation Treatment", description = "Справочник: Реабилитационное лечение")
@ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = RehabilitationSolution.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",  content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
public interface IRehabilitationSolution {
    @GetMapping(value = "/all")
    @Operation( description = "Список всех реабилитационных лечений", summary = "Список всех Реабилитационных лечений")
    public ResponseEntity<List<RehabilitationSolution>> getAllRehabilitationSolution() throws Exception;
    @RequestMapping( method = RequestMethod.GET, value = "/find/{name}")
    @Operation( description = "Поиск по названию лечения", summary = "Поиск по названию лечения")
    public ResponseEntity<RehabilitationSolution> findByName( @Parameter( description = "Наименование лечения") String name ) throws Exception;
    @Operation( description = "Добавить способ лечения", summary = "Добавить способ лечения")
    @RequestMapping( method = RequestMethod.POST, value = "/add/{solution}")
    public ResponseEntity<RehabilitationSolution> save( RehabilitationSolution solution ) throws Exception;
    
}
