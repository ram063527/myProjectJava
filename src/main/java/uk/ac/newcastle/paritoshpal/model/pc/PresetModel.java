package main.java.uk.ac.newcastle.paritoshpal.model.pc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PresetModel extends AbstractPCModel{

    private final String manufacturer;
    private final List<String> parts;

    public PresetModel(String name,String manufacturer, List<String> parts) {
        super(name);
        this.manufacturer = validateAndNormalizeManufacturer(manufacturer);
        if(parts == null || parts.isEmpty()){
            throw new IllegalArgumentException("Parts list cannot be null or empty.");
        }
        this.parts = List.copyOf(parts);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public List<String> getParts() {
        return this.parts;
    }

    @Override
    public String toString() {
        return "Preset Model: "+this.manufacturer+" "+this.getName();
    }

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
