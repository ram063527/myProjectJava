package main.java.uk.ac.newcastle.paritoshpal.model.customer;

import java.util.HashMap;
import java.util.Map;

/**
 * A factory for creating and managing unique {@link Customer} instances.
 * This class ensures that for any given name, only one {@code Customer}
 * object exists within the application.
 *
 */

//Todo : Explain why we chose factory clearly in the report
    //Todo : Why Our keys are not weak - because of validation and normalization in place.


public final class CustomerFactory {


    private static final Map<Name, Customer> customers = new HashMap<>();

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private CustomerFactory() {
        // Empty by design.
    }

    /**
     * Gets the unique {@link Customer} instance for the given name.
     * If a customer with the specified first and last name already exists,
     * the existing instance is returned. Otherwise, a new {@code Customer}
     * is created, stored for future requests, and then returned.
     * @param firstName the first name of the customer; cannot be null or empty.
     * @param lastName the last name of the customer; cannot be null or empty.
     * @return the unique, non-null {@code Customer} instance.
     * @throws IllegalArgumentException if either name component is invalid.
     */
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
