package org.course;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void createItemSuccess(){
        Item item = new Item("Milk", "100");
        assertEquals(item.getName(), "Milk");
        assertEquals(item.getPrice().toString(), "100");
    }

    @Test
    void createItemByInvalidName(){
        assertThrows(IllegalArgumentException.class, () -> new Item("Milk&%!", "100"));
        assertThrows(IllegalArgumentException.class, () -> new Item("", "100"));
    }

    @Test
    void createItemByInvalidPrice(){
        assertThrows(IllegalArgumentException.class, () -> new Item("Milk", "0"));
        assertThrows(IllegalArgumentException.class, () -> new Item("Milk", "-1"));
        assertThrows(IllegalArgumentException.class, () -> new Item("Milk", "abc"));
        assertThrows(IllegalArgumentException.class, () -> new Item("Milk", "99999999999"));
    }
}