package com.nhnacademy.domain;

import lombok.Value;

import javax.validation.constraints.*;

@Value
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
}
