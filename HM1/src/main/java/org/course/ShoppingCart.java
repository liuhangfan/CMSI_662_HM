package org.course;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * It allows users adding, updating, and removing items.
 */
public class ShoppingCart {
    private final UUID id;
    private final CustomerId customerId;
    private final Map<Item, Integer> items;

    /**
     * Create a ShoppingCart instance for a given customer.
     * @throws IllegalArgumentException If the customerId is invalid.
     */
    public ShoppingCart(CustomerId customerId) {
        if(customerId == null) throw new IllegalArgumentException("customerId must be not null");
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.items = new ConcurrentHashMap<>(); // ensure operation of items is thread-safe
    }

    public UUID getId() {
        return id;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    /**
     * Retrieves an unmodifiable view of the items in the cart.
     *
     * @return A map of item names to their quantities.
     */
    public Map<Item, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    /**
     * Adds or increments the quantity of an item in the cart.
     *
     * @param item     The item to add.
     * @param quantity The quantity to add.
     * @throws IllegalArgumentException If the item name or quantity is invalid.
     */
    public void addItem(Item item, int quantity) {
        int total = items.getOrDefault(item, 0) + quantity;
        Validator.validateItemQuantity(total);
        items.put(item, total);
    }

    public void addItem(Item item) {
        addItem(item, 1);
    }

    /**
     * Updates the quantity of an existing item in the cart.
     *
     * @param item     The item to update.
     * @param quantity The new quantity of the item.
     * @throws IllegalArgumentException If the item is not in the cart or if the quantity is invalid.
     */
    public void updateItem(Item item, int quantity) {
        Validator.validateItemQuantity(quantity);
        if (!items.containsKey(item)) {
            throw new IllegalArgumentException("Item was not found in the cart");
        }
        items.put(item, quantity);
    }

    /**
     * Removes an item from the cart.
     *
     * @param item The item to remove.
     * @throws IllegalArgumentException If the item is not in the cart.
     */
    public void removeItem(Item item) {
        if (!items.containsKey(item)) {
            throw new IllegalArgumentException("Item was not found in the cart");
        }
        items.remove(item);
    }

    /**
     * Calculates the total cost of the items in the cart.
     *
     * @return The total cost.
     */
    public double getTotalCost() {
        return items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice().doubleValue() * entry.getValue())
                .sum();
    }
}
