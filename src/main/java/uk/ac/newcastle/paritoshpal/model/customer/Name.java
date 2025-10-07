package main.java.uk.ac.newcastle.paritoshpal.model.customer;

import java.util.Objects;

/**
 * Represents a person's name as an immutable object.
 * <p>Equality is based on first and last name components</p>
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
     * <p>
     *     The format is "firstName - lastName", for example "john - doe".
     *     This format is compatible with {@link #valueOf(String)} method.
     * </p>
     * @return the formatted string representation of the name.
     * @see #valueOf(String)
     */
    @Override
    public String toString() {
        return firstName + " - " + lastName;
    }

    /**
     * Obtains an instance of {@code Name} from a string representation.
     * <p>
     *     The string must be in the format "firstName - lastName".
     * </p>
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
     *
     * @param o   the reference object with which to compare.
     * @return true if both current object and the reference object are same,
     * and if first name and last name fields are equal.
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
     * Method to validate and normalize <cod>name</cod>.
     * @param name
     * @return normalized <code>name</code>
     * @throws IllegalArgumentException if name is null empty or contains invalid character.
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
