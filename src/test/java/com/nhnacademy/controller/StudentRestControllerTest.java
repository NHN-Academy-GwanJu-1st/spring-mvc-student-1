package com.nhnacademy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.nhnacademy.domain.Student;
import com.nhnacademy.domain.StudentRestRequest;
import com.nhnacademy.exception.ValidationFailedException;
import com.nhnacademy.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentRestControllerTest {

    private MockMvc mockMvc;
    private StudentRepository studentRepository;

    private ObjectMapper objectMapper;
    private XmlMapper xmlMapper;

    private StudentRestRequest testStudent;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        xmlMapper = new XmlMapper();

        testStudent = new StudentRestRequest("test", "test@test.com", 90, "test comment");

        studentRepository = mock(StudentRepository.class);

        mockMvc = MockMvcBuilders.standaloneSetup(new StudentRestController(studentRepository))
                .build();
    }

    @Test
    void studentPost_json() throws Exception {

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testStudent)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void studentPost_xml() throws Exception{

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_XML)
                        .content(xmlMapper.writeValueAsString(testStudent)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void studentGet_return_json() throws Exception {

        long studentId = 1;
        Student student = Student.create(
                testStudent.getName(),
                testStudent.getEmail(),
                testStudent.getScore(),
                testStudent.getComment()
                );

        when(studentRepository.getStudent(studentId)).thenReturn(student);

        mockMvc.perform(get("/students/" + studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.score").value(90))
                .andExpect(jsonPath("$.comment").value("test comment"));

    }

    @Test
    void studentPutTest() throws Exception {
        long studentId = 1;
        StudentRestRequest modifyStudent = new StudentRestRequest("modifyName", "modify@test.com", 50, "modify comment");

        Student student = Student.create(
                modifyStudent.getName(),
                modifyStudent.getEmail(),
                modifyStudent.getScore(),
                modifyStudent.getComment()
        );
        student.setId(studentId);

        Student beforeStudent = Student.create(
                testStudent.getName(),
                testStudent.getEmail(),
                testStudent.getScore(),
                testStudent.getComment()
        );
        beforeStudent.setId(studentId);


        when(studentRepository.register("test", "test@test.com", 100, "test comment")).thenReturn(beforeStudent);
        when(studentRepository.modify(studentId, modifyStudent.getName(), modifyStudent.getEmail(), modifyStudent.getScore(), modifyStudent.getComment())).thenReturn(student);

        mockMvc.perform(put("/students/" + studentId)
                        .content(objectMapper.writeValueAsString(modifyStudent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("modifyName"))
                .andExpect(jsonPath("$.email").value("modify@test.com"))
                .andExpect(jsonPath("$.score").value(50))
                .andExpect(jsonPath("$.comment").value("modify comment"));

    }



}