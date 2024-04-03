package org.course;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerIdTest {

    @Test
    void createCustomerIdSuccess(){
        CustomerId customerId = new CustomerId("AAA11111AA-A");
        assertEquals(customerId.getId(), "AAA11111AA-A");
    }

    @Test
    void createCustomerIdFailure(){
        assertThrows(IllegalArgumentException.class, () -> new CustomerId("AAA11-11AA-A"));
    }
}