package main.java.uk.ac.newcastle.paritoshpal.model.pc;

import java.util.HashMap;
import java.util.Map;


public class CustomModelFactory {

    private static long modelCounter = 0;

    private CustomModelFactory() {
        // empty
    }
    public static CustomModel createCustomModel(String name) {

        String uniqueName = "custom-pc-" + (++modelCounter);
        return new CustomModel(uniqueName);
    }


}
