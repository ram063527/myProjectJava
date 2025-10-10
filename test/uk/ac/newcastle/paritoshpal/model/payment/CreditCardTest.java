package uk.ac.newcastle.paritoshpal.model.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.ac.newcastle.paritoshpal.model.payment.CreditCard;
import uk.ac.newcastle.paritoshpal.model.payment.CreditCardFactory;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {


    String validNumber = "12345678";
    String validHolder = "paritosh pal";
    Date validDate = new Date();
    CreditCard creditCard = CreditCardFactory.getCreditCard(validNumber,validDate,validHolder);
    private Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal.getTime();

    }

    @Nested
    @DisplayName("Credit card creation and Normalization Tests")
    class CreditCardCreationTests{



        @Test
        @DisplayName("Test normal credit card creation")
        void testNormalCreditCardCreation(){
            // setup
            String number = "12345678";
            Date expiryDate = new Date();
            String holder = "paritosh pal";

            CreditCard cc = CreditCardFactory.getCreditCard(number, expiryDate, holder);

            Date expectedDate = new Date();
            assertEquals("12345678", cc.getNumber());
            assertEquals("paritosh pal", cc.getHolder());
            assertEquals(expectedDate, cc.getExpiryDate());
        }

        @Test
        @DisplayName("Test creation with invalid number")
        void testInvalidNumber(){
            Date expiryDate = new Date();
            String holder = "paritosh pal";
            Date expectedDate = new Date();
            assertThrowsExactly(IllegalArgumentException.class,()-> CreditCardFactory.getCreditCard("12345", expiryDate, holder));
            assertThrowsExactly(IllegalArgumentException.class,()-> CreditCardFactory.getCreditCard(null, expiryDate, holder));
            assertThrowsExactly(IllegalArgumentException.class,()-> CreditCardFactory.getCreditCard("abcde", expiryDate, holder));
        }

        @Test
        @DisplayName("Test creation with invalid holder")
        void testInvalidHolder(){
            Date expiryDate = new Date();
            String number = "12345678";
            Date expectedDate = new Date();
            assertThrowsExactly(IllegalArgumentException.class,()-> CreditCardFactory.getCreditCard(number, expiryDate, "paritosh@123"));
            assertThrowsExactly(IllegalArgumentException.class,()-> CreditCardFactory.getCreditCard(number, expiryDate, null));
        }

        @Test
        void testNullDate(){
            assertThrowsExactly(IllegalArgumentException.class,()-> CreditCardFactory.getCreditCard("12345678", null, "Valid Holder"));
        }


    }



    @Nested
    @DisplayName("Functionality tests")
    class FunctionalityTests {




        @Test
        @DisplayName("Test getHolder()")
        void testGetHolder(){
            assertEquals("paritosh pal",validHolder);
        }

        @Test
        @DisplayName("Test getNumber()")
        void testGetNumber(){
            assertEquals("12345678",validNumber);
        }

        @Test
        @DisplayName("Test getExpiryDate()")
        void testGetExpiryDate(){
            assertEquals(validDate,creditCard.getExpiryDate());
        }

        @Test
        @DisplayName("Test isValid for future Dates")
        void testIsValidForFutureDate() {
            Date futureDate = createDate(2030, Calendar.DECEMBER, 31);
            CreditCard card = CreditCardFactory.getCreditCard("12341234", futureDate, "valid holder");
            assertTrue(card.isValid());
        }

        @Test
        @DisplayName("isValid() returns false for expired card")
        void testIsValidForPastDate() {
            Date pastDate = createDate(2020, Calendar.JANUARY, 1);
            CreditCard card = CreditCardFactory.getCreditCard("44445555", pastDate, "Expired Holder");

            assertFalse(card.isValid());
        }

    }

    @Nested
    @DisplayName("Object methods tests")
    class ObjectTests {

        Date pastDate = createDate(2020, Calendar.JANUARY, 1);
        Date futureDate = createDate(2030, Calendar.DECEMBER, 31);

        @Test
        @DisplayName("Test equals() is based only on card number")
        void testEqualsContract() {


            CreditCard card1 = CreditCardFactory.getCreditCard(validNumber, futureDate, "John Doe");
            CreditCard card2 = CreditCardFactory.getCreditCard(validNumber, pastDate, "Jane Doe"); // Same number, different details
            CreditCard card3 = CreditCardFactory.getCreditCard("11112222", futureDate, "John Doe"); // Different number

            assertEquals(card1, card2, "Cards with the same number should be equal");
            assertNotEquals(card1, card3, "Cards with different numbers should not be equal");
        }

        @Test
        @DisplayName("Test hashCode() is based only on card number")
        void testHashCodeContract() {
            CreditCard card1 = CreditCardFactory.getCreditCard(validNumber, futureDate, "John Doe");
            CreditCard card2 = CreditCardFactory.getCreditCard(validNumber, pastDate, "Jane Doe"); // Same number
            CreditCard card3 = CreditCardFactory.getCreditCard("11112222", futureDate, "John Doe"); // Different number

            assertEquals(card1.hashCode(), card2.hashCode());
            assertNotEquals(card1.hashCode(), card3.hashCode());
        }

    }


}