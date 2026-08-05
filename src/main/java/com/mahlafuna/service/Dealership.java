package com.mahlafuna.service;

import com.mahlafuna.model.Customer;
import com.mahlafuna.model.Person;
import com.mahlafuna.model.Staff;
import com.mahlafuna.model.Vehicle;

import java.util.*;

public class Dealership {
    private final String dealershipName;
    private final List<Vehicle> inventory;
    private final List<Customer> customers;
    private final List<Staff> staffList;

    public Dealership(String dealershipName) {
        this.dealershipName = dealershipName;
        this.customers = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.staffList = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) { this.inventory.add(vehicle); }

    public void removeVehicle(String regNumber) {
        Vehicle found = findVehicle(regNumber);

        if (found == null) throw new IllegalArgumentException();
        else inventory.remove(found);
    }

    public Vehicle findVehicle(String regNumber) {
        for (Vehicle vehicle : inventory) {
            if (vehicle.registrationNumber().equals(regNumber))
                return vehicle;
        }
        return null;
    }

    public List<Vehicle> availableVehicles() {
        List<Vehicle> avail = new ArrayList<>();

        for (Vehicle vehicle : inventory) {
            if (vehicle.available())
                avail.add(vehicle);
        }
        return avail;
    }

    public void addCustomer(Customer customer) { this.customers.add(customer); }

    public Customer findCustomer(String customerId) {
        for (Customer customer : customers) {
            if (customer.customerId().equals(customerId))
                return customer;
        }
        return null;
    }

    public List<Customer> activeCustomers() {
        List<Customer> active = new ArrayList<>();

        for (Customer customer : customers) {
            if (customer.active())
                active.add(customer);
        }
        return active;
    }

    public void addStaff(Staff staff) { this.staffList.add(staff); }

    public Staff findStaff(String employeeId) {
        for (Staff staff : staffList) {
            if (staff.employeeId().equals(employeeId))
                return staff;
        }
        return null;
    }

    public String dealershipName() { return dealershipName; }
    public List<Vehicle> inventory() { return Collections.unmodifiableList(inventory); }
    public List<Customer> customers() { return Collections.unmodifiableList(customers); }
    public List<Staff> staffList() { return Collections.unmodifiableList(staffList); }

    public double totalStockValue() {
        double sum = 0.0;

        for (Vehicle vehicle : inventory) {
            if (vehicle.available())
                sum += vehicle.listingPrice();
        }
        return sum;
    }

    public double totalCommissionDue() {
        double sum = 0.0;

        for (Staff staff : staffList) {
            sum += staff.monthlySalary();
        }
        return sum;
    }

    public void printAllRoles(List<Person> people) {
        for (Person person : people)
            System.out.println(person.role());
    }
}
