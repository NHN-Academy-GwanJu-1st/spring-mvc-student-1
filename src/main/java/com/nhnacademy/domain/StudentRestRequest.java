package com.nhnacademy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudentRestRequest {
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
