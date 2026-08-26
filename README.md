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

```bash
mvn clean test
```

---

## Implementation Steps

---

## Step 1 - Implement `Billable` (Interface)

**File:** `src/main/java/com/mahlafuna/model/Billable.java`

`Billable` is an interface — not an abstract class. Think carefully about which types in the system should implement it and which should not, and why.

| Method              | Details                                                                                                                                           |
|---------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| `generateInvoice()` | Returns a formatted invoice string including the customer's full name and offer amount. Example: `"Invoice for Alice Dlamini \| Offer: R85000.0"` |

> **Design Question:** Why is `Billable` an interface rather than part of `Customer`? Which concrete classes should implement it?

---

### Step 2 - Implement `Person` (Abstract)

**File:** `src/main/java/com/mahlafuna/model/Person.java`

Root of the entire people hierarchy.

#### Fields

| Field         | Type     | Immutable?                       |
|---------------|----------|----------------------------------|
| `firstName`   | `String` | Yes                              |
| `lastName`    | `String` | Yes                              |
| `email`       | `String` | No — `updateEmail(String)`       |
| `phoneNumber` | `String` | No — `updatePhoneNumber(String)` |

#### Methods

| Method                      | Details                                                   |
|-----------------------------|-----------------------------------------------------------|
| `firstName()`               | Returns first name                                        |
| `lastName()`                | Returns last name                                         |
| `fullName()`                | Returns `firstName + " " + lastName`                      |
| `email()`                   | Returns email                                             |
| `phoneNumber()`             | Returns phone number                                      |
| `updateEmail(String)`       | Updates email                                             |
| `updatePhoneNumber(String)` | Updates phone number                                      |
| `toString()`                | Includes full name and email                              |
| `role()`                    | **Abstract** — every subclass provides its own role label |

---

### Step 3 - Implement `Customer` (Abstract)

**File:** `src/main/java/com/mahlafuna/model/Customer.java`

#### Additional Fields

| Field        | Type      | Immutable?                         |
|--------------|-----------|------------------------------------|
| `customerId` | `String`  | Yes                                |
| `active`     | `boolean` | No — `activate()` / `deactivate()` |

#### Constructor

Calls `super()`. Sets `active` to `true`.

#### Methods

| Method           | Details                                             |
|------------------|-----------------------------------------------------|
| `customerId()`   | Returns customer ID.                                |
| `active()`       | Returns active status                               |
| `activate()`     | Sets `active` to `true`                             |
| `deactivate()`   | Sets `active` to `false`                            |
| `offerAmount()`  | **Abstract** — each subclass calculates differently |
| `customerType()` | **Abstract** — returns a short channel label        |

---

### Step 4 - Implement `WalkInCustomer`, `OnlineCustomer`, `TradeInCustomer`

All three extend `Customer` and implement `Billable`. Use `@Override` on every overridden method.

---

#### `WalkInCustomer`

|                              | Details                                                            |
|------------------------------|--------------------------------------------------------------------|
| Extra field                  | `offeredPrice (double)` — mutable. Constructor throws if negative. |
| `offeredPrice()`             | Returns the offered price                                          |
| `updateOfferedPrice(double)` | Throws `IllegalArgumentException` if negative                      |
| `offerAmount()`              | Returns `offeredPrice`                                             |
| `customerType()`             | Returns `"Walk-In"`                                                |
| `role()`                     | Returns `"Walk-In Customer"`                                       |
| `generateInvoice()`          | `"Invoice for [fullName] \| Offer: R[offerAmount]"`                |

---

#### `OnlineCustomer`

|                           | Details                                                                    |
|---------------------------|----------------------------------------------------------------------------|
| Extra fields              | `baseOffer (double)` — mutable. `portalUsername (String)` — **immutable**. |
| `ONLINE_FEE_MULTIPLIER`   | `public static final double ONLINE_FEE_MULTIPLIER = 0.95`                  |
| `updateBaseOffer(double)` | Throws if negative                                                         |
| `portalUsername()`        | Returns username. No updater.                                              |
| `offerAmount()`           | Returns `baseOffer * 0.95`                                                 |
| `customerType()`          | Returns `"Online"`                                                         |
| `role()`                  | Returns `"Online Customer"`                                                |
| `generateInvoice()`       | Includes the discounted offer amount                                       |

