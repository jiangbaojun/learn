package com.example.spring.jackson.model;

import java.util.Date;

public class Car {
    private String brand;
    private Integer doors;
    private Date produceDate;
    private Saler saler;

    public Car() {
    }

    public Car(String brand, Integer doors) {
        this.brand = brand;
        this.doors = doors;
    }

    public Car(Date produceDate) {
        this.produceDate = produceDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public Date getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    public Saler getSaler() {
        return saler;
    }

    public void setSaler(Saler saler) {
        this.saler = saler;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", doors=" + doors +
                ", produceDate=" + produceDate +
                ", saler=" + saler +
                '}';
    }
}
