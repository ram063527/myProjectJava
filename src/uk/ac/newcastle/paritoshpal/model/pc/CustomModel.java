package uk.ac.newcastle.paritoshpal.model.pc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mutable, custom-built PC model where parts can be added or
 * removed.
 * The name of a {@code CustomModel} is guaranteed to be unique and is generated
 * by the {@link CustomModelFactory}.Instances of this class should only be created
 * through factory.
 */
public final class CustomModel extends AbstractPCModel{

    private final List<String> parts;

    /**
     * Constructs a Custom PC Model instance from the given {@code name}
     * and by initializing a list of parts.
     * @param name
     */
    CustomModel(String name) {
        super(name);
        this.parts = new ArrayList<>();
    }

    /**
     * Adds a part to this custom model's list of parts.
     * Null or empty parts are ignored.
     * @param part the part to add.
     */
    public void addPart(String part) {
        if(part!=null && !part.trim().isEmpty()){
            this.parts.add(part);
        }
    }

    /**
     * Removes the first occurrence of a specific part from this custom model.
     * If the part is not in the list, no action is taken.
     * @param part the part to remove.
     */
    public void removePart(String part) {
        this.parts.remove(part);
    }

    /**
     * Compares this custom model to the specified object for equality.
     * @param o the object to compare this {@code CustomModel} against.
     *
     * @return {@code true} if the object is also a {@code CustomModel} and
     * has the same unique name; {@code false} otherwise.
     */

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

    /**
     * For a {@code CustomModel}, this returns a defensive copy to
     * the internal mutable list of parts.
     */
    @Override
    public List<String> getParts() {
        return new ArrayList<>(this.parts);
    }

    /**
     * Returns a string representation of custom model.
     *
     * @return a string in the format "Custom Model : [name]".
     */

    @Override
    public String toString() {
        return "Custom Model : "+this.getName();
    }
}
