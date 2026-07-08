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

    @Test
    void customerIdReturnsCorrectValue() {
        assertEquals("C001", walkIn.customerId());
        assertEquals("C002", online.customerId());
        assertEquals("C003", tradeIn.customerId());
    }

    @Test
    void walkInOfferAmountIsOfferedPrice() {
        assertEquals(85000, walkIn.offerAmount(), 0.001);
    }

    @Test
    void walkInUpdateOfferedPriceChangesValue() {
        walkIn.updateOfferedPrice(90000);
        assertEquals(90000, walkIn.offerAmount(), 0.001);
    }

    @Test
    void walkInUpdateOfferedPriceThrowsForNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> walkIn.updateOfferedPrice(-1));
    }

    @Test
    void walkInConstructorThrowsForNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> new WalkInCustomer("X", "Y", "x@y.com", "000", "C999", -500));
    }

    @Test
    void walkInCustomerTypeIsCorrect() {
        assertEquals("Walk-In", walkIn.customerType());
    }

    @Test
    void walkInRoleIsCorrect() {
        assertEquals("Walk-In Customer", walkIn.role());
    }

    @Test
    void walkInGenerateInvoiceContainsNameAndAmount() {
        String invoice = walkIn.generateInvoice();
        assertTrue(invoice.contains("Alice Dlamini"), "Invoice must contain full name");
        assertTrue(invoice.contains("85000"),         "Invoice must contain offer amount");
    }

    @Test
    void walkInIsBillable() {
        assertInstanceOf(Billable.class, walkIn,
                "WalkInCustomer must implement Billable");
    }

    @Test
    void walkInInvoiceThroughBillableReference() {
        Billable b = new WalkInCustomer("Alice", "Dlamini", "alice@email.com", "071 111 1111", "C001", 85000);
        assertTrue(b.generateInvoice().contains("Alice Dlamini"),
                "generateInvoice() must work through a Billable reference");
    }

    @Test
    void onlineOfferAmountAppliesFivePercentFee() {
        double expected = 85000 * OnlineCustomer.ONLINE_FEE_MULTIPLIER;
        assertEquals(expected, online.offerAmount(), 0.001);
    }

    @Test
    void onlineUpdateBaseOfferChangesOfferAmount() {
        online.updateBaseOffer(100000);
        double expected = 100000 * OnlineCustomer.ONLINE_FEE_MULTIPLIER;
        assertEquals(expected, online.offerAmount(), 0.001);
    }

    @Test
    void onlineUpdateBaseOfferThrowsForNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> online.updateBaseOffer(-1));
    }

    @Test
    void onlinePortalUsernameIsImmutable() {
        assertThrows(NoSuchMethodException.class,
                () -> OnlineCustomer.class.getMethod("updatePortalUsername", String.class),
                "portalUsername must be immutable — no updater");
    }

    @Test
    void onlinePortalUsernameReturnsCorrectValue() {
        assertEquals("bob_nkosi", online.portalUsername());
    }

    @Test
    void onlineCustomerTypeIsCorrect() {
        assertEquals("Online", online.customerType());
    }

    @Test
    void onlineRoleIsCorrect() {
        assertEquals("Online Customer", online.role());
    }

    @Test
    void onlineIsBillable() {
        assertInstanceOf(Billable.class, online,
                "OnlineCustomer must implement Billable");
    }

    @Test
    void onlineInvoiceReflectsDiscountedAmount() {
        String invoice = online.generateInvoice();
        assertTrue(invoice.contains("Bob Nkosi"), "Invoice must contain full name");
        double expected = 85000 * OnlineCustomer.ONLINE_FEE_MULTIPLIER;
        assertTrue(invoice.contains(String.valueOf(expected)),
                "Invoice must reflect the discounted offer amount");
    }

    @Test
    void tradeInOfferAmountIsNetValue() {
        assertEquals(45000, tradeIn.offerAmount(), 0.001);
    }

    @Test
    void tradeInOfferAmountIsNeverNegative() {
        TradeInCustomer c = new TradeInCustomer("X","Y","x@y.com","000","C999", 10000, 50000);
        assertEquals(0.0, c.offerAmount(), 0.001,
                "offerAmount() must return 0.0 when finance exceeds trade-in value");
    }

    @Test
    void tradeInUpdateTradeInValueChangesOfferAmount() {
        tradeIn.updateTradeInValue(70000);
        assertEquals(60000, tradeIn.offerAmount(), 0.001);
    }

    @Test
    void tradeInUpdateTradeInValueThrowsForNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> tradeIn.updateTradeInValue(-1));
    }

    @Test
    void tradeInUpdateOutstandingFinanceThrowsForNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> tradeIn.updateOutstandingFinance(-1));
    }

    @Test
    void tradeInConstructorThrowsForNegativeValues() {
        assertThrows(IllegalArgumentException.class,
                () -> new TradeInCustomer("X","Y","x@y.com","000","C999",-1, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new TradeInCustomer("X","Y","x@y.com","000","C999", 0, -1));
    }
}
