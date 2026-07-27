package com.mahlafuna.service;

import com.mahlafuna.model.Customer;
import com.mahlafuna.model.Staff;
import com.mahlafuna.model.Vehicle;

import java.util.*;

public class Dealership {
    private final String dealershipName;
    private List<Vehicle> inventory;
    private List<Customer> customers;
    private List<Staff> staffList;

    public Dealership(String dealershipName) {
        this.dealershipName = dealershipName;
        this.customers = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.staffList = new ArrayList<>();
    }

    public String dealershipName() {
        return dealershipName;
    }

    public List<Vehicle> inventory() {
        return Collections.unmodifiableList(inventory);
    }

    public List<Customer> customers() {
        return Collections.unmodifiableList(customers);
    }

    public List<Staff> staffList() {
        return Collections.unmodifiableList(staffList);
    }
}
