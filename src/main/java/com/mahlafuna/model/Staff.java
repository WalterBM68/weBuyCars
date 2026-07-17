package com.mahlafuna.model;

public abstract class Staff extends Person {
    private final String employeeId;
    private int yearsOfService;

    public Staff(String firstName, String lastName, String email, String phoneNumber, String employeeId, int yearsOfService) {
        super(firstName, lastName, email, phoneNumber);
        this.employeeId = employeeId;
        updateYearsOfService(yearsOfService);
    }

    public void updateYearsOfService(int yearsOfService) {
        if (yearsOfService < 0)
            throw new IllegalArgumentException();
        else this.yearsOfService = yearsOfService;
    }

    public String employeeId() {
        return employeeId;
    }

    public int yearsOfService() {
        return yearsOfService;
    }

    @Override
    public String role() {
        return "Staff";
    }

    public abstract double monthlySalary();
    public abstract String duties();
}
