package test.uk.ac.newcastle.paritoshpal.model.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.newcastle.paritoshpal.model.payment.CreditCard;
import uk.ac.newcastle.paritoshpal.model.payment.CreditCardFactory;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CreditCardFactory Unit Tests")
class CreditCardFactoryTest {

    private Date date1;
    private Date date2;

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2030, Calendar.DECEMBER, 1);
        date1 = calendar.getTime();

        calendar.set(2032, Calendar.DECEMBER, 1);
        date2 = calendar.getTime();

    }

    @Test
    @DisplayName("Test factory for same instance")
    void testFactoryReturnsSameInstance() {
        CreditCard card1 = CreditCardFactory.getCreditCard("12345678", date1, "paritosh pal");
        CreditCard card2 = CreditCardFactory.getCreditCard("12345678", date2, "paritosh pal");

        assertSame(card1, card2);
    }

    @Test
    @DisplayName("Test factory with same number different holder")
    void testFactoryWithSameNumberDifferentHolder() {
        CreditCard original = CreditCardFactory.getCreditCard("12345678", date1, "paritosh pal");
        CreditCard retrieved = CreditCardFactory.getCreditCard("12345678", date2, "john pal");

        assertSame(original, retrieved);
        // Holder should come from the original
        assertEquals("paritosh pal", retrieved.getHolder());
        // Expiry date should come from the original
        assertEquals(date1, retrieved.getExpiryDate());

    }

    @Test
    @DisplayName("Test factory with different numbers")
    void testFactoryWithDifferentNumbers(){
        CreditCard card1 = CreditCardFactory.getCreditCard("11111111", date1, "Holder A");
        CreditCard card2 = CreditCardFactory.getCreditCard("22222222", date1, "Holder B");

        // Factory should return different instances for different numbers.
        assertNotSame(card1, card2);
    }

}