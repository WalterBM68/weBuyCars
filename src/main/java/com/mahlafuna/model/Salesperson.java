package com.mahlafuna.model;

public class Salesperson extends Staff {
    private int dealsClosed;

    public static final double BASE_SALARY = 15_000.00;
    public static final double COMMISSION_PER_DEAL = 750.00;

    public Salesperson(String firstName, String lastName, String email, String phoneNumber, String employeeId, int yearsOfService) {
        super(firstName, lastName, email, phoneNumber, employeeId, yearsOfService);
        this.dealsClosed = 0;
    }

    public int dealsClosed() {
        return dealsClosed;
    }
    public void recordDeal() {
        this.dealsClosed+=1;
    }
    public void resetDeals() {
        this.dealsClosed = 0;
    }

    @Override
    public String role() {
        return "Salesperson";
    }

    @Override
    public double monthlySalary() {
        return BASE_SALARY + (dealsClosed * COMMISSION_PER_DEAL);
    }

    @Override
    public String duties() {
        return "Customer liaison, vehicle sales, and deal negotiation";
    }

    @Override
    public String toString() {
        return "Salesperson{" +
                "dealsClosed=" + dealsClosed +
                "employeeId=" + employeeId() +
                '}';
    }
}
