package com.nhnacademy.domain;

import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.*;

@Getter
public class StudentRegisterRequest {

    @NotBlank
    private String name;

    @Email
    private String email;

    @Min(0)
    @Max(100)
    private int score;

    @Size(min = 0, max = 200)
    private String comment;

    public StudentRegisterRequest() {

    }

    public StudentRegisterRequest(String name, String email, int score, String comment) {
        this.name = name;
        this.email = email;
        this.score = score;
        this.comment = comment;
    }

}
