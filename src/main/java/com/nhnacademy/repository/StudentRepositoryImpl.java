package com.nhnacademy.repository;

import com.nhnacademy.domain.Student;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class StudentRepositoryImpl implements StudentRepository {

    private final Map<Long, Student> students = new HashMap<>();

    @Override
    public boolean exists(long id) {
        return students.containsKey(id);
    }

    @Override
    public Student register(String name, String email, int score, String comment) {
        long id = students.keySet()
                .stream()
                .max(Comparator.comparing(Function.identity()))
                .map(l -> l + 1)
                .orElse(1L);

        Student student = Student.create(name, email, score, comment);
        student.setId(id);

        students.put(id, student);

        return student;
    }

    @Override
    public Student modify(long id, String name, String email, int score, String comment) {

        Student student = Student.create(name, email, score, comment);
        student.setId(id);
        students.replace(id, student);

        return students.get(id);
    }

    @Override
    public Student getStudent(long id) {
        return students.get(id);
    }
}
