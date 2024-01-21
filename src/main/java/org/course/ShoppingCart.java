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
    private final String customerId;
    private final Map<String, Integer> items;

    /**
     * Create a ShoppingCart instance for a given customer.
     *
     * @param customerId The unique identifier of the customer.
     * @throws IllegalArgumentException If the customerId is invalid.
     */
    public ShoppingCart(String customerId) {
        Validator.validateCustomerId(customerId);
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.items = new ConcurrentHashMap<>(); // ensure operation of items is thread-safe
    }

    public UUID getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    /**
     * Retrieves an unmodifiable view of the items in the cart.
     *
     * @return A map of item names to their quantities.
     */
    public Map<String, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    /**
     * Adds or increments the quantity of an item in the cart.
     *
     * @param item     The name of the item to add.
     * @param quantity The quantity to add.
     * @throws IllegalArgumentException If the item name or quantity is invalid.
     */
    public void addItem(String item, int quantity) {
        Validator.validateItemName(item);
        int total = items.getOrDefault(item, 0) + quantity;
        Validator.validateItemQuantity(total);
        items.put(item, total);
    }

    /**
     * Updates the quantity of an existing item in the cart.
     *
     * @param item     The name of the item to update.
     * @param quantity The new quantity of the item.
     * @throws IllegalArgumentException If the item is not in the cart or if the quantity is invalid.
     */
    public void updateItem(String item, int quantity) {
        Validator.validateItemName(item);
        Validator.validateItemQuantity(quantity);
        if (!items.containsKey(item)) {
            throw new IllegalArgumentException("Item was not found in the cart");
        }
        items.put(item, quantity);
    }

    /**
     * Removes an item from the cart.
     *
     * @param item The name of the item to remove.
     * @throws IllegalArgumentException If the item is not in the cart.
     */
    public void removeItem(String item) {
        Validator.validateItemName(item);
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
        double totalCost = 0.0;
        for (Map.Entry<String, Integer> e : items.entrySet()) {
            totalCost += getItemCost(e.getKey()) * e.getValue();
        }
        return totalCost;
    }

    private double getItemCost(String item) {
        // TODO need logic to get item cost from a pricing service
        return 0; // example
    }
}
