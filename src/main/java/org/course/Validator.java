package org.course;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern CUSTOMER_ID_PATTERN = Pattern.compile("[A-Za-z]{3}\\d{5}[A-Za-z]{2}-(A|Q)");
    private static final Pattern ITEM_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$"); // Alphabets, numbers, and spaces, but no leading/trailing spaces
    private static final int ITEM_QUANTITY_UPPER_BOUND = 1000;
    private static final int ITEM_NAME_LENGTH_UPPER_BOUND = 50;
    private static final BigDecimal ITEM_PRICE_UPPER_BOUND = new BigDecimal("100000");

    public static void validateCustomerId(String customerId) {
        if (!CUSTOMER_ID_PATTERN.matcher(customerId).matches()) {
            throw new IllegalArgumentException("Invalid customer ID: " + customerId);
        }
    }

    public static void validateItemName(String itemName) {
        if (itemName == null
                || itemName.length() > ITEM_NAME_LENGTH_UPPER_BOUND
                || !ITEM_NAME_PATTERN.matcher(itemName).matches()) {
            throw new IllegalArgumentException("Invalid itemName: " + itemName);
        }
    }

    public static void validateItemQuantity(Integer num) {
        if (num < 1 || num > ITEM_QUANTITY_UPPER_BOUND) {
            throw new IllegalArgumentException("Invalid quantity: " + num);
        }
    }

    public static void validateItemPrice(String price){
        try {
            BigDecimal monetary = new BigDecimal(price);
            if (monetary.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Price must be positive: " + price);
            }
            if (monetary.compareTo(ITEM_PRICE_UPPER_BOUND) > 0){
                throw new IllegalArgumentException("Price " + price + " must be less than upper bound 100000");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid price format: " + price, e);
        }
    }
}