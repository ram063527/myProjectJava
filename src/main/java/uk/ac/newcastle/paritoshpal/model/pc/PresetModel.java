package main.java.uk.ac.newcastle.paritoshpal.model.pc;

import java.util.List;
import java.util.Objects;

/**
 * Represents an immutable, pre-set PC model with a fixed list of parts.
 * Instances of this class are created with a specific name, manufacturer, and
 * parts list, which cannot be changed after creation.
 * Equality is based on the model name, manufacturer and parts list.
 */

public final class PresetModel extends AbstractPCModel{

    private final String manufacturer;
    private final List<String> parts;

    /**
     * Constructs a pre-set PC model from the given {@code name},{@code manufacturer}
     * and {@code parts}.
     * @param name the name of the pre-set pc model; cannot be null or empty.
     * @param manufacturer the name of the manufacturer; cannot be null or empty.
     * @param parts the list of parts this pre-set model contains; cannot be null or empty.
     * @throws IllegalArgumentException if either of the fields are null or empty or
     * {@code name} and {@code manufacturer} contains an invalid character.
     */
    public PresetModel(String name,String manufacturer, List<String> parts) {
        super(name);
        this.manufacturer = validateAndNormalizeManufacturer(manufacturer);
        if(parts == null || parts.isEmpty()){
            throw new IllegalArgumentException("Parts list cannot be null or empty.");
        }
        this.parts = List.copyOf(parts);
    }

    /**
     * Returns the name of manufacturer.
     * @return the manufacturer's name.
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Returns the list of unmodifiable parts for this model.
     * @return the list of parts.
     */
    @Override
    public List<String> getParts() {
        return this.parts;
    }

    /**
     * Returns a string representation of pre-set model.
     *
     * @return a string in the format "Preset Model : [manufacturer] [name]".
     */
    @Override
    public String toString() {
        return "Preset Model: "+this.manufacturer+" "+this.getName();
    }

    /**
     * Compares this custom model to the specified object for equality.
     * @param o the object to compare this {@code PresetModel} against.
     *
     * @return {@code true} if the object is also a {@code PresetModel} and
     * has the same name, manufacturer and list of parts; {@code false} otherwise.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PresetModel that = (PresetModel) o;
        return Objects.equals(getName(), that.getName())
                && Objects.equals(manufacturer, that.getManufacturer())
                && Objects.equals(parts,that.parts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), manufacturer, parts);
    }

    /**
     * Validates and normalizes a manufacturer string.
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
    private String validateAndNormalizeManufacturer(String name) {
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
