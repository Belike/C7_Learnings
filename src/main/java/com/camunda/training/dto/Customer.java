package com.camunda.training.dto;

public class Customer {
    public Customer(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }
    public Customer(){

    }

    private String name;
    private String lastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
