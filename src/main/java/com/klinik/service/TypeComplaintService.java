package com.klinik.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.klinik.entity.TypeComplaint;
import com.klinik.entity.Complaint;
import com.klinik.repositories.ComplaintRepository;
import com.klinik.repositories.TypeComplaintRepository;
import lombok.RequiredArgsConstructor;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TypeComplaintService {

    private final ComplaintRepository     complaintRepository;
    private final TypeComplaintRepository typeComplaintRepository;

    public List<TypeComplaint> findAll(){
        return typeComplaintRepository.findAll();
    }

    public TypeComplaint saveTypeComplaint( TypeComplaint typeComplaint, Long idComplaint ) throws Exception{
        Optional<Complaint> complaint = complaintRepository.findById( idComplaint);
        checkSaveTypeComplaint( complaint, typeComplaint );
        typeComplaint.setComplaint( complaint.get() );
        return typeComplaintRepository.save( typeComplaint );
    }

    private void checkSaveTypeComplaint( Optional<Complaint> complaint,  TypeComplaint typeComplaint ){
        if( complaint.isEmpty() ) throw new IllegalArgumentException( "Неверный параметр, жалоба с таким ИД не существует");
        if( typeComplaintRepository.findName( typeComplaint.getName()).isPresent() ) throw new IllegalArgumentException( "Под жалоба с таким наименованием уже существует");
        if( typeComplaintRepository.findById( typeComplaint.getIdTypeComplaint()).isPresent() ) throw new IllegalArgumentException( "Под жалоба с таким ИД уже существует");
    }

     public List<TypeComplaint> findByIdComplaint( Long id ) throws Exception{
        if( complaintRepository.findById( id ).isEmpty()) throw new NoSuchElementException( "Жалобы с таким ИД не существует");
        return typeComplaintRepository.findByIdComplaint( id );
    }
}
