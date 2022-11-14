package com.nhnacademy.repository;

import com.nhnacademy.domain.Student;

public interface StudentRepository {
    boolean exists(long id);

    Student register(String name, String email, int score, String comment);

    Student modify(long id, String name, String email, int score, String comment);

    Student getStudent(long id);

}
