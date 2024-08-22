package com.klinik.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.klinik.entity.KeyEntity;

@Repository
public interface KeyEntityRepository  extends JpaRepository<KeyEntity, Long> {

    @Query( "SELECT ke FROM KeyEntity ke WHERE ke.alice = :alice")
    Optional<KeyEntity> findByKeyAlias( String alice );
    
}
