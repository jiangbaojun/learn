package com.mrk.flux.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String name;
    private Integer age;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }
}
