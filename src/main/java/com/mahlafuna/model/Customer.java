package com.mahlafuna.model;

public abstract class Customer extends Person {
    private final String customerId;
    private boolean active;

    public Customer(String firstName, String lastName, String email, String phoneNumber, String customerId) {
        super(firstName, lastName, email, phoneNumber);
        this.customerId = customerId;
        this.active = true;
    }

    public String customerId() {
        return customerId;
    }

    public boolean active() {
        return active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public abstract double offerAmount();
    public abstract String customerType();
}
