package com.mahlafuna.model;

public class Bakkie extends Vehicle {
    private final boolean isDoubleCab;

    public static final double BASE_PRICE = 120_000.00;
    public static final double PRICE_PER_KM = 1.80;
    public static final double DOUBLE_CAB_PREMIUM = 10_000.00;
    public static final double MINIMUM_PRICE = 30_000.00;

    public Bakkie(String registrationNumber, String make, String model, int year, int mileage, boolean isDoubleCab) {
        super(registrationNumber, make, model, year, mileage);
        this.isDoubleCab = isDoubleCab;
    }

    public boolean doubleCab() {
        return isDoubleCab;
    }

    @Override
    public double listingPrice() {
        return Math.max(BASE_PRICE - mileage() * PRICE_PER_KM + DOUBLE_CAB_PREMIUM, MINIMUM_PRICE);
    }

    @Override
    public String vehicleType() {
        if (isDoubleCab)
            return "Bakkie (Double Cab)";
        else return "Bakkie (Single Cab)";
    }
}
