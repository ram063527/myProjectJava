package main.java.uk.ac.newcastle.paritoshpal.model.pc;

import java.util.HashMap;
import java.util.Map;


public class CustomModelFactory {

    private static final Map<String,CustomModel> models = new HashMap<>();

    private CustomModelFactory() {
        // empty
    }
    public static CustomModel createCustomModel(String name) {
        String normalizedName = validateAndNormalizeManufacturer(name);
        if(models.containsKey(normalizedName)){
            throw new IllegalArgumentException("A custom model with the name '" + name + "' already exists.");
        }
        CustomModel newModel = new CustomModel(normalizedName);
        models.put(normalizedName, newModel);
        return newModel;
    }

    private static String validateAndNormalizeManufacturer(String name) {
        if(name==null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Custom model name cannot be null or empty.");
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
