package uk.ac.newcastle.paritoshpal.model.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.ac.newcastle.paritoshpal.model.customer.Name;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Name} class.
 */
class NameTest {

    @Nested
    @DisplayName("Name creation and Normalization Tests")
    class NameCreationTests{

        @Test
        @DisplayName("Test normal name creation")
        void testNormalNameCreation(){
            // setup
            String firstName = "Paritosh";
            String lastName = "Pal";
            Name name = new Name(firstName,lastName);
            assertEquals("paritosh",name.getFirstName());
            assertEquals("pal",name.getLastName());
        }

        @Test
        @DisplayName("Test name creation with leading and trailing whitespace")
        void testNameNormalizationWhitespace(){
            String firstName = "  Paritosh          ";
            String lastName = " Pal    ";

            Name name = new Name(firstName,lastName);
            assertEquals("paritosh",name.getFirstName());
            assertEquals("pal",name.getLastName());

        }

        @Test
        @DisplayName("Test name creation with internal whitespaces")
        void testNameNormalizationInternalWhitespace(){
            String firstName = "John    Doe";
            String lastName = "Van   Der        Beek";
            Name name = new Name(firstName,lastName);
            assertEquals("john doe",name.getFirstName());
            assertEquals("van der beek",name.getLastName());
        }
    }

    @Nested
    @DisplayName("Invalid name creation tests")
    class InvalidNameCreationTests{

        @Test
        @DisplayName("Test creation with null first or last names")
        void testNullName(){
            assertThrowsExactly(IllegalArgumentException.class,()->
                    new Name("paritosh",null));
        }

        @Test
        @DisplayName("Test creation with empty name")
        void testEmptyName(){
            assertThrowsExactly(IllegalArgumentException.class,()->
                    new Name("","Pal"));
        }

        @Test
        @DisplayName("Test creation with invalid character")
        void testInvalidCharacters(){
            assertThrowsExactly(IllegalArgumentException.class,()->
                    new Name("paritosh@23232","pal"));
        }

    }

    @Nested
    @DisplayName("Object method tests")
    class ObjectMethodTests{

        @Test
        @DisplayName("Test toString()")
        void testToString(){
            Name name = new Name("Paritosh","Pal");
            assertEquals("paritosh - pal",name.toString());
        }

        @Test
        @DisplayName("Test equals() and hashcode()")
        void testEqualsAndHashCode(){
            Name name1 = new Name("Paritosh","Pal");
            Name name2 = new Name("Paritosh","Pal");
            Name name3 = new Name("Paritosh P","al");

            // Equals
            assertEquals(name1,name2);
            assertEquals(name2,name1);
            assertNotEquals(name1,name3);

            // hashcode
            assertEquals(name1.hashCode(),name2.hashCode());
            assertNotEquals(name1.hashCode(),name3.hashCode());

        }

    }

    @Nested
    @DisplayName("Test valueOf() Method")
    class valueOfTests{

        @Test
        @DisplayName("Test valueOf() with valid string")
        void testValueOfValidString(){
            String nameStr = "Paritosh - pal";
            Name name = Name.valueOf(nameStr);
            assertEquals("paritosh",name.getFirstName());
            assertEquals("pal",name.getLastName());
        }

        @Test
        @DisplayName("Test valueOf with invalid format")
        void testValueOfInvalidFormat(){
            String nameStr = "Paritosh pal";
            assertThrowsExactly(IllegalArgumentException.class,()->
            {
                Name name = Name.valueOf(nameStr);
            });
        }

        @Test
        @DisplayName("Test valueOf with null")
        void testValueOfNull(){
            String nameStr = null;
            assertThrowsExactly(IllegalArgumentException.class,()->
            {
                Name name = Name.valueOf(nameStr);
            }
            );
        }
    }
}