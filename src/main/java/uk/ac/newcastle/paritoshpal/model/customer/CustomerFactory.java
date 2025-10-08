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

    /*
        Arguments for a factory :
        Two customers are considered to be the same if they have the same name.

        now
        Customer customer1 = new Customer(new Name("John","Smith"));
        Customer customer2 = new Customer(new Name("John","Smith"));

        // This is true.
        customer1.equals(customer2) //-> true

        // This is false
        customer1 == customer2

        // The analytics will still work without the customer factory
        as the method relies on the equals and hash code.
        // Given the requirements - we are considering the same ( we can implement the factory )

     */
    //Todo : Why Our keys are not weak - because of validation and normalization in place.

    // Keys are strong because we have key as Name
    // This name has equals and hashcode implemented.

    // eg: new Name("John" ,"Doe") and new Name("Jane","Smith")
    // could generate the same hash code.
    // But it will use keys equals method to find the exact match.


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
