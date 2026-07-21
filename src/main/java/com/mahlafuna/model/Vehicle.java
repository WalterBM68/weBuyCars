package com.mahlafuna.model;

public abstract class Vehicle {
    private final String registrationNumber;
    private final String make;
    private final String model;
    private int year;
    private int mileage;
    private boolean available;

    public Vehicle(String registrationNumber, String make, String model, int year, int mileage) {
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.available = false;
    }

    //Setters
    public void updateYear(int year) {
        this.year = year;
    }
    public void updateMileage(int mileage) {
        this.mileage = mileage;
    }
    public void markSold() {
        this.available = false;
    }
    public void markAvailable() {
        this.available = true;
    }

    //Getters
    public int mileage() {
        return mileage;
    }
    public String make() {
        return make;
    }
    public String model() {
        return model;
    }
    public int year() {
        return year;
    }
    public String registrationNumber() {
        return registrationNumber;
    }
    public boolean available() { return available; }

    public abstract double listingPrice();
    public abstract String vehicleType();
}
