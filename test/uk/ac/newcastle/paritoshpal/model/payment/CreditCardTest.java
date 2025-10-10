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

    private Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day,0,0,0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();

    }

    @Nested
    @DisplayName("Credit card creation and Normalization Tests")
    class CreditCardCreationTests{



        @Test
        @DisplayName("Test normal credit card creation")
        void testNormalCreditCardCreation(){
            // setup
            String number = "11223344";
            Date expiryDate = createDate(2025,Calendar.MARCH,22);
            String holder = "paritosh pal";

            CreditCard cc = CreditCardFactory.getCreditCard(number, expiryDate, holder);

            Date expectedDate =  createDate(2025,2,22);
            assertEquals("11223344", cc.getNumber());
            assertEquals("paritosh pal", cc.getHolder());
            assertEquals(expectedDate, cc.getExpiryDate());
        }

        @Test
        @DisplayName("Test creation with invalid number")
        void testInvalidNumber(){
            Date expiryDate = createDate(2028,Calendar.MARCH,22);
            String holder = "paritosh pal";
            assertThrowsExactly(IllegalArgumentException.class,()-> CreditCardFactory.getCreditCard("12345", expiryDate, holder));
            assertThrowsExactly(IllegalArgumentException.class,()-> CreditCardFactory.getCreditCard(null, expiryDate, holder));
            assertThrowsExactly(IllegalArgumentException.class,()-> CreditCardFactory.getCreditCard("abcde", expiryDate, holder));
        }

        @Test
        @DisplayName("Test creation with invalid holder")
        void testInvalidHolder(){
            Date expiryDate = createDate(2029, Calendar.MAY, 2);
            String number = "98765432";
            assertThrowsExactly(IllegalArgumentException.class,()-> CreditCardFactory.getCreditCard(number, expiryDate, "paritosh@123"));
            assertThrowsExactly(IllegalArgumentException.class,()-> CreditCardFactory.getCreditCard(number, expiryDate, null));
        }

        @Test
        void testNullDate(){
            assertThrowsExactly(IllegalArgumentException.class,()-> CreditCardFactory.getCreditCard("33445566", null, "Valid Holder"));
        }


    }



    @Nested
    @DisplayName("Functionality tests")
    class FunctionalityTests {


        @Test
        @DisplayName("Test getHolder()")
        void testGetHolder(){
            Date expiry = createDate(2030, Calendar.JUNE, 15);
            CreditCard card = CreditCardFactory.getCreditCard("44556677", expiry, "  PARITOSH PAL  ");
            assertEquals("paritosh pal",card.getHolder());
        }

        @Test
        @DisplayName("Test getNumber()")
        void testGetNumber(){
            Date expiry = createDate(2032, Calendar.JUNE, 15);
            CreditCard card = CreditCardFactory.getCreditCard("44326677", expiry, "  PARITOSH PAL  ");
            assertEquals("44326677",card.getNumber());
        }

        @Test
        @DisplayName("Test getExpiryDate()")
        void testGetExpiryDate(){
            Date expiry = createDate(2032, Calendar.JUNE, 15);
            CreditCard card = CreditCardFactory.getCreditCard("44326677", expiry, "  PARITOSH PAL  ");
            assertEquals(expiry,card.getExpiryDate());
        }

        @Test
        @DisplayName("Test isValid for future Dates")
        void testIsValidForFutureDate() {
            Date futureDate = createDate(2030, Calendar.DECEMBER, 31);
            CreditCard card = CreditCardFactory.getCreditCard("55667788", futureDate, "valid holder");
            assertTrue(card.isValid());
        }

        @Test
        @DisplayName("isValid() returns false for expired card")
        void testIsValidForPastDate() {
            Date pastDate = createDate(2020, Calendar.JANUARY, 1);
            CreditCard card = CreditCardFactory.getCreditCard("66778899", pastDate, "Expired Holder");

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

            String sameNumber = "77889900";
            Date pastDate = createDate(2020, Calendar.JANUARY, 1);
            Date futureDate = createDate(2030, Calendar.DECEMBER, 31);

            CreditCard card1 = CreditCardFactory.getCreditCard(sameNumber, futureDate, "John Doe");
            CreditCard card2 = CreditCardFactory.getCreditCard(sameNumber, pastDate, "Jane Doe"); // Same number, different details
            CreditCard card3 = CreditCardFactory.getCreditCard("11112222", futureDate, "John Doe"); // Different number

            assertEquals(card1, card2);
            assertNotEquals(card1, card3);
        }

        @Test
        @DisplayName("Test hashCode() is based only on card number")
        void testHashCodeContract() {

            String sameNumber = "88990011";
            Date pastDate = createDate(2020, Calendar.JANUARY, 1);
            Date futureDate = createDate(2030, Calendar.DECEMBER, 31);

            CreditCard card1 = CreditCardFactory.getCreditCard(sameNumber, futureDate, "John Doe");
            CreditCard card2 = CreditCardFactory.getCreditCard(sameNumber, pastDate, "Jane Doe"); // Same number
            CreditCard card3 = CreditCardFactory.getCreditCard("22334455", futureDate, "John Doe"); // Different number

            assertEquals(card1.hashCode(), card2.hashCode());
            assertNotEquals(card1.hashCode(), card3.hashCode());
        }

    }


}