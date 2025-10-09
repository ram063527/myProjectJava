package test.uk.ac.newcastle.paritoshpal.model.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.newcastle.paritoshpal.model.customer.Customer;
import uk.ac.newcastle.paritoshpal.model.customer.CustomerFactory;
import uk.ac.newcastle.paritoshpal.model.customer.Name;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    @DisplayName("Test getters delegate correctly to Name")
    void testGetters(){
        Customer customer = CustomerFactory.getCustomer("Paritosh","Pal");
        Name expectedName =  new Name("Paritosh","Pal");

        assertEquals(expectedName,customer.getName());
        assertEquals("paritosh",customer.getFirstName());
        assertEquals("pal",customer.getLastName());
    }

    @Test
    @DisplayName("Test toString() delegates correctly to Name")
    void testToString(){
        Customer customer = CustomerFactory.getCustomer("Paritosh","Pal");
        assertEquals("paritosh - pal",customer.toString());
    }

    @Test
    @DisplayName("Test equals() is based on Name")
    void testEquals() {
        Customer customer1 = CustomerFactory.getCustomer("Paritosh","Pal");
        Customer customer2 = CustomerFactory.getCustomer("Paritosh","Pal");
        Customer customer3 = CustomerFactory.getCustomer("Paritosh P","al");

        assertEquals(customer1,customer2);
        assertNotEquals(customer1,customer3);
        assertNotEquals(null,customer1);

    }

    @Test
    @DisplayName("Test hashcode() is based on Name")
    void testHashCode() {
        Customer customer1 = CustomerFactory.getCustomer("Paritosh","Pal");
        Customer customer2 = CustomerFactory.getCustomer("Paritosh","Pal");
        Customer customer3 = CustomerFactory.getCustomer("Paritosh P","al");

        assertEquals(customer1.hashCode(),customer2.hashCode());
        assertNotEquals(customer1.hashCode(),customer3.hashCode());
    }

}