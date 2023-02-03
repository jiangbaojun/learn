package com.example.spring.entity;

public class User {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void outName() {
        System.out.println("the name is:"+name);;
        new User2().who();
    }
}
