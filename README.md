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

---

#### `TradeInCustomer`

|                                    | Details                                                                                                             |
|------------------------------------|---------------------------------------------------------------------------------------------------------------------|
| Extra fields                       | `tradeInValue (double)` and `outstandingFinance (double)` — both mutable. Constructor throws if either is negative. |
| `updateTradeInValue(double)`       | Throws if negative                                                                                                  |
| `updateOutstandingFinance(double)` | Throws if negative                                                                                                  |
| `offerAmount()`                    | Returns `tradeInValue - outstandingFinance`. Returns `0.0` if the result would be negative.                         |
| `customerType()`                   | Returns `"Trade-In"`                                                                                                |
| `role()`                           | Returns `"Trade-In Customer"`                                                                                       |
| `generateInvoice()`                | Includes the net offer amount                                                                                       |

> **Design Tip — Interface vs Abstract Class:** `Billable` is an interface because not every `Person` is billable — only customers are. An abstract class would force every subclass (including `Staff`) to deal with invoicing, which makes no sense. Use an interface when a capability applies to some types across different branches of a hierarchy.

---

### Step 5 — Implement `Staff` (Abstract)

**File:** `src/main/java/com/mahlafuna/model/Staff.java`

#### Additional Fields

| Field            | Type     | Immutable?                                          |
|------------------|----------|-----------------------------------------------------|
| `employeeId`     | `String` | Yes                                                 |
| `yearsOfService` | `int`    | No — `updateYearsOfService(int)` throws if negative |

#### Abstract Methods

| Method            | Details                                       |
|-------------------|-----------------------------------------------|
| `monthlySalary()` | Returns monthly gross salary                  |
| `duties()`        | Returns a description of key responsibilities |

---

### Step 6 — Implement `Salesperson` and `Valuator`

---

#### `Salesperson`

|                       | Details                                                           |
|-----------------------|-------------------------------------------------------------------|
| Extra field           | `dealsClosed (int)` — defaults to `0`                             |
| `BASE_SALARY`         | `public static final double BASE_SALARY = 15_000.00`              |
| `COMMISSION_PER_DEAL` | `public static final double COMMISSION_PER_DEAL = 750.00`         |
| `dealsClosed()`       | Returns deals closed this month                                   |
| `recordDeal()`        | Increments `dealsClosed` by 1                                     |
| `resetDeals()`        | Resets `dealsClosed` to 0                                         |
| `monthlySalary()`     | `BASE_SALARY + (dealsClosed × COMMISSION_PER_DEAL)`               |
| `duties()`            | Returns `"Customer liaison, vehicle sales, and deal negotiation"` |
| `role()`              | Returns `"Salesperson"`                                           |

---

#### `Valuator`

|                   | Details                                                                   |
|-------------------|---------------------------------------------------------------------------|
| Extra field       | `certification (String)` — **immutable**                                  |
| `BASE_SALARY`     | `public static final double BASE_SALARY = 20_000.00`                      |
| `BONUS_PER_YEAR`  | `public static final double BONUS_PER_YEAR = 500.00`                      |
| `certification()` | Returns certification. No updater.                                        |
| `monthlySalary()` | `BASE_SALARY + (yearsOfService × BONUS_PER_YEAR)`                         |
| `duties()`        | Returns `"Vehicle inspection, condition reporting, and price assessment"` |
| `role()`          | Returns `"Valuator (" + certification + ")"`                              |

---

### Step 7 — Implement `Vehicle` (Abstract)

**File:** `src/main/java/com/mahlafuna/model/Vehicle.java`

#### Fields

| Field                | Type      | Immutable?                                                         |
|----------------------|-----------|--------------------------------------------------------------------|
| `registrationNumber` | `String`  | Yes                                                                |
| `make`               | `String`  | Yes                                                                |
| `model`              | `String`  | Yes                                                                |
| `year`               | `int`     | No — `updateYear(int)` throws if before 1900 or after current year |
| `mileage`            | `int`     | No — `updateMileage(int)` throws if negative                       |
| `available`          | `boolean` | No — `markSold()` / `markAvailable()`                              |

#### Abstract Methods

| Method           | Details                                   |
|------------------|-------------------------------------------|
| `listingPrice()` | Returns the calculated listing price      |
| `vehicleType()`  | Returns a short type label e.g. `"Sedan"` |

---
