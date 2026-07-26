package com.mahlafuna.model;

public class SUV extends Vehicle {
    private final boolean isAllWheelDrive;

    public static final double BASE_PRICE = 150000.00;
    public static final double PRICE_PER_KM = 2.00;
    public static final double AWD_PREMIUM = 15000.00;
    public static final double MINIMUM_PRICE = 40000.00;

    public SUV(String registrationNumber, String make, String model, int year, int mileage, boolean isAllWheelDrive) {
        super(registrationNumber, make, model, year, mileage);
        this.isAllWheelDrive = isAllWheelDrive;
    }

    public boolean allWheelDrive() {
        return isAllWheelDrive;
    }

    @Override
    public double listingPrice() {
        if (!AWD_PREMIUM)
        return Math.max(BASE_PRICE - mileage() * PRICE_PER_KM + AWD_PREMIUM, MINIMUM_PRICE);
    }

    @Override
    public String vehicleType() {
        return "SUV";
    }
}
