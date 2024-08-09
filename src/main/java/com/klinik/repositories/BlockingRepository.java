package com.klinik.repositories;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.klinik.entity.Blocking;

@Repository
public interface BlockingRepository extends JpaRepository<Blocking, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Blocking b \n" + 
           "SET b.status = false, b.statusBlock = 2, b.dateUnblock = current_timestamp \n" + 
           "WHERE b.status = true AND b.datePlanUnblock <= current_timestamp")
    public void unblockBlocking();

    @Query( "SELECT distinct (b.user.id) FROM  Blocking b WHERE b.status = false and statusBlock = 2 and (b.dateUnblock between :from and :to) " )
    public List<Long> getBlockStatus(LocalDateTime from , LocalDateTime to );

    @Transactional
    @Modifying
    @Query("UPDATE Blocking b \n" + 
           "SET b.statusBlock = 3\n" + 
           "WHERE b.status = false and b.statusBlock = 2")
    public void unblockBlockingStatus();


}
