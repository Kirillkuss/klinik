package com.klinik.mapper;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import com.klinik.redis.model.Doctor;

@Component
public class DoctorImpl implements DoctorMapper{

    @Override
    public Doctor doctorToRedis( com.klinik.entity.Doctor doctor) {
        return new com.klinik.redis.model.Doctor( doctor.getIdDoctor().toString(), doctor, LocalDateTime.now() );
    }

    @Override
    public com.klinik.entity.Doctor redisToDoctor( Doctor doctor ) {
        return doctor.getDoctor();
    }
    
}
