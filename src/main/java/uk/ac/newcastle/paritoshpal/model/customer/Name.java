package main.java.uk.ac.newcastle.paritoshpal.model.customer;

import java.util.Objects;

/**
    * Represents a person's name as an immutable object.
    * Equality is based on the first and last name.
 */
public final class Name {

    private final String firstName;
    private final String lastName;

    public Name(String firstName, String lastName) {

        this.firstName = validateAndNormalizeName(firstName);
        this.lastName = validateAndNormalizeName(lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    @Override
    public String toString() {
        return firstName + " - " + lastName;
    }

    public static Name valueOf(String name){
       // 1. Validate the input string itself.
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        String [] parts = name.split(" - ");
        // 2. Check if the split produced exactly 2 parts
        if(parts.length != 2){
            throw new IllegalArgumentException("Invalid name format. Expected 'firstName - lastName', but got '" + name + "'.");
        }
        // 3. The constructor will handle validation of individual parts.
        return new Name(parts[0], parts[1]);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name name)) return false;
        return Objects.equals(firstName, name.firstName) && Objects.equals(lastName, name.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    // private method to validate first name and last name

    private String validateAndNormalizeName(String name) {
        if(name==null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        // set to lowercase
        name = name.toLowerCase();
        // remove all leading and trailing whitespaces
        name = name.strip();
        //replacing all clusters of whitespaces with a single whitespace
        name = name.replaceAll("\\s+"," ");
        // make sure that all characters are either letters or whitespaces
        char [] chars = name.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(!Character.isLetter(chars[i]) && !Character.isWhitespace(chars[i])){
                throw new IllegalArgumentException("Invalid name. Character: "+chars[i]+" is invalid.");
            }
        }
        return name;
    }
}
