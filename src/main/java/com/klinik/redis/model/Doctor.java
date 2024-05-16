package com.klinik.redis.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RedisHash(value ="Doctor")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Doctor implements Serializable {
    
    @Id
    private String id;
    private com.klinik.entity.Doctor doctor;
    private LocalDateTime startTime;
    @TimeToLive
    private Long timer = 10L;

    public Doctor( String id, com.klinik.entity.Doctor doctor, LocalDateTime startTime ){
       // this.id = UUID.randomUUID().toString();
        this.id = id; 
        this.doctor = doctor;
        this.startTime = startTime;
    }

    public Doctor( String id, com.klinik.entity.Doctor doctor, LocalDateTime startTime,Long timer ){
        this.id = id;
        this.doctor = doctor;
        this.startTime = startTime;
        this.timer = timer;
    }
}
