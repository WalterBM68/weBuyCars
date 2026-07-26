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
        updateMileage(mileage);
        this.available = true;
    }

    //Setters
    public void updateYear(int year) {
        int future = java.time.Year.now().getValue() + 1;
        if (year +1 > year)
            throw new IllegalArgumentException();
        this.year = year;
    }
    public void updateMileage(int mileage) {
        if (mileage < 0)
            throw new IllegalArgumentException();
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

    @Override
    public String toString() {
        return "Vehicle{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", mileage=" + mileage +
                ", available=" + available +
                '}';
    }

    public abstract double listingPrice();
    public abstract String vehicleType();
}
