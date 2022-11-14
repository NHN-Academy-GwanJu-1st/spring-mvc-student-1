package com.nhnacademy.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Student {

    @Setter
    private long id;
    private final String name;
    private final String email;
    private final int score;
    private final String comment;

    public static Student create(String name, String email, int score, String comment) {
        return new Student(name, email, score, comment);
    }

    private Student(String name, String email, int score, String comment) {
        this.name = name;
        this.email = email;
        this.score = score;
        this.comment = comment;
    }

}
