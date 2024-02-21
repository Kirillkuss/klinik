package com.klinik.redis.model;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.annotation.Id;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * From RedisTemplate
 */
@RedisHash(timeToLive = 10L)
///@RedisHash(timeToLive = 60, value ="Person")
@Getter
@Setter
@ToString
//@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Data
public class Person implements Serializable {

    @Id
    //@Hidden
    private String id ;
    @Schema( example = "Lee")
    private String name;
    @TimeToLive
    @Schema( example = "60")
    private Long time;

        public Person( String id, String name, Long time ){
        this.id  = id; // UUID.randomUUID().toString();
        this.name = name;
        this.time = time;
    }

}
