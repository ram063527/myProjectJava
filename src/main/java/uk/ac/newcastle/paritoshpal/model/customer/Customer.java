package main.java.uk.ac.newcastle.paritoshpal.model.customer;

import java.util.Objects;

/**
 * Represents a customer as an immutable object.
 * Instances of this class are managed by the {@link CustomerFactory} to ensure
 * that only one object exists for each unique customer name.
 * Equality is based on the {@code Name} component.
 */
public final class Customer {

    private final Name name;

    /**
     * Constructs a new {@code Customer} instance from the given name.
     *
     * @param name the name object; cannot be null.
     * @throws IllegalArgumentException if {@code name} is null.
     */
     Customer(Name name) {
        if(name == null){
            throw new IllegalArgumentException("Name cannot be null.");
        }
        this.name = name;
    }

    /**
     * Returns the name of the customer.
     * @return the name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Returns the first name of the customer.
     * @return the first name.
     */
    public String getFirstName(){
        return this.name.getFirstName();
    }

    /**
     * Returns the last name of the customer.
     * @return the last name.
     */

    public String getLastName(){
        return this.name.getLastName();
    }

    /**
     * Returns the string representation of this customer.
     * Delegates the call to the {@code name} object.
     *
     * @return the formatted string representation of the customer's name.
     *
     */
    @Override
    public String toString() {
        return name.toString();
    }

    /**
     * Compares this customer to the specified object.
     *
     * @param o the object to compare this {@code Customer} against.
     * @return {@code true} if the object is also a {@code Customer} and the
     * {@code name} is equal; {@code false} otherwise.
     */

    @Override
    public boolean equals(Object o) {
       if(this == o) return true;
       if(!(o instanceof Customer)) return false;
       Customer customer = (Customer) o;
       return Objects.equals(name, customer.name);
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
