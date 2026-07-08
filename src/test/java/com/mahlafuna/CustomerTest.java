package com.mahlafuna;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Customer and all three Customer subclasses.
 */
public class CustomerTest {
    private WalkInCustomer walkIn;
    private OnlineCustomer online;
    private TradeInCustomer tradeIn;

    @BeforeEach
    void setUp() {
        walkIn = new WalkInCustomer("Sizwe", "Sabisa", "sabisa@gmail.com", "071 111 1111", "C001", 65000);
        online = new OnlineCustomer("Stara", "Dambuza", "stara@gmail.com", "071 222 2222", "C002", 95000, "seputswa");
        tradeIn = new TradeInCustomer("Dzoyo", "Mpho", "mlungu@gmail.com", "071 333 3333", "C003", 45000, 40000);
    }

    @Test
    void customerIsAbstract() {
        assertTrue(Modifier.isAbstract(Customer.class.getModifiers()), "Customer must be declared abstract");
    }

    @Test
    void walkInCustomerIsAPerson() {
        assertInstanceOf(Person.class, walkIn);
    }

    @Test
    void onlineCustomerIsAPerson() {
        assertInstanceOf(Person.class, online);
    }

    @Test
    void tradeInCustomerIsAPerson() {
        assertInstanceOf(Person.class, tradeIn);
    }

    @Test
    void allCustomersAreCustomer() {
        assertInstanceOf(Customer.class, walkIn);
        assertInstanceOf(Customer.class, online);
        assertInstanceOf(Customer.class, tradeIn);
    }

    @Test
    void customerDefaultsToActive() {
        assertTrue(walkIn.active());
        assertTrue(online.active());
        assertTrue(tradeIn.active());
    }

    @Test
    void activateAndDeactivateWork() {
        walkIn.deactivate();
        assertFalse(walkIn.active());
        walkIn.activate();
        assertTrue(walkIn.active());
    }

    @Test
    void customerIdIsImmutable() {
        assertThrows(NoSuchMethodException.class,
                () -> Customer.class.getMethod("updateCustomerId", String.class),
                "customerId must be immutable — no updater");
    }
}
