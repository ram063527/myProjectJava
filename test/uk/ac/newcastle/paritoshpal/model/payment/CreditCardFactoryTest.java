package uk.ac.newcastle.paritoshpal.model.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CreditCardFactory Unit Tests")
class CreditCardFactoryTest {

    private Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day,0,0,0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();

    }


    @Test
    @DisplayName("Test factory for same instance")
    void testFactoryReturnsSameInstance() {
        Date date1 = createDate(2030, Calendar.JANUARY, 1);
        Date date2 = createDate(2032, Calendar.FEBRUARY, 2);
        String cardNumber = "11110000";
        CreditCard card1 = CreditCardFactory.getCreditCard("87789865", date1, "paritosh pal");
        CreditCard card2 = CreditCardFactory.getCreditCard("87789865", date2, "tatsavi pal");

        assertSame(card1, card2);
    }

    @Test
    @DisplayName("Test factory with same number different holder")
    void testFactoryWithSameNumberDifferentHolder() {
        Date originalDate = createDate(2031, Calendar.MARCH, 3);
        Date newDate = createDate(2033, Calendar.APRIL, 4);
        String cardNumber = "22220000";

        CreditCard original = CreditCardFactory.getCreditCard(cardNumber, originalDate, "paritosh pal");
        CreditCard retrieved = CreditCardFactory.getCreditCard(cardNumber, newDate, "john pal");

        assertSame(original, retrieved);
        // Holder should come from the original
        assertEquals("paritosh pal", retrieved.getHolder());
        // Expiry date should come from the original
        assertEquals(originalDate, retrieved.getExpiryDate());

    }

    @Test
    @DisplayName("Test factory with different numbers")
    void testFactoryWithDifferentNumbers(){

        Date date = createDate(2034, Calendar.MAY, 5);


        CreditCard card1 = CreditCardFactory.getCreditCard("33330000", date, "Holder A");
        CreditCard card2 = CreditCardFactory.getCreditCard("44440000", date, "Holder B");

        // Factory should return different instances for different numbers.
        assertNotSame(card1, card2);
    }

}