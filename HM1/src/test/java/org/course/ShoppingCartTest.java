package org.course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    private final CustomerId CUSTOMER_ID = new CustomerId("AAA11111AA-A");
    private final Item item1 = new Item("Milk", "2.99");
    private final Item item2 = new Item("COla", "1.99");
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
        assertNotNull(shoppingCart.getId());
    }

    @Test
    void createShoppingCart_thenFailure() {
        assertThrows(IllegalArgumentException.class, () -> new ShoppingCart(null));
    }

    @Test
    void addItem_thenSuccess() {
        mockShoppingCart.addItem(item1, 5);
        assertEquals(5, mockShoppingCart.getItems().get(item1));
    }

    @Test
    void addItem_invalidItemQuantity_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.addItem(item1, -1));
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.addItem(item1, 0));
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.addItem(item1, 99999999));
    }

    @Test
    void updateItem_thenSuccess() {
        mockShoppingCart.addItem(item1, 5);
        mockShoppingCart.updateItem(item1, 10);
        assertEquals(10, mockShoppingCart.getItems().get(item1));
    }

    @Test
    void updateItem_notFound_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.updateItem(item1, 10));
    }

    @Test
    void updateItem_invalidItemQuantity_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.updateItem(item1, 0));
    }

    @Test
    void removeItem_thenSuccess() {
        mockShoppingCart.addItem(item1, 5);
        mockShoppingCart.removeItem(item1);
        assertFalse(mockShoppingCart.getItems().containsKey(item1));
    }

    @Test
    void removeItem_notFound_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> mockShoppingCart.removeItem(item1));
    }
    @Test
    void getItems_thenSuccess() {
        mockShoppingCart.addItem(item1, 5);
        assertEquals(1, mockShoppingCart.getItems().size());
        assertTrue(mockShoppingCart.getItems().containsKey(item1));
        assertEquals(5, (int) mockShoppingCart.getItems().get(item1));
    }

    @Test
    void getItems_isImmutable() {
        Map<Item, Integer> items = mockShoppingCart.getItems();
        assertThrows(UnsupportedOperationException.class, () -> items.put(item1, 1));
    }

    @Test
    void getTotalCost_thenSuccess() {
        mockShoppingCart.addItem(item1, 5);
        mockShoppingCart.addItem(item2, 5);
        assertEquals(24.9, mockShoppingCart.getTotalCost());
    }

    @Test
    void testThreadSafety() {
        IntStream.range(0, 100).parallel().forEach(i -> {
            Item item = i/2 ==0 ? item1 : item2;
            mockShoppingCart.addItem(item);
        });
        mockShoppingCart.removeItem(item1);
        mockShoppingCart.removeItem(item2);
        assertTrue(mockShoppingCart.getItems().isEmpty(), "Cart should be empty after adding and removing items.");
    }
}