# 🚗 WeBuyCars — Java OOP Assessment

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