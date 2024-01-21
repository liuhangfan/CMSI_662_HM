package org.course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    private final String CUSTOMER_ID = "AAA11111AA-A";
    private final String ITEM_NAME = "ITEM NAME";
    private final String INVALID_ITEM_NAME = "ITEM_NAME%%%%";
    private ShoppingCart mockShoppingCart;


    @BeforeEach
    public void init() {
        mockShoppingCart = new ShoppingCart(CUSTOMER_ID);
    }

    @Test
    void createShoppingCart_thenSuccess() {
        ShoppingCart shoppingCart = new ShoppingCart(CUSTOMER_ID);
        assertEquals(CUSTOMER_ID, shoppingCart.getCustomerId());
        assertTrue(shoppingCart.getItems().isEmpty());
        assertFalse(shoppingCart.getId().toString().isEmpty());
    }

    @Test
    void createShoppingCart_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new ShoppingCart("INVALID_CUSTOMER_ID"));
    }

    @Test
    void addItem_thenSuccess() {
        mockShoppingCart.addItem(ITEM_NAME, 5);
        assertEquals(5, mockShoppingCart.getItems().get(ITEM_NAME));
    }

    @Test
    void addItem_invalidItemName_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.addItem(INVALID_ITEM_NAME, 5));
    }

    @Test
    void addItem_invalidItemQuantity_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.addItem(ITEM_NAME, -1));
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.addItem(ITEM_NAME, 0));
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.addItem(ITEM_NAME, 99999999));
    }

    @Test
    void updateItem_thenSuccess() {
        mockShoppingCart.addItem(ITEM_NAME, 5);
        mockShoppingCart.updateItem(ITEM_NAME, 10);
        assertEquals(10, mockShoppingCart.getItems().get(ITEM_NAME));
    }

    @Test
    void updateItem_notFound_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.updateItem(ITEM_NAME, 10));
    }

    @Test
    void updateItem_invalidItemName_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.updateItem(INVALID_ITEM_NAME, 10));
    }

    @Test
    void updateItem_invalidItemQuantity_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.updateItem(ITEM_NAME, 0));
    }

    @Test
    void removeItem_thenSuccess() {
        mockShoppingCart.addItem(ITEM_NAME, 5);
        mockShoppingCart.removeItem(ITEM_NAME);
        assertFalse(mockShoppingCart.getItems().containsKey(ITEM_NAME));
    }

    @Test
    void removeItem_notFound_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.removeItem(ITEM_NAME));
    }

    @Test
    void removeItem_invalidItemName_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.removeItem(INVALID_ITEM_NAME));
    }

    @Test
    void getItems_thenSuccess() {
        mockShoppingCart.addItem(ITEM_NAME, 5);
        assertEquals(1, mockShoppingCart.getItems().size());
        assertTrue(mockShoppingCart.getItems().containsKey(ITEM_NAME));
        assertEquals(5, (int) mockShoppingCart.getItems().get(ITEM_NAME));
    }

    @Test
    void getItems_isImmutable() {
        Map<String, Integer> items = mockShoppingCart.getItems();
        assertThrows(UnsupportedOperationException.class, () -> items.put(ITEM_NAME, 1));
    }

    @Test
    void getTotalCost_thenSuccess() {
        mockShoppingCart.addItem(ITEM_NAME, 5);
        assertEquals(0.0, mockShoppingCart.getTotalCost());
    }

    @Test
    void testThreadSafety() {
        IntStream.range(0, 100).parallel().forEach(i -> {
            mockShoppingCart.addItem("Item" + i, 1);
            mockShoppingCart.updateItem("Item" + i, 2);
            mockShoppingCart.removeItem("Item" + i);
        });
        assertTrue(mockShoppingCart.getItems().isEmpty(), "Cart should be empty after adding and removing items.");
    }
}