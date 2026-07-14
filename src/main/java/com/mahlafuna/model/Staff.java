package com.mahlafuna.model;

public abstract class Staff {
    private final String employeeId;
    private int yearsOfService;

    public Staff(String employeeId, int yearsOfService) {
        this.employeeId = employeeId;
        updateYearsOfService(yearsOfService);
    }

    public void updateYearsOfService(int yearsOfService) {
        if (yearsOfService > 0)
            this.yearsOfService = yearsOfService;
        else throw new IllegalArgumentException();
    }

    public abstract double monthlySalary();
    public abstract String duties();
}
