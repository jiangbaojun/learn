package com.mrk.rmq.entity;

import java.io.Serializable;

/**
 * @Author: jiangbaojun
 * @Date: 2020/3/6 15:26
 */
public class User implements Serializable {

    private final long serialVersionUID = 1l;

    private String name;
    private Integer age;

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
