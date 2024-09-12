package com.klinik.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.klinik.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query( "SELECT u from User u WHERE u.login = :login")
    Optional<User> findByLogin( String login );

    @Query( "SELECT u from User u WHERE u.email = :email")
    Optional<User> findByEmail( String email );

    @Query("SELECT u FROM User u WHERE u.login = :word OR u.email = :word")
    Optional<User> findUserByLoginOrByMail( String word );
    
}
