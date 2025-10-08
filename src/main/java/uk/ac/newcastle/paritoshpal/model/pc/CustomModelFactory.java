package main.java.uk.ac.newcastle.paritoshpal.model.pc;

public final class CustomModelFactory {

    private static long modelCounter = 0;

    private CustomModelFactory() {
        // empty
    }
    public static CustomModel createCustomModel(String name) {

        String uniqueName = "custom-pc-" + (++modelCounter);
        return new CustomModel(uniqueName);
    }


}
