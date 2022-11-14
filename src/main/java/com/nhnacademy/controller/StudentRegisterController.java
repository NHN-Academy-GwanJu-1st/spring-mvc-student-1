package com.nhnacademy.controller;

import com.nhnacademy.domain.Student;
import com.nhnacademy.domain.StudentRegisterRequest;
import com.nhnacademy.exception.ValidationFailedException;
import com.nhnacademy.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@Controller
@RequestMapping("/student/register")
public class StudentRegisterController {
    private final StudentRepository studentRepository;

    public StudentRegisterController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public String studentRegisterForm() {
        return "studentRegister";
    }

    @PostMapping
    public ModelAndView registerStudent(@Valid @ModelAttribute(value = "student") StudentRegisterRequest studentRequest,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        ModelAndView modelAndView = new ModelAndView("studentView");
        Student student = studentRepository.register(
                studentRequest.getName(),
                studentRequest.getEmail(),
                studentRequest.getScore(),
                studentRequest.getComment()
        );

        modelAndView.addObject("student", student);
        return modelAndView;
    }

}
