package uk.ac.newcastle.paritoshpal.model.pc;

import java.util.List;

/**
 * An abstract base class for {@link PCModel} implementation.
 * This class provides a common foundation for all PC models by
 * handling the storage and validation of the model's name.
 * Subclasses are required to provide their own implementation for
 * {@link #getParts()} method.
 *
 */
public abstract class AbstractPCModel implements PCModel {

    private final String name;

    /**
     * Constructs a new {@code PCModel} instance from the given name.
     *
     * @param name the name of the PC model; cannot be null or empty.
     * @throws IllegalArgumentException if {@code name} is null, empty, or
     * contain invalid characters.
     */
     AbstractPCModel(String name) {
       this.name = validateAndNormalizeName(name);
    }

    /**
     * Returns the name of the PCModel.
     * @return the name.
     */

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public abstract List<String> getParts();

    /**
     * Validates and normalizes a name string.
     *
     * Normalization includes converting the string to lowercase, trimming leading/trailing
     * whitespace, and collapsing internal whitespace to a single space.
     * Validation ensures the name is not null/empty and contains only letters,digits,spaces and
     * a dash (-) character
     *
     * @param name the name string to process.
     * @return the validated and normalized name string.
     * @throws IllegalArgumentException if the {@code name} is null, empty or contains
     * invalid characters.
     */

    private String validateAndNormalizeName(String name) {
        if(name==null || name.trim().isEmpty()){
            throw new IllegalArgumentException("PC model name cannot be null or empty.");
        }
        // set to lowercase
        name = name.toLowerCase();
        // remove all leading and trailing whitespaces
        name = name.strip();
        //replacing all clusters of whitespaces with a single whitespace
        name = name.replaceAll("\\s+"," ");
        // make sure that all characters are either letters or whitespaces or digits
        char [] chars = name.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c  = chars[i];
            if(!Character.isLetter(chars[i]) && !Character.isWhitespace(chars[i]) && !Character.isDigit(chars[i]) && c!='-'){
                throw new IllegalArgumentException("Invalid name. Character: "+ c +" is invalid.");
            }
        }
        return name;
    }
}

