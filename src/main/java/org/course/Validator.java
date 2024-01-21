package org.course;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern CUSTOMER_ID_PATTERN = Pattern.compile("[A-Za-z]{3}\\d{5}[A-Za-z]{2}-(A|Q)");
    private static final Pattern ITEM_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$"); // Alphabets, numbers, and spaces, but no leading/trailing spaces
    private static final int ITEM_QUANTITY_UPPER_BOUND = 1000;
    private static final int ITEM_NAME_LENGTH_UPPER_BOUND = 100;
    public static void validateCustomerId(String customerId){
        if (!CUSTOMER_ID_PATTERN.matcher(customerId).matches()) {
            throw new IllegalArgumentException("Invalid customer ID: " + customerId);
        }
    }

    public static void validateItemName(String itemName){
        if (itemName == null
                || itemName.length() > ITEM_NAME_LENGTH_UPPER_BOUND
                || !ITEM_NAME_PATTERN.matcher(itemName).matches()) {
            throw new IllegalArgumentException("Invalid itemName: " + itemName);
        }
    }

    public static void validateItemQuantity(Integer num){
        if (num < 1 || num > ITEM_QUANTITY_UPPER_BOUND) {
            throw new IllegalArgumentException("Invalid quantity: " + num);
        }
    }
}