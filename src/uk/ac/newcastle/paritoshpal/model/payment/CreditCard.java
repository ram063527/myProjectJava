package uk.ac.newcastle.paritoshpal.model.payment;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a credit card as an immutable object.
 * Equality is based solely on the unique card number.
 */
public final class CreditCard {


    private final String number;
    private final Date expiryDate;
    private final String holder;

    /**
     * Constructs a new {@code CreditCard} instance from the given number, expiry date
     * and holder name.
     * Should only be instantiated by the factory.
     *
     * @param number the unique 8-digit card number; must contain only digits.
     * @param expiryDate the expiration date of the card; cannot be null.
     * @param holder the cardholder's name; cannot be null, empty or contain invalid characters.
     * @throws IllegalArgumentException if any of the parameters are null, or if the {@code number}
     * is non 8-digits string, or if the {@code holder} is empty or contains characters other than
     * letters and spaces.
     */
    CreditCard( String number,  Date expiryDate, String holder) {
        this.number = validateNumber(number);
        if(expiryDate == null){
            throw new IllegalArgumentException("Invalid expiry date. Expiry date: "+expiryDate+" is invalid.");
        }
        this.expiryDate = new Date(expiryDate.getTime());
        this.holder = validateAndNormalizeHolder(holder);
    }

    /**
     * Returns the 8-digit card number.
     * @return the card number.
     */
    public String getNumber() {
        return number; // Strings are already Immutable
    }

    /**
     * Returns a defensive copy of the expiry date to protect immutability.
     * @return a copy of the expiry date.
     */
    public Date getExpiryDate() {
        return new Date(expiryDate.getTime());
    }

    /**
     * Returns the name of the cardholder.
     * @return the cardholder's name.
     */
    public String getHolder() {
        return holder; // Strings are already immutable
    }

    /**
     * Checks if the credit card is currently valid.
     * A card is considered valid if its expiry date is in the future.
     * @return {@code true} if the card has not expired, {@code false} otherwise.
     */

    public boolean isValid() {
        // All 3 components are set
        return number != null && expiryDate != null && holder != null && expiryDate.after(new Date());
    }

    /**
     * Returns the string representation of this credit card.
     * The format is "number holder expiryDate", for example "12345678 john 23/08/2032".
     *
     * @return the formatted string representation of the credit card.
     */
    @Override
    public String toString() {
        return number + " " + holder + " " + expiryDate.toString();
    }

    /**
     * Compares this credit card to the specified object for equality.
     *
     * @param o the object to compare this {@code CreditCard} against.
     * @return {@code true} if the object is also a {@code CreditCard} and has
     * the same card number; {@code false} otherwise.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }




    private String validateNumber(String number) {
        //Null check
        if(number == null) {
            throw new IllegalArgumentException("Card number cannot be null.");
        }
        // should contain only 8 "digits". no white spaces nothing
        char [] chars =  number.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(!Character.isDigit(chars[i])) {
                throw new IllegalArgumentException("Invalid card number. Character "+chars[i]+" is invalid.");
            }
        }
        if(chars.length != 8) {
            throw new IllegalArgumentException("Invalid card number. Card number must have 8 digits.");
        }
        return number;

    }

    /**
     * Validates and normalizes the holder name string.
     *
     * Normalization includes converting the string to lowercase, trimming leading/trailing
     * whitespace, and collapsing internal whitespace to a single space.
     * Validation ensures the name is not null/empty and contains only letters and spaces.
     *
     * @param holder the name string to process.
     * @return the validated and normalized holder name string.
     * @throws IllegalArgumentException if the {@code holder} is null, empty or contains
     * invalid characters.
     */
    private String validateAndNormalizeHolder(String holder) {
        // null and empty check
        if(holder == null || holder.trim().isEmpty()) {
            throw new IllegalArgumentException("Card holder cannot be null.");
        }
        // no leading and trailing zeros
        holder = holder.strip();
        // lowercase for simplicity
        holder = holder.toLowerCase();
        // replacing all clusters of whitespaces with a single whitespace
        holder = holder.replaceAll("\\s+", " ");
        // making sure all the characters are either whitespace or letters
        char[] chars = holder.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            if(!Character.isLetter(chars[i]) && !Character.isWhitespace(chars[i])) {
                throw new IllegalArgumentException("invalid holder. character: " + chars[i] + " is invalid");
            }
        }

        return  holder;
    }

}
