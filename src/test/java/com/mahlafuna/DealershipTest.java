package com.mahlafuna;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Dealership service class — management and polymorphic reporting.
 */
public class DealershipTest {
    private Dealership    dealership;
    private Sedan         sedan;
    private SUV           suv;
    private WalkInCustomer  walkIn;
    private OnlineCustomer  online;
    private TradeInCustomer tradeIn;
    private Salesperson   salesperson;
    private Valuator      valuator;

    @BeforeEach
    void setUp() {
        dealership  = new Dealership("WeBuyCars Foreshore");
        sedan       = new Sedan("CA 123-456",  "Toyota","Corolla",2019,45000,4);
        suv         = new SUV("CA 789-012",    "Ford",  "Everest",2021,30000,true);
        walkIn      = new WalkInCustomer("Alice","Dlamini","alice@email.com","071 111 1111","C001",85000);
        online      = new OnlineCustomer("Bob","Nkosi","bob@email.com","071 222 2222","C002",85000,"bob_nkosi");
        tradeIn     = new TradeInCustomer("Carol","Botha","carol@email.com","071 333 3333","C003",55000,10000);
        salesperson = new Salesperson("Dave","Sithole","dave@wbc.co.za","071 444 4444","E001",2);
        valuator    = new Valuator("Eve","Khumalo","eve@wbc.co.za","071 555 5555","E002",5,"NADA Certified");
    }

    @Test
    void dealershipNameReturnsCorrectValue() {
        assertEquals("WeBuyCars Foreshore", dealership.dealershipName());
    }

    @Test
    void addVehicleIncreasesInventorySize() {
        dealership.addVehicle(sedan);
        assertEquals(1, dealership.inventory().size());
    }

    @Test
    void inventoryReturnsUnmodifiableView() {
        dealership.addVehicle(sedan);
        assertThrows(UnsupportedOperationException.class,
                () -> dealership.inventory().add(suv));
    }

    @Test
    void findVehicleReturnsCorrectVehicle() {
        dealership.addVehicle(sedan);
        Vehicle found = dealership.findVehicle("CA 123-456");
        assertNotNull(found);
        assertEquals("Corolla", found.model());
    }

    @Test
    void findVehicleReturnsNullForUnknownRegistration() {
        assertNull(dealership.findVehicle("UNKNOWN"));
    }

    @Test
    void removeVehicleDecreasesInventorySize() {
        dealership.addVehicle(sedan);
        dealership.addVehicle(suv);
        dealership.removeVehicle("CA 123-456");
        assertEquals(1, dealership.inventory().size());
    }

    @Test
    void removeVehicleThrowsForUnknownRegistration() {
        assertThrows(IllegalArgumentException.class,
                () -> dealership.removeVehicle("UNKNOWN"));
    }

    @Test
    void availableVehiclesReturnsOnlyAvailableStock() {
        dealership.addVehicle(sedan);
        dealership.addVehicle(suv);
        sedan.markSold();
        assertEquals(1, dealership.availableVehicles().size());
        assertEquals("Everest", dealership.availableVehicles().get(0).model());
    }

    @Test
    void availableVehiclesReturnsNewList() {
        dealership.addVehicle(sedan);
        dealership.availableVehicles().clear();
        assertEquals(1, dealership.inventory().size(),
                "availableVehicles() must return a new list, not a view of inventory");
    }

    @Test
    void addCustomerIncreasesCustomerCount() {
        dealership.addCustomer(walkIn);
        assertEquals(1, dealership.customers().size());
    }

    @Test
    void customersReturnsUnmodifiableView() {
        dealership.addCustomer(walkIn);
        assertThrows(UnsupportedOperationException.class,
                () -> dealership.customers().add(online));
    }

    @Test
    void findCustomerReturnsCorrectCustomer() {
        dealership.addCustomer(walkIn);
        dealership.addCustomer(online);
        Customer found = dealership.findCustomer("C002");
        assertNotNull(found);
        assertEquals("Bob", found.firstName());
    }

    @Test
    void findCustomerReturnsNullForUnknownId() {
        assertNull(dealership.findCustomer("UNKNOWN"));
    }

    @Test
    void activeCustomersReturnsOnlyActiveCustomers() {
        dealership.addCustomer(walkIn);
        dealership.addCustomer(online);
        online.deactivate();
        assertEquals(1, dealership.activeCustomers().size());
        assertEquals("C001", dealership.activeCustomers().get(0).customerId());
    }

    @Test
    void addStaffIncreasesStaffCount() {
        dealership.addStaff(salesperson);
        assertEquals(1, dealership.staffList().size());
    }

    @Test
    void staffListReturnsUnmodifiableView() {
        dealership.addStaff(salesperson);
        assertThrows(UnsupportedOperationException.class,
                () -> dealership.staffList().add(valuator));
    }

    @Test
    void findStaffReturnsCorrectStaff() {
        dealership.addStaff(salesperson);
        dealership.addStaff(valuator);
        Staff found = dealership.findStaff("E002");
        assertNotNull(found);
        assertEquals("Eve", found.firstName());
    }

    @Test
    void findStaffReturnsNullForUnknownId() {
        assertNull(dealership.findStaff("UNKNOWN"));
    }

    @Test
    void totalStockValueSumsAvailableVehicles() {
        dealership.addVehicle(sedan);
        dealership.addVehicle(suv);
        double expected = sedan.listingPrice() + suv.listingPrice();
        assertEquals(expected, dealership.totalStockValue(), 0.001);
    }

    @Test
    void totalStockValueExcludesSoldVehicles() {
        dealership.addVehicle(sedan);
        dealership.addVehicle(suv);
        sedan.markSold();
        assertEquals(suv.listingPrice(), dealership.totalStockValue(), 0.001,
                "totalStockValue() must only sum available vehicles");
    }

    @Test
    void totalStockValueIsZeroWithNoInventory() {
        assertEquals(0.0, dealership.totalStockValue(), 0.001);
    }

    @Test
    void totalCommissionDueSumsAllStaffSalaries() {
        dealership.addStaff(salesperson);
        dealership.addStaff(valuator);
        double expected = salesperson.monthlySalary() + valuator.monthlySalary();
        assertEquals(expected, dealership.totalCommissionDue(), 0.001);
    }

    @Test
    void totalCommissionDueIsZeroWithNoStaff() {
        assertEquals(0.0, dealership.totalCommissionDue(), 0.001);
    }

    @Test
    void totalCommissionDueReflectsRecordedDeals() {
        dealership.addStaff(salesperson);
        double before = dealership.totalCommissionDue();
        salesperson.recordDeal();
        assertTrue(dealership.totalCommissionDue() > before,
                "totalCommissionDue() must increase after a deal is recorded");
    }

    @Test
    void printAllRolesPrintsCorrectRolesForMixedList() {
        dealership.addCustomer(walkIn);
        dealership.addCustomer(online);
        dealership.addStaff(salesperson);
        dealership.addStaff(valuator);

        List<Person> everyone = new ArrayList<>();
        everyone.addAll(dealership.customers());
        everyone.addAll(dealership.staffList());

        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));

        dealership.printAllRoles(everyone);

        System.setOut(originalOut);
        String output = captured.toString();

        assertTrue(output.contains("Walk-In Customer"),
                "printAllRoles() must print the role of each WalkInCustomer");
        assertTrue(output.contains("Online Customer"),
                "printAllRoles() must print the role of each OnlineCustomer");
        assertTrue(output.contains("Salesperson"),
                "printAllRoles() must print the role of each Salesperson");
        assertTrue(output.contains("Valuator (NADA Certified)"),
                "printAllRoles() must print the role of each Valuator");
    }

    @Test
    void printAllRolesAcceptsMixedPersonList() {
        List<Person> people = new ArrayList<>();
        people.add(new WalkInCustomer("Alice","Dlamini","a@b.com","000","C001",85000));
        people.add(new Salesperson("Dave","Sithole","d@wbc.co.za","000","E001",2));

        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));

        assertDoesNotThrow(() -> dealership.printAllRoles(people),
                "printAllRoles() must handle a mixed List<Person> without errors");

        System.setOut(originalOut);
    }

    @Test
    void printAllRolesPrintsEveryPersonInList() {
        List<Person> people = new ArrayList<>();
        people.add(walkIn);
        people.add(salesperson);

        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));

        dealership.printAllRoles(people);

        System.setOut(originalOut);
        String output = captured.toString();

        long lineCount = output.lines().filter(l -> !l.isBlank()).count();
        assertEquals(2, lineCount,
                "printAllRoles() must print exactly one line per person");
    }
}
