package org.course;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorTest {

    @Test
    void validateValidCustomerId() {
        assertDoesNotThrow(() -> Validator.validateCustomerId("ABC12345DE-A"));
    }

    @Test
    void validateInvalidCustomerId() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateCustomerId("1234"));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateCustomerId("ABCD2345DE-A"));
    }

    @Test
    void validateValidItemName() {
        assertDoesNotThrow(() -> Validator.validateItemName("Item Name"));
    }

    @Test
    void validateInvalidItemName() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateItemName(null));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateItemName("This item name is way too long and should cause an exception because it exceeds the length limit"));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateItemName("Invalid#Name"));
    }

    @Test
    void validateValidItemQuantity() {
        assertDoesNotThrow(() -> Validator.validateItemQuantity(100));
    }

    @Test
    void validateInvalidItemQuantity() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateItemQuantity(0));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateItemQuantity(1001));
    }

    @Test
    void validateInvalidItemPrice(){
        assertThrows(IllegalArgumentException.class, () -> Validator.validateItemPrice("-1"));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateItemPrice("0"));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateItemPrice("99999999999"));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateItemPrice("abc"));
    }

    @Test
    void validateValidItemPrice() {
        assertDoesNotThrow(() -> Validator.validateItemPrice("12.33"));
    }
}