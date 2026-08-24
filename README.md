# 🚗 WeBuyCars — Java OOP Small Project

## Overview

In this small project I will design and implement a used car dealership system for WeBuyCars. The system manages two parallel class hierarchies — **People** (customers and staff) and **Vehicles** — through a shared set of OOP principles.

## Learning Objectives

- Encapsulation
- Inheritance
- Polymorphism
- Abstraction

---

## Class Hierarchy

```
Person  (abstract)
├── Customer  (abstract)
│   ├── WalkInCustomer   implements Billable
│   ├── OnlineCustomer   implements Billable
│   └── TradeInCustomer  implements Billable
└── Staff  (abstract)
    ├── Salesperson
    └── Valuator

Vehicle  (abstract)
├── Sedan
├── SUV
└── Bakkie

Interface: Billable
```

---

## Project Structure

```
webuycars/
  .gitignore
  pom.xml
  README.md
  src/
    main/java/com/mahlafuna/
      Main.java                             
      model/
        Billable.java                        
        Person.java                         
        Customer.java                       
        WalkInCustomer.java                 
        OnlineCustomer.java                 
        TradeInCustomer.java                
        Staff.java                          
        Salesperson.java                    
        Valuator.java                       
        Vehicle.java                        
        Sedan.java                          
        SUV.java                            
        Bakkie.java                         
      service/
        Dealership.java                     
    test/java/com/mahlafuna/
      PersonTest.java
      CustomerTest.java
      StaffTest.java
      VehicleTest.java
      DealershipTest.java
```

> **Do NOT modify any test files or `Main.java`.**

---

## Testing