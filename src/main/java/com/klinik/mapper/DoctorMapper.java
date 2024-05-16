package com.klinik.mapper;

import org.mapstruct.Mapper;
import com.klinik.entity.Doctor;

@Mapper
public interface DoctorMapper {

    com.klinik.redis.model.Doctor doctorToRedis( Doctor doctor );
    Doctor redisToDoctor( com.klinik.redis.model.Doctor doctor );
}
