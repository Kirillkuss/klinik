package com.klinik.rest;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.klinik.entity.Rehabilitation_solution;
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
public interface IRehabilitationSolution {
    @GetMapping(value = "/all")
    @Operation( description = "Список всех реабилитационных лечений", summary = "Список всех Реабилитационных лечений")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список всех реабилитационных лечений", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Rehabilitation_solution.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                                content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                               content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseEntity<List<Rehabilitation_solution>> getAllRehabilitationSolution() throws Exception;
    @RequestMapping( method = RequestMethod.GET, value = "/find/{name}")
    @Operation( description = "Поиск по названию лечения", summary = "Поиск по названию лечения")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получено реабилитационное лечение по наименованию", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Rehabilitation_solution.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                                     content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                                    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseEntity<Rehabilitation_solution> findByName( @Parameter( description = "Наименование лечения") String name ) throws Exception;
    @Operation( description = "Добавить способ лечения", summary = "Добавить способ лечения")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавлен способ лечения", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Rehabilitation_solution.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",           content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",          content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @RequestMapping( method = RequestMethod.POST, value = "/add/{solution}")
    public ResponseEntity<Rehabilitation_solution> save( Rehabilitation_solution solution ) throws Exception;
    
}
