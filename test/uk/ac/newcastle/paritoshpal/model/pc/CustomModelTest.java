package uk.ac.newcastle.paritoshpal.model.pc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.ac.newcastle.paritoshpal.model.pc.CustomModel;
import uk.ac.newcastle.paritoshpal.model.pc.CustomModelFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Custom model unit tests")
class CustomModelTest {

    private CustomModel customModel;

    @BeforeEach
    void setUp() {
        customModel = CustomModelFactory.createCustomModel();
    }

    @Nested
    @DisplayName("Custom model creation tests")
    class CustomModelCreationTests{

        @Test
        @DisplayName("Test new custom model creation")
        void testNormalCreation(){
            assertTrue(customModel.getParts().isEmpty());
            String name = customModel.getName();
            assertNotNull(name);
            assertTrue(name.matches("custom-pc-\\d+"));
        }

    }

    @Nested
    @DisplayName("Part Manipulation")
    class PartManipulationTests{

        @Test
        @DisplayName("Test addPart() method")
        void testAddPart(){
            customModel.addPart("CPU: Ryzen 7");
            assertEquals(1, customModel.getParts().size());
            assertEquals("CPU: Ryzen 7", customModel.getParts().get(0));
            assertTrue(customModel.getParts().contains("CPU: Ryzen 7"));
        }

        @Test
        @DisplayName("Test addPart() method with invalid params")
        void testAddInvalidPart(){
            assertThrowsExactly(IllegalArgumentException.class,()->
                    customModel.addPart(null));
            assertThrowsExactly(IllegalArgumentException.class,()->
                    customModel.addPart(""));
            assertThrowsExactly(IllegalArgumentException.class,()->
                    customModel.addPart("        "));

        }

        @Test
        @DisplayName("Test removeParts() method")
        void testRemoveExistingPart(){
            String partToRemove = "RAM: 24GB";
            customModel.addPart(partToRemove);
            customModel.addPart("CPU: Ryzen 7");
            assertTrue( customModel.removePart(partToRemove));
            assertEquals(1, customModel.getParts().size());
            assertFalse(customModel.getParts().contains(partToRemove));
        }

        @Test
        @DisplayName("Test")
        void testRemoveNonExistentPart(){
            customModel.addPart("CPU: Ryzen 7");
            customModel.addPart("RAM: 24GB");
            assertFalse(customModel.removePart("RAM: 65GB"));
        }
    }

    @Nested
    @DisplayName("Getter Methods test")
    class GetterMethodsTest{

        @Test
        @DisplayName("Test getPart() method")
        void testGetParts(){
            customModel.addPart("CPU: Ryzen 7");
            customModel.addPart("RAM: 24GB");
            List<String> retrievedParts =  customModel.getParts();
            assertEquals(2, retrievedParts.size());
            assertTrue(retrievedParts.contains("CPU: Ryzen 7"));
            assertTrue(retrievedParts.contains("RAM: 24GB"));

        }
    }

    @Nested
    @DisplayName("Object class method tests")
    class ObjectMethodsTest{

        @Test
        @DisplayName("Test equals() is based only on name")
        void testEquals() {
            CustomModel model1 = CustomModelFactory.createCustomModel();
            CustomModel model2 = CustomModelFactory.createCustomModel(); // A different model

            // Manually create a reference to model1 to test equality
            CustomModel model1Ref = model1;

            assertEquals(model1, model1Ref);
            assertNotEquals(model1, model2);

            // hashcode
            assertNotEquals(model1.hashCode(),model2.hashCode());

        }

        @Test
        @DisplayName("Test toString()")
        void testToString() {
            // The name is auto-generated, so we just check the format
            assertTrue(customModel.toString().startsWith("Custom Model : custom-pc-"));
        }
    }

}