package com.nhnacademy.controller;

import com.nhnacademy.domain.Student;
import com.nhnacademy.domain.StudentRegisterRequest;
import com.nhnacademy.repository.StudentRepository;
import com.nhnacademy.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentRestController {

    private final StudentRepository studentRepository;

    public StudentRestController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public void postStudents(StudentRegisterRequest studentRequest) {
        studentRepository.register(
                studentRequest.getName(),
                studentRequest.getEmail(),
                studentRequest.getScore(),
                studentRequest.getComment());

    }

    @GetMapping("/students/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public Student getStudent(@PathVariable(value = "studentId") long studentId) {
        return studentRepository.getStudent(studentId);
    }

    @PutMapping("/students/{studentId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Student modifyStudent(
            @PathVariable(value = "studentId") long studentId,
            @RequestBody StudentRegisterRequest studentRequest) {

        Student student = studentRepository.getStudent(studentId);

        return studentRepository.modify(
                studentId,
                studentRequest.getName(),
                studentRequest.getEmail(),
                studentRequest.getScore(),
                studentRequest.getComment());
    }

}
