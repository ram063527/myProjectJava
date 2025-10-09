package test.uk.ac.newcastle.paritoshpal.model.customer;

import org.junit.jupiter.api.Test;
import uk.ac.newcastle.paritoshpal.model.customer.Name;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class NameTest {

    @Test
    void testNameCreation(){
        // Setup
        String firstName = "Paritosh";
        String lastName = "Pal";

        // Execution
        Name name = new Name(firstName,lastName);

        // verification
        //  Normal Case
        assertEquals("paritosh",name.getFirstName());
        assertEquals("pal",name.getLastName());

        // null case
        String firstName2 = null;
        String lastName2 = "Pal";
        assertThrowsExactly(IllegalArgumentException.class,()->{
            Name name2 = new Name(firstName2,lastName2);
        });

        // invalid character
        String firstName3 = "Paritosh@12";
        String lastName3 = "pal23";
        assertThrowsExactly(IllegalArgumentException.class,()->{
            Name name3 = new Name(firstName3,lastName3);
        });

        // normalization
        String firstName4 = "Paritosh    ";
        String lastName4 = "      Pal    ";
        assertEquals("paritosh",name.getFirstName());
        assertEquals("pal",name.getLastName());
    }

    @Test
    void getFirstName() {
    }

    @Test
    void getLastName() {
    }

    @Test
    void testToString() {
    }

    @Test
    void valueOf() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}