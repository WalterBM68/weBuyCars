package com.mahlafuna;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Vehicle and all three Vehicle subclasses.
 */
public class VehicleTest {

    private Sedan  sedan;
    private SUV    suv;
    private Bakkie bakkieDouble;
    private Bakkie bakkieSingle;

    @BeforeEach
    void setUp() {
        sedan        = new Sedan("CA 123-456",  "Toyota", "Corolla", 2019, 45000, 4);
        suv          = new SUV("CA 789-012",    "Ford",   "Everest", 2021, 30000, true);
        bakkieDouble = new Bakkie("CA 345-678", "Toyota", "Hilux",   2020, 60000, true);
        bakkieSingle = new Bakkie("CA 901-234", "Nissan", "NP300",   2018, 80000, false);
    }

    @Test
    void vehicleIsAbstract() {
        assertTrue(Modifier.isAbstract(Vehicle.class.getModifiers()),
                "Vehicle must be declared abstract");
    }

    @Test
    void sedanIsAVehicle() {
        assertInstanceOf(Vehicle.class, sedan);
    }

    @Test
    void suvIsAVehicle() {
        assertInstanceOf(Vehicle.class, suv);
    }

    @Test
    void bakkieIsAVehicle() {
        assertInstanceOf(Vehicle.class, bakkieDouble);
    }

    @Test
    void constructorSetsAllFields() {
        assertEquals("CA 123-456", sedan.registrationNumber());
        assertEquals("Toyota",     sedan.make());
        assertEquals("Corolla",    sedan.model());
        assertEquals(2019,         sedan.year());
        assertEquals(45000,        sedan.mileage());
    }

    @Test
    void vehicleDefaultsToAvailable() {
        assertTrue(sedan.available());
        assertTrue(suv.available());
    }

    @Test
    void markSoldSetsAvailableToFalse() {
        sedan.markSold();
        assertFalse(sedan.available());
    }

    @Test
    void markAvailableRestoresAvailability() {
        sedan.markSold();
        sedan.markAvailable();
        assertTrue(sedan.available());
    }

    @Test
    void registrationNumberIsImmutable() {
        assertThrows(NoSuchMethodException.class,
                () -> Vehicle.class.getMethod("updateRegistrationNumber", String.class));
    }

    @Test
    void makeIsImmutable() {
        assertThrows(NoSuchMethodException.class,
                () -> Vehicle.class.getMethod("updateMake", String.class));
    }

    @Test
    void modelIsImmutable() {
        assertThrows(NoSuchMethodException.class,
                () -> Vehicle.class.getMethod("updateModel", String.class));
    }

    @Test
    void updateYearChangesValue() {
        sedan.updateYear(2020);
        assertEquals(2020, sedan.year());
    }

    @Test
    void updateYearThrowsForYearBefore1900() {
        assertThrows(IllegalArgumentException.class, () -> sedan.updateYear(1899));
    }

    @Test
    void updateYearThrowsForFutureYear() {
        int future = java.time.Year.now().getValue() + 1;
        assertThrows(IllegalArgumentException.class, () -> sedan.updateYear(future));
    }

    @Test
    void updateMileageChangesValue() {
        sedan.updateMileage(50000);
        assertEquals(50000, sedan.mileage());
    }

    @Test
    void updateMileageThrowsForNegative() {
        assertThrows(IllegalArgumentException.class, () -> sedan.updateMileage(-1));
    }

    @Test
    void constructorThrowsForNegativeMileage() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sedan("CA 000-000","X","Y", 2020, -1, 4));
    }

    @Test
    void toStringContainsKeyDetails() {
        String result = sedan.toString();
        assertTrue(result.contains("CA 123-456"), "toString() must include registration");
        assertTrue(result.contains("Toyota"),      "toString() must include make");
        assertTrue(result.contains("Corolla"),     "toString() must include model");
    }

    @Test
    void sedanNumberOfDoorsReturnsCorrectValue() {
        assertEquals(4, sedan.numberOfDoors());
    }

    @Test
    void sedanNumberOfDoorsIsImmutable() {
        assertThrows(NoSuchMethodException.class,
                () -> Sedan.class.getMethod("updateNumberOfDoors", int.class));
    }

    @Test
    void sedanConstructorThrowsForFewerThanTwoDoors() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sedan("CA 000-000","X","Y",2020,0,1));
    }

    @Test
    void sedanListingPriceCalculatedCorrectly() {
        double expected = Math.max(Sedan.BASE_PRICE - (45000 * Sedan.PRICE_PER_KM), Sedan.MINIMUM_PRICE);
        assertEquals(expected, sedan.listingPrice(), 0.001);
    }

    @Test
    void sedanListingPriceNeverGoesBelowMinimum() {
        Sedan highMileage = new Sedan("CA 000-000","X","Y",2010,500000,4);
        assertTrue(highMileage.listingPrice() >= Sedan.MINIMUM_PRICE,
                "Sedan listing price must never go below the minimum");
    }

    @Test
    void sedanVehicleTypeIsCorrect() {
        assertEquals("Sedan", sedan.vehicleType());
    }

    @Test
    void suvAllWheelDriveReturnsCorrectValue() {
        assertTrue(suv.allWheelDrive());
    }

    @Test
    void suvAllWheelDriveIsImmutable() {
        assertThrows(NoSuchMethodException.class,
                () -> SUV.class.getMethod("updateAllWheelDrive", boolean.class));
    }

    @Test
    void suvListingPriceWithAwd() {
        double expected = Math.max(
                SUV.BASE_PRICE - (30000 * SUV.PRICE_PER_KM) + SUV.AWD_PREMIUM,
                SUV.MINIMUM_PRICE);
        assertEquals(expected, suv.listingPrice(), 0.001);
    }

    @Test
    void suvListingPriceWithoutAwd() {
        SUV nonAwd = new SUV("CA 000-000","X","Y",2021,30000,false);
        double expected = Math.max(
                SUV.BASE_PRICE - (30000 * SUV.PRICE_PER_KM),
                SUV.MINIMUM_PRICE);
        assertEquals(expected, nonAwd.listingPrice(), 0.001);
    }

    @Test
    void suvListingPriceNeverGoesBelowMinimum() {
        SUV highMileage = new SUV("CA 000-000","X","Y",2010,500000,false);
        assertTrue(highMileage.listingPrice() >= SUV.MINIMUM_PRICE);
    }

    @Test
    void suvVehicleTypeIsCorrect() {
        assertEquals("SUV", suv.vehicleType());
    }

    @Test
    void bakkieDoubleCabReturnsCorrectValue() {
        assertTrue(bakkieDouble.doubleCab());
        assertFalse(bakkieSingle.doubleCab());
    }

    @Test
    void bakkieDoubleCabIsImmutable() {
        assertThrows(NoSuchMethodException.class,
                () -> Bakkie.class.getMethod("updateDoubleCab", boolean.class));
    }

    @Test
    void bakkieListingPriceWithDoubleCab() {
        double expected = Math.max(
                Bakkie.BASE_PRICE - (60000 * Bakkie.PRICE_PER_KM) + Bakkie.DOUBLE_CAB_PREMIUM,
                Bakkie.MINIMUM_PRICE);
        assertEquals(expected, bakkieDouble.listingPrice(), 0.001);
    }

    @Test
    void bakkieListingPriceWithSingleCab() {
        double expected = Math.max(
                Bakkie.BASE_PRICE - (80000 * Bakkie.PRICE_PER_KM),
                Bakkie.MINIMUM_PRICE);
        assertEquals(expected, bakkieSingle.listingPrice(), 0.001);
    }

    @Test
    void bakkieListingPriceNeverGoesBelowMinimum() {
        Bakkie highMileage = new Bakkie("CA 000-000","X","Y",2010,500000,false);
        assertTrue(highMileage.listingPrice() >= Bakkie.MINIMUM_PRICE);
    }

    @Test
    void bakkieVehicleTypeIncludesCabType() {
        assertTrue(bakkieDouble.vehicleType().toLowerCase().contains("double"));
        assertTrue(bakkieSingle.vehicleType().toLowerCase().contains("single"));
    }

    @Test
    void listingPriceDispatchesThroughVehicleReference() {
        Vehicle v = new Sedan("CA 123-456","Toyota","Corolla",2019,45000,4);
        double expected = Math.max(Sedan.BASE_PRICE - (45000 * Sedan.PRICE_PER_KM), Sedan.MINIMUM_PRICE);
        assertEquals(expected, v.listingPrice(), 0.001,
                "listingPrice() must dispatch correctly through a Vehicle reference");
    }

    @Test
    void vehicleTypeDispatchesThroughVehicleReference() {
        Vehicle v = new SUV("CA 789-012","Ford","Everest",2021,30000,true);
        assertEquals("SUV", v.vehicleType(),
                "vehicleType() must dispatch correctly through a Vehicle reference");
    }

    @Test
    void listingPricesReflectDifferentBaseRates() {
        // Same mileage, different base prices
        Sedan  s = new Sedan("A","X","Y",2020,10000,4);
        SUV    u = new SUV("B","X","Y",2020,10000,false);
        Bakkie b = new Bakkie("C","X","Y",2020,10000,false);
        assertNotEquals(s.listingPrice(), u.listingPrice());
        assertNotEquals(s.listingPrice(), b.listingPrice());
        assertNotEquals(u.listingPrice(), b.listingPrice());
    }
}
