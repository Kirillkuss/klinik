package com.klinik.redis.model;

import java.io.Serializable;
import org.springframework.data.redis.core.RedisHash;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RedisHash(value ="Cache")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cache implements Serializable {

    @Id
    private String id;

    private String description;
    
}
