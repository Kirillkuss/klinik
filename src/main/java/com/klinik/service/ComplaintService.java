package com.klinik.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.klinik.entity.Сomplaint;
import com.klinik.repositories.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository repository;

    @PersistenceContext
    EntityManager em;

    public Сomplaint findById( Long id ) throws Exception{
        return (Сomplaint) em.createQuery("SELECT u FROM Сomplaint u where u.id_complaint = :id")
                             .setParameter("id", id )
                             .getResultList()
                             .stream().findFirst().orElse( null );
    }

    public List<Сomplaint> listComplaints() throws Exception{
        return repository.findAll();
    }


}
