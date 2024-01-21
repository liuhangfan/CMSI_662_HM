package org.course;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ShoppingCart {
    private final UUID id;
    private final String customerId;
    private final ConcurrentHashMap<String, Integer> items;

    public ShoppingCart(String customerId) {
        Validator.validateCustomerId(customerId);
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.items = new ConcurrentHashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Map<String, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public void addItem(String item, int quantity) {
        Validator.validateItemQuantity(quantity);
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public void updateItem(String item, int quantity) {
        Validator.validateItemName(item);
        Validator.validateItemQuantity(quantity);
        if (!items.containsKey(item)) {
            throw new IllegalArgumentException("Item was not found in the cart");
        }
        items.put(item, quantity);
    }

    public void removeItem(String item) {
        Validator.validateItemName(item);
        if (!items.containsKey(item)) {
            throw new IllegalArgumentException("Item was not found in the cart");
        }
        items.remove(item);
    }

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
