package com.mahlafuna.model;

public class Valuator extends Staff {
    private String certification;

    public static final double BASE_SALARY = 20_000.00;
    public static final double BONUS_PER_YEAR = 500.00;

    public Valuator(String firstName, String lastName, String email, String phoneNumber, String employeeId, int yearsOfService, String certification) {
        super(firstName, lastName, email, phoneNumber, employeeId, yearsOfService);
        this.certification = certification;
    }

    public String certification() {
        return certification;
    }

    @Override
    public String role() {
        return "Valuator (" + certification + ")";
    }

    @Override
    public double monthlySalary() {
        return BASE_SALARY + (yearsOfService() * BONUS_PER_YEAR);
    }

    @Override
    public String duties() {
        return "Vehicle inspection, condition reporting, and price assessment";
    }

    @Override
    public String toString() {
        return "Valuator{" +
                "certification='" + certification + '\'' +
                '}';
    }
}
