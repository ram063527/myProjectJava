package main.java.uk.ac.newcastle.paritoshpal.model.pc;

import java.util.List;

/**
 * Represents the contract for all PC Models in the system.
 */
public interface PCModel {

    /**
     * Gets the name of the PC Model.
     * @return The model name.
     */
    String getName();

    /**
     * Gets the list of computer parts for this model.
     * @return A list of parts
     */
    List<String> getParts();
}
