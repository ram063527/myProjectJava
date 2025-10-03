package main.java.uk.ac.newcastle.paritoshpal.model.customer;

import java.util.HashMap;
import java.util.Map;

public final class CustomerFactory {

    private static final Map<Name, Customer> customers = new HashMap<>();

    private CustomerFactory() {
        // Empty
    }

    public static Customer getCustomer(String firstName, String lastName) {

        Name nameToFind = new Name(firstName, lastName);
        Customer customer = customers.get(nameToFind);
        if(customer != null){
            return customer;
        }
        Customer newCustomer = new Customer(nameToFind);
        customers.put(nameToFind, newCustomer);
        return newCustomer;

    }
}
