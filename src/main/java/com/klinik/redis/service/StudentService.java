package com.klinik.redis.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klinik.redis.model.Student;
import com.klinik.redis.repository.StudentRepository;

@Service
public class StudentService {
    
    @Autowired
    StudentRepository studentRepository;

    public Iterable<Student> getAllStudent(){
        return studentRepository.findAll();
    }

    public Student getStudentById( String id ){
        return studentRepository.findById( id ).orElseThrow( () -> new NoSuchElementException("Студент не найден"));
    }

    public Student getAddStudent( Student student ){
        return studentRepository.save( student );
    }

}
