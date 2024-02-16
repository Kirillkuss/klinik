package com.klinik.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.redis.model.Student;
import com.klinik.redis.rest.RestStudent;
import com.klinik.redis.service.StudentService;

@RestController
public class StudentController implements RestStudent{
    
    @Autowired
    StudentService studentService;

    @Override
    public ResponseEntity<Iterable<Student>> getAllStudents() {
        return ResponseEntity.ok( studentService.getAllStudent() );
    }

    @Override
    public ResponseEntity<Student> getMethodName( String id ) {
        return ResponseEntity.ok( studentService.getStudentById( id ));
    }

    @Override
    public ResponseEntity<Student> postMethodName(Student student) {
        return ResponseEntity.ok( studentService.getAddStudent( student ));
    }

}
