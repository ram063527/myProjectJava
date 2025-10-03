package main.java.uk.ac.newcastle.paritoshpal.model.customer;

import java.util.Objects;

public final class Customer {

    private final Name name;

     Customer(Name name) {
        if(name == null){
            throw new IllegalArgumentException("Name cannot be null.");
        }
        this.name = name;
    }

    public Name getName() {
        return this.name;
    }

    public String getFirstName(){
        return this.name.getFirstName();
    }

    public String getLastName(){
        return this.name.getLastName();
    }

    @Override
    public String toString() {
        return name.toString();
    }

    @Override
    public boolean equals(Object o) {
       if(this == o) return true;
       if(!(o instanceof Customer)) return false;
       Customer customer = (Customer) o;
       return Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
