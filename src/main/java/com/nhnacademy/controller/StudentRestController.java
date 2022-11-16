package com.nhnacademy.controller;

import com.nhnacademy.domain.Student;
import com.nhnacademy.domain.StudentRestRequest;
import com.nhnacademy.exception.StudentNotFoundException;
import com.nhnacademy.exception.ValidationFailedException;
import com.nhnacademy.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@RestController
public class StudentRestController {

    private final StudentRepository studentRepository;

    public StudentRestController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public Student postStudents(@Valid @RequestBody StudentRestRequest studentRequest) {
        return studentRepository.register(
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
    @ResponseStatus(HttpStatus.OK)
    public Student modifyStudent(
            @PathVariable(value = "studentId") long studentId,
            @RequestBody StudentRestRequest studentRequest) {

        return studentRepository.modify(
                studentId,
                studentRequest.getName(),
                studentRequest.getEmail(),
                studentRequest.getScore(),
                studentRequest.getComment());
    }
}
