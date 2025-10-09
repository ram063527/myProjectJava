package main.java.uk.ac.newcastle.paritoshpal.model.pc;

/**
 * A factory for creating {@link CustomModel} instances, each with a
 * guaranteed unique name.
 * This class provides a static method to generate new {@code CustomModel} object.
 */
public final class CustomModelFactory {

    private static long modelCounter = 0;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */

    private CustomModelFactory() {
        // empty
    }

    /**
     * Creates a new {@link CustomModel} instance with a guaranteed unique,
     * auto-generated name.
     *
     * @return a new, non-null {@code CustomModel} instance.
     */
    public static CustomModel createCustomModel() {

        String uniqueName = "custom-pc-" + (++modelCounter);
        return new CustomModel(uniqueName);
    }


}
