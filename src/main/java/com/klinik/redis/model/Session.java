package com.klinik.redis.model;

import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * From Spring Data Redis
 */
@RedisHash(value ="Session")
//@RedisHash(timeToLive = 60, value ="Session")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Session implements Serializable {

    @Id
    @Hidden
    private String id;
    @Schema( example = "The first key")
    private String name;
    @TimeToLive
    @Schema( example = "60")
    private Integer time;

    public Session( String id, String name, Integer time ){
        this.id  = UUID.randomUUID().toString();
        this.name = name;
        this.time = time;
    }
}
