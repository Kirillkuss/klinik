package com.klinik.controller;

import com.klinik.entity.Doctor;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping( value = "Doctors")
@RestController
@Tag(name = "1. Doctors", description = "Доктора:")
public class DoctorController {

    @Autowired
    private DoctorService service;

    @GetMapping(value = "/getAll")
    @Operation( description = "Список всех докторов", summary = "Список всех докторов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получение списка всех докторов", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Doctor.class))) })
    })
    public String  getAllDoc() throws Exception{
        return service.allDoctor().toString();
    }

    @GetMapping(value = "/FindDoctorByFIO")
    @Operation( description = "Поиск врача по ФИО", summary = "Поиск врача по ФИО")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Поиск доктора по ФИО", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Doctor.class))) })
    })
    public String findByFIO(@Parameter( description = "ФИО врача") String word ) throws Exception{
        try{
            List<Doctor> doctors = service.findByFIO( word );
            if( doctors.isEmpty() == true ) throw new MyException( 999, "По данному запросу ничего не найдено");
            return doctors.toString();
        }catch( MyException ex ){
            return ex.getMessage();
        }

    }

    @PostMapping( value = "/addDoctor")
    @Operation( description = "Добавить доктора", summary = "Добавить доктора")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Доктор добавлен", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Doctor.class))) })})
    public String addDoctor( Doctor doctor ) throws Exception{
        try{
            if (service.findById( doctor.getId_doctor() ) != null ) throw new MyException( 999, "Пользователь с таким ИД уще существует");
            Doctor response =  service.saveDoctor( doctor );
            return response.toString();
        }catch( MyException ex ){
            return  ex.getMessage();
        }
        
    }
}
