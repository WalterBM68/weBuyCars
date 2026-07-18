package com.mahlafuna.model;

public class Sedan extends Vehicle {
    private final int numberOfDoors;

    public static final double BASE_PRICE = 80000.00;
    public static final double PRICE_PER_KM = 1.50;
    public static final double MINIMUM_PRICE = 20000.00;

    public Sedan(String registrationNumber, String make, String model, int year, int mileage, int numberOfDoors) {
        super(registrationNumber, make, model, year, mileage);
        this.numberOfDoors = numberOfDoors;
    }

    public int numberOfDoors() {
        if (numberOfDoors < 2)
            throw new IllegalArgumentException();
        else return numberOfDoors;
    }

    @Override
    public double listingPrice() {
        return Math.max(BASE_PRICE - mileage() * PRICE_PER_KM, MINIMUM_PRICE);
    }

    @Override
    public String vehicleType() {
        return "Sedan";
    }
}
