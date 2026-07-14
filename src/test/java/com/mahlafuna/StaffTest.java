package com.mahlafuna;

import com.mahlafuna.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Staff and both Staff subclasses.
 */
public class StaffTest {

    private Salesperson salesperson;
    private Valuator    valuator;

    @BeforeEach
    void setUp() {
        salesperson = new Salesperson("Dave", "Sithole", "dave@wbc.co.za", "071 444 4444", "E001", 2);
        valuator    = new Valuator("Eve", "Khumalo", "eve@wbc.co.za", "071 555 5555", "E002", 5, "NADA Certified");
    }

    @Test
    void staffIsAbstract() {
        assertTrue(Modifier.isAbstract(Staff.class.getModifiers()),
                "Staff must be declared abstract");
    }

    @Test
    void salespersonIsAPerson() {
        assertInstanceOf(Person.class, salesperson);
    }

    @Test
    void valuatorIsAPerson() {
        assertInstanceOf(Person.class, valuator);
    }

    @Test
    void salespersonIsStaff() {
        assertInstanceOf(Staff.class, salesperson);
    }

    @Test
    void valuatorIsStaff() {
        assertInstanceOf(Staff.class, valuator);
    }

    @Test
    void employeeIdReturnsCorrectValue() {
        assertEquals("E001", salesperson.employeeId());
        assertEquals("E002", valuator.employeeId());
    }

    @Test
    void employeeIdIsImmutable() {
        assertThrows(NoSuchMethodException.class,
                () -> Staff.class.getMethod("updateEmployeeId", String.class),
                "employeeId must be immutable — no updater");
    }

    @Test
    void yearsOfServiceReturnsCorrectValue() {
        assertEquals(2, salesperson.yearsOfService());
        assertEquals(5, valuator.yearsOfService());
    }

    @Test
    void updateYearsOfServiceChangesValue() {
        salesperson.updateYearsOfService(4);
        assertEquals(4, salesperson.yearsOfService());
    }

    @Test
    void updateYearsOfServiceThrowsForNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> salesperson.updateYearsOfService(-1));
    }

    @Test
    void updateYearsOfServiceAllowsZero() {
        assertDoesNotThrow(() -> valuator.updateYearsOfService(0));
        assertEquals(0, valuator.yearsOfService());
    }

    @Test
    void constructorThrowsForNegativeYearsOfService() {
        assertThrows(IllegalArgumentException.class,
                () -> new Salesperson("X","Y","x@y.com","000","E999",-1));
    }

    @Test
    void toStringContainsEmployeeId() {
        assertTrue(salesperson.toString().contains("E001"));
    }

    @Test
    void salespersonDealsClosedDefaultsToZero() {
        assertEquals(0, salesperson.dealsClosed());
    }

    @Test
    void recordDealIncrementsDealsClosed() {
        salesperson.recordDeal();
        salesperson.recordDeal();
        assertEquals(2, salesperson.dealsClosed());
    }

    @Test
    void resetDealsSetsDealClosedToZero() {
        salesperson.recordDeal();
        salesperson.recordDeal();
        salesperson.resetDeals();
        assertEquals(0, salesperson.dealsClosed());
    }

    @Test
    void salespersonMonthlySalaryIncludesCommission() {
        salesperson.recordDeal();
        salesperson.recordDeal();
        double expected = Salesperson.BASE_SALARY + (2 * Salesperson.COMMISSION_PER_DEAL);
        assertEquals(expected, salesperson.monthlySalary(), 0.001);
    }

    @Test
    void salespersonMonthlySalaryWithNoDealsIsBaseSalary() {
        assertEquals(Salesperson.BASE_SALARY, salesperson.monthlySalary(), 0.001);
    }

    @Test
    void salespersonMonthlySalaryUpdatesAfterRecordDeal() {
        double before = salesperson.monthlySalary();
        salesperson.recordDeal();
        assertTrue(salesperson.monthlySalary() > before,
                "Salary must increase after recording a deal");
    }

    @Test
    void salespersonDutiesAreCorrect() {
        assertEquals("Customer liaison, vehicle sales, and deal negotiation",
                salesperson.duties());
    }

    @Test
    void salespersonRoleIsCorrect() {
        assertEquals("Salesperson", salesperson.role());
    }

    @Test
    void salespersonToStringContainsDealsClosed() {
        salesperson.recordDeal();
        assertTrue(salesperson.toString().contains("1"),
                "toString() must include deals closed");
    }

    @Test
    void valuatorMonthlySalaryIncludesYearsOfServiceBonus() {
        double expected = Valuator.BASE_SALARY + (5 * Valuator.BONUS_PER_YEAR);
        assertEquals(expected, valuator.monthlySalary(), 0.001);
    }

    @Test
    void valuatorMonthlySalaryUpdatesWithYearsOfService() {
        valuator.updateYearsOfService(10);
        double expected = Valuator.BASE_SALARY + (10 * Valuator.BONUS_PER_YEAR);
        assertEquals(expected, valuator.monthlySalary(), 0.001);
    }

    @Test
    void valuatorCertificationReturnsCorrectValue() {
        assertEquals("NADA Certified", valuator.certification());
    }

    @Test
    void valuatorCertificationIsImmutable() {
        assertThrows(NoSuchMethodException.class,
                () -> Valuator.class.getMethod("updateCertification", String.class),
                "certification must be immutable — no updater");
    }

    @Test
    void valuatorDutiesAreCorrect() {
        assertEquals("Vehicle inspection, condition reporting, and price assessment",
                valuator.duties());
    }

    @Test
    void valuatorRoleIncludesCertification() {
        assertEquals("Valuator (NADA Certified)", valuator.role());
    }

    @Test
    void valuatorToStringContainsCertification() {
        assertTrue(valuator.toString().contains("NADA Certified"));
    }

    @Test
    void monthlySalaryDispatchesThroughStaffReference() {
        Staff s = new Salesperson("Dave","Sithole","d@wbc.co.za","000","E001", 0);
        assertEquals(Salesperson.BASE_SALARY, s.monthlySalary(), 0.001,
                "monthlySalary() must dispatch correctly through a Staff reference");
    }

    @Test
    void roleDispatchesThroughStaffReference() {
        Staff s = new Valuator("Eve","Khumalo","e@wbc.co.za","000","E002", 3, "NADA Certified");
        assertEquals("Valuator (NADA Certified)", s.role(),
                "role() must dispatch correctly through a Staff reference");
    }

    @Test
    void dutiesDispatchesThroughStaffReference() {
        Staff s = new Salesperson("Dave","Sithole","d@wbc.co.za","000","E001", 0);
        assertEquals("Customer liaison, vehicle sales, and deal negotiation", s.duties(),
                "duties() must dispatch correctly through a Staff reference");
    }

    @Test
    void salaryStructuresDifferAcrossTypes() {
        assertNotEquals(salesperson.monthlySalary(), valuator.monthlySalary());
    }
}
