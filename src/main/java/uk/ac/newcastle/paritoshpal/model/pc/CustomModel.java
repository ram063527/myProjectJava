package main.java.uk.ac.newcastle.paritoshpal.model.pc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CustomModel extends AbstractPCModel{

    private final List<String> parts;

    CustomModel(String name) {
        super(name);
        this.parts = new ArrayList<>();
    }

    public void addPart(String part) {
        if(part!=null && !part.trim().isEmpty()){
            this.parts.add(part);
        }
    }

    public void removePart(String part) {
        this.parts.remove(part);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomModel that = (CustomModel) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public List<String> getParts() {
        return new ArrayList<>(this.parts);
    }
}
