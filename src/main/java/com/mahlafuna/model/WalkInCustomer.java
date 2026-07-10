package com.mahlafuna.model;

public class WalkInCustomer extends Person {
    private String id;
    private int amount;

    public WalkInCustomer(String email, String phoneNumber, String lastName, String firstName, String id, int amount) {
        super(email, phoneNumber, lastName, firstName);
        this.id = id;
        this.amount = amount;
    }
}
