package com.mahlafuna;

import com.mahlafuna.model.*;
import com.mahlafuna.service.Dealership;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Dealership dealership = new Dealership("WeBuyCars Foreshore");

        // Vehicles
        dealership.addVehicle(new Sedan("CA 123-456", "Toyota", "Corolla", 2019, 45000, 4));
        dealership.addVehicle(new SUV("CA 789-012",   "Ford",   "Everest", 2021, 30000, true));
        dealership.addVehicle(new Bakkie("CA 345-678","Toyota", "Hilux",   2020, 60000, true));

        // Customers
        dealership.addCustomer(new WalkInCustomer("Alice",  "Dlamini","alice@email.com", "071 111 1111","C001", 85000));
        dealership.addCustomer(new OnlineCustomer("Bob",    "Nkosi",  "bob@email.com",   "071 222 2222","C002", 85000, "bob_nkosi"));
        dealership.addCustomer(new TradeInCustomer("Carol", "Botha",  "carol@email.com", "071 333 3333","C003", 55000, 10000));

        // Staff
        dealership.addStaff(new Salesperson("Dave",  "Sithole","dave@wbc.co.za",  "071 444 4444","E001", 2));
        dealership.addStaff(new Valuator("Eve","Khumalo","eve@wbc.co.za","071 555 5555","E002", 5, "NADA Certified"));

        // Record a deal for the salesperson
        Salesperson dave = (Salesperson) dealership.findStaff("E001");
        if (dave != null) {
            dave.recordDeal();
            dave.recordDeal();
        }

        System.out.println("=== " + dealership.dealershipName() + " ===");
        System.out.println("\nTotal stock value:    R" + dealership.totalStockValue());
        System.out.println("Total commission due: R" + dealership.totalCommissionDue());

        System.out.println("\n-- All Roles --");
        List<Person> everyone = new ArrayList<>();
        everyone.addAll(dealership.customers());
        everyone.addAll(dealership.staffList());
        dealership.printAllRoles(everyone);

        System.out.println("\n-- Available Vehicles --");
        for (Vehicle v : dealership.availableVehicles()) {
            System.out.println("  " + v.vehicleType() + ": " + v.make() + " " + v.model()
                    + " | Listing: R" + v.listingPrice());
        }

        System.out.println("\n-- Active Customers --");
        for (Customer c : dealership.activeCustomers()) {
            System.out.println("  " + c.fullName() + " | " + c.role()
                    + " | Offer: R" + c.offerAmount());
        }
    }
}