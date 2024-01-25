package org.course;

import java.util.Map;

public class Main {

    /**
     * Here are some example tests, more detailed unit tests can be found in test module.
     */
    public static void main(String[] args) {
        CustomerId sampleCustomerId = new CustomerId("AAA11111AA-A");
        ShoppingCart cart = new ShoppingCart(sampleCustomerId);

        System.out.println("test cart UUID");
        testUUID(cart);

        System.out.println("test create ShoppingCart by empty customerId");
        testShoppingCart();

        System.out.println("test create customerId by invalid ID");
        testCreateCustomerId();

        System.out.println("test the returned items is immutable");
        testItemsImmutable(cart);

        System.out.println("test the item creation");
        testItemCreation();

        System.out.println("test the cart cost");
        testCartCost(sampleCustomerId);
    }

    private static void testUUID(ShoppingCart cart) {
        assert cart.getId().toString().length() == 36 : "Cart ID should be in UUID format";
    }

    private static void testShoppingCart() {
        try {
            new ShoppingCart(null);
            assert false : "Cart creation should fail with null CustomerId";
        } catch (IllegalArgumentException e) {
            assert true; // Test passed
        }
    }

    private static void testCreateCustomerId() {
        try {
            new CustomerId("ABC123DEF-A");
            assert false : "CustomerId creation should fail with malformed ID";
        } catch (IllegalArgumentException e) {
            assert true; // Test passed
        }
    }

    private static void testItemsImmutable(ShoppingCart cart) {
        cart.addItem(new Item("Milk", "2.99"), 1);
        cart.addItem(new Item("Cola", "1.99"), 1);
        Map<Item, Integer> items = cart.getItems();
        try {
            items.put(new Item("Milk", "1.99"), 2);
            assert false : "Should not be able to modify items map";
        } catch (UnsupportedOperationException e) {
            assert true;
        }
    }

    private static void testItemCreation() {
        try {
            new Item("ab^&&*c", "1.00");
            assert false : "Item creation should fail with invalid name";
        } catch (IllegalArgumentException e) {
            assert true;
        }

        try {
            new Item("abc", "0");
            assert false : "Item creation should fail with invalid price";
        } catch (IllegalArgumentException e) {
            assert true;
        }
    }

    private static void testCartCost(CustomerId sampleCustomerId) {
        ShoppingCart newCart = new ShoppingCart(sampleCustomerId);
        newCart.addItem(new Item("Milk", "2.99"), 2);
        newCart.addItem(new Item("Cola", "1.99"), 3);
        newCart.addItem(new Item("Chess", "3.10"), 2);
        newCart.addItem(new Item("Milk", "0.99"), 3);
        assert newCart.getTotalCost() == 21.12 : "Total cost is incorrect";
        newCart.removeItem(new Item("Milk", "0.99"));
        assert newCart.getTotalCost() == 18.15 : "Total cost is incorrect";
        newCart.updateItem(new Item("Cola", "1.99"), 4);
        assert newCart.getTotalCost() == 20.14 : "Total cost is incorrect";
    }
}
