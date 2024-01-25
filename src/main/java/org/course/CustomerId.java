package org.course;


public class CustomerId {
    private final String id;

    public CustomerId(String id) {
        Validator.validateCustomerId(id);
        this.id = id;
    }

    public String getId(){
        return id;
    }
}
