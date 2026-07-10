package com.mahlafuna;

import com.mahlafuna.model.Person;
import com.mahlafuna.model.WalkInCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Person abstract class.
 */
public class PersonTest {

    private WalkInCustomer person;

    @BeforeEach
    void setUp() {
        person = new WalkInCustomer("Alice", "Dlamini", "alice@email.com", "071 111 1111", "C001", 85000);
    }

    @Test
    void personIsAbstract() {
        assertTrue(Modifier.isAbstract(Person.class.getModifiers()),
                "Person must be declared abstract");
    }

    @Test
    void constructorSetsAllFields() {
        assertEquals("Alice",           person.firstName());
        assertEquals("Dlamini",         person.lastName());
        assertEquals("alice@email.com", person.email());
        assertEquals("071 111 1111",    person.phoneNumber());
    }

    @Test
    void firstNameIsImmutable() {
        assertThrows(NoSuchMethodException.class,
                () -> Person.class.getMethod("updateFirstName", String.class),
                "firstName must be immutable — no updater");
    }

    @Test
    void lastNameIsImmutable() {
        assertThrows(NoSuchMethodException.class,
                () -> Person.class.getMethod("updateLastName", String.class),
                "lastName must be immutable — no updater");
    }

    @Test
    void updateEmailChangesValue() {
        person.updateEmail("new@email.com");
        assertEquals("new@email.com", person.email());
    }

    @Test
    void updatePhoneNumberChangesValue() {
        person.updatePhoneNumber("082 000 0000");
        assertEquals("082 000 0000", person.phoneNumber());
    }

    @Test
    void fullNameReturnsCombinedName() {
        assertEquals("Alice Dlamini", person.fullName());
    }

    @Test
    void toStringContainsFullNameAndEmail() {
        String result = person.toString();
        assertTrue(result.contains("Alice"),           "toString() must contain first name");
        assertTrue(result.contains("Dlamini"),         "toString() must contain last name");
        assertTrue(result.contains("alice@email.com"), "toString() must contain email");
    }

    @Test
    void roleDispatchesThroughPersonReference() {
        Person p = new WalkInCustomer("Alice", "Dlamini", "alice@email.com", "071 111 1111", "C001", 85000);
        assertEquals("Walk-In Customer", p.role(),
                "role() must dispatch correctly through a Person reference");
    }
}
