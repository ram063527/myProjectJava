package uk.ac.newcastle.paritoshpal.model.pc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.ac.newcastle.paritoshpal.model.pc.PresetModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Preset Model tests")
class PresetModelTest {

    String name = "XPS13";
    String manufacturer = "Dell";
    List<String> parts = Arrays.asList("CPU", "Motherboard", "RAM", "GPU");

    @Nested
    @DisplayName("Preset Model creation tests")
    class PresetModelCreationTests{

        @Test
        @DisplayName("Test normal preset model creation")
        void testNormalModelCreation(){
            PresetModel presetModel = new PresetModel(name, manufacturer, parts);
            assertEquals("xps13", presetModel.getName());
            assertEquals("dell",presetModel.getManufacturer());
            assertEquals(parts, presetModel.getParts());
        }

        @Test
        @DisplayName("Test name and manufacturer normalization")
        void testNormalization(){
            PresetModel presetModel = new PresetModel("    Inspiron    543", "  Dell  ", parts);
            assertEquals("inspiron 543", presetModel.getName());
            assertEquals("dell", presetModel.getManufacturer());
            assertEquals(parts, presetModel.getParts());
        }

        @Test
        @DisplayName("Test invalid preset model creation")
        void testInvalidCreations(){
            // Invalid characters in name and manufacturer
            assertThrowsExactly(IllegalArgumentException.class,()->
                    new PresetModel("Inspiron@123", "Dell@234", parts));

            // Null values
            assertThrowsExactly(IllegalArgumentException.class,()->
                    new PresetModel(null, null, parts));

            // Empty list
            List<String> emptyPartsList = new ArrayList<>();
            assertThrowsExactly(IllegalArgumentException.class,()->
                    new PresetModel("Inspiron", "Dell", emptyPartsList));


        }
    }

    @Nested
    @DisplayName("Getter Methods Tests")
    class GetterMethodsTests{

        PresetModel presetModel = new PresetModel("Inspiron", "Dell", parts);

        @Test
        @DisplayName("Test getName()")
        void testGetName(){
            assertEquals("inspiron", presetModel.getName());
        }

        @Test
        @DisplayName("Test getManufacturer()")
        void testGetManufacturer(){
            assertEquals("dell",presetModel.getManufacturer());
        }

        @Test
        @DisplayName("Test getParts()")
        void testGetParts(){
            assertEquals(parts, presetModel.getParts());
        }

        @Test
        @DisplayName("Test getParts() returns unmodifiable list")
        void testGetPartsListIsUnmodifiable(){
            List<String> retrivedParts = presetModel.getParts();
            assertThrows(UnsupportedOperationException.class,()->retrivedParts.add("SSD : 2TB"));
        }

    }

    @Nested
    @DisplayName("Object Class method tests")
    class ObjectTests{


        @Test
        @DisplayName("Test equals() and hashcode()")
        void testEqualsAndHashCode(){
            PresetModel model1 = new PresetModel(name,manufacturer,parts);
            PresetModel model2 = new PresetModel("XPS13", "Dell", List.of("CPU",
                    "Motherboard",
                    "RAM",
                    "GPU"));
            PresetModel model3 = new PresetModel("Office Pro", manufacturer, parts); // Different name
            PresetModel model4 = new PresetModel(name, "Competitor Corp", parts); // Different manufacturer
            PresetModel model5 = new PresetModel(name, manufacturer, List.of("CPU: i7")); // Different parts

            // Equality
            assertEquals(model1, model2);

            // Not equal
            assertNotEquals(model1, model3);
            assertNotEquals(model1, model4);
            assertNotEquals(model1, model5);

            // Hashcode
            assertEquals(model1.hashCode(), model2.hashCode());
            assertNotEquals(model1.hashCode(), model3.hashCode());

        }

        @Test
        @DisplayName("Test toString()")
        void testToString() {
            PresetModel model = new PresetModel(name, manufacturer, parts);
            assertEquals("Preset Model: dell xps13", model.toString());
        }



    }

}