package com.example.spring.jackson.model;


public class Saler {
    private String name;
    private String address;

    public Saler() {
    }

    public Saler(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Saler{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
