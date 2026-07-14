package com.mahlafuna.model;

public class Salesperson extends Person {
    private int dealsClosed;

    public Salesperson(String firstName, String lastName, String email, String phoneNumber) {
        super(firstName, lastName, email, phoneNumber);
        this.dealsClosed = 0;
    }
}
