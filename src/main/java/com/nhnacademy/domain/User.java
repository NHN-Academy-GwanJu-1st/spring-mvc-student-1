package com.nhnacademy.domain;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter
    private final String id;

    @Getter
    private final String password;

    @Getter
    @Setter
    private int age;

    @Getter
    @Setter
    private String name;

    public static User create(String id, String password) {
        return new User(id, password);
    }

    private User(String id, String password) {
        this.id = id;
        this.password = password;
    }


}
