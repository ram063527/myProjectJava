package test.uk.ac.newcastle.paritoshpal.model.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.newcastle.paritoshpal.model.customer.Customer;
import uk.ac.newcastle.paritoshpal.model.customer.CustomerFactory;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Customer Factory Unit Tests")
class CustomerFactoryTest {

    @Test
    @DisplayName("Factory ensuring uniqueness test")
    void testFactoryReturnsSameInstance(){
        Customer customer1 = CustomerFactory.getCustomer("Paritosh","Pal");
        Customer customer2 =  CustomerFactory.getCustomer("Paritosh","Pal");

        assertSame(customer1,customer2);
        assertEquals(customer1,customer2);
    }

    @Test
    @DisplayName("Factory returns different instances for different names")
    void testFactoryReturnsDifferentInstance(){
        Customer customer1 = CustomerFactory.getCustomer("Paritosh","Pal");
        Customer customer2 = CustomerFactory.getCustomer("Paritoshik","Paul");

        assertNotSame(customer1,customer2);
        assertNotEquals(customer1,customer2);
    }

}