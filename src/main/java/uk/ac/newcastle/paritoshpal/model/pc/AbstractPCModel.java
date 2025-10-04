package main.java.uk.ac.newcastle.paritoshpal.model.pc;

import java.util.List;

public abstract class AbstractPCModel implements PCModel {

    private final String name;

    protected AbstractPCModel(String name) {
       this.name = validateAndNormalizeName(name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public abstract List<String> getParts();


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

