package main.java.uk.ac.newcastle.paritoshpal.model.customer;

import java.util.Objects;

/**
 * Represents a person's name as an immutable object.
 * Equality is based on first and last name components.
 */
public final class Name {

    private final String firstName;
    private final String lastName;

    /**
     * Constructs a new {@code Name} instance from the given first and last names.
     *
     * @param firstName the first name (part) of the name; cannot be null or empty.
     * @param lastName the last name (part) of the name; cannot be null or empty.
     * @throws IllegalArgumentException if either {@code firstName} or {@code lastName} is null empty or contains invalid character.
     */
    public Name(String firstName, String lastName) {

        this.firstName = validateAndNormalizeName(firstName);
        this.lastName = validateAndNormalizeName(lastName);
    }


    /**
     * Returns the first part of the name.
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name part of the name.
     * @return the last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the string representation of this name.
     * The format is "firstName - lastName", for example "john - doe".
     * This format is compatible with {@link #valueOf(String)} method.
     *
     * @return the formatted string representation of the name.
     * @see #valueOf(String)
     */
    @Override
    public String toString() {
        return firstName + " - " + lastName;
    }

    /**
     * Obtains an instance of {@code Name} from a string representation.
     * The string must be in the format "firstName - lastName".
     *
     * @param name a name in the specified string representation
     * @return a {@code Name} instance of corresponding to the given string.
     * @throws IllegalArgumentException if {@code name}is null or empty.
     * @throws ArrayIndexOutOfBoundsException if there are not two components parts
     * to <code> name </code> (first and last names)
     */

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


    /**
     * Compares this name to the specified object.
     *
     * @param o the object to compare this {@code Name} against.
     * @return {@code true} if the object is also a {@code Name} and the
     * first and last names are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name name)) return false;
        return Objects.equals(firstName, name.firstName) && Objects.equals(lastName, name.lastName);
    }

    /**
     * @see Object#hashCode()
     */

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    /**
     * Validates and normalizes a name string.
     *
     * Normalization includes converting the string to lowercase, trimming leading/trailing
     * whitespace, and collapsing internal whitespace to a single space.
     * Validation ensures the name is not null/empty and contains only letters and spaces.
     *
     * @param name the name string to process.
     * @return the validated and normalized name string.
     * @throws IllegalArgumentException if the {@code name} is null, empty or contains
     * invalid characters.
     */
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
