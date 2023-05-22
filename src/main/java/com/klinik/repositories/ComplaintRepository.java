package com.klinik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klinik.entity.Сomplaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Сomplaint, Long> {
}
