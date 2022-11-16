package com.nhnacademy.controller;

import com.nhnacademy.domain.Student;
import com.nhnacademy.domain.StudentRegisterRequest;
import com.nhnacademy.exception.StudentNotFoundException;
import com.nhnacademy.exception.ValidationFailedException;
import com.nhnacademy.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {

    private static String HIDE_CHECK = "yes";
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @ModelAttribute("student")
    public Student getStudent(@PathVariable(value = "studentId") long studentId) {
        Student student = studentRepository.getStudent(studentId);
        if (Objects.isNull(student)) {
            throw new StudentNotFoundException();
        }
        return student;
    }

    @GetMapping("/{studentId}")
    public String viewStudent(
            @ModelAttribute(value = "student") Student student,
            Model model) {

        model.addAttribute("student", student);
        model.addAttribute("hideCheck", false);
        return "view/studentView";
    }

    @GetMapping(value = "/{studentId}", params = {"hideScore"})
    public String viewStudentHideScore(@RequestParam(value = "hideScore", required = false) String hideCheck,
                                       @ModelAttribute(value = "student") Student student,
                                       Model model) {

        if (!hideCheck.equals(HIDE_CHECK)) {
            return "studentView";
        }

        model.addAttribute("hideCheck", true);

        return "view/studentView";
    }


    @GetMapping("/{studentId}/modify")
    public String studentModifyForm(
            @ModelAttribute(value = "student") Student student,
            Model model) {

        model.addAttribute("student", student);

        return "view/studentModify";
    }

    @PostMapping("/{studentId}/modify")
    public String modifyStudent(@Valid @ModelAttribute StudentRegisterRequest studentRequest,
                             @PathVariable(value = "studentId") long studentId,
                             Model model,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Student modified = studentRepository.modify(
                studentId,
                studentRequest.getName(),
                studentRequest.getEmail(),
                studentRequest.getScore(),
                studentRequest.getComment()
        );

        model.addAttribute("student", modified);

        return "view/studentView";
    }


    @ExceptionHandler(StudentNotFoundException.class)
    public String handleStudentNotFound(StudentNotFoundException ex, Model model) {
        log.error("Call StudentController handleStudentNotFound : {}", ex);
        model.addAttribute("exception", ex);
        return "view/error";
    }
}
