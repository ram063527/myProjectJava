package main.java.uk.ac.newcastle.paritoshpal.model.payment;

import java.util.Date;
import java.util.Objects;

public final class CreditCard {

    private final String number;
    private final Date expiryDate;
    private final String holder;

    CreditCard(final String number, final Date expiryDate, final String holder) {
        this.number = validateNumber(number);
        if(expiryDate == null){
            throw new IllegalArgumentException("Invalid expiry date. Expiry date: "+expiryDate+" is invalid.");
        }
        this.expiryDate = new Date(expiryDate.getTime());
        this.holder = validateAndNormalizeHolder(holder);
    }

    public String getNumber() {
        return number; // Strings are already Immutable
    }

    public Date getExpiryDate() {
        return new Date(expiryDate.getTime());
    }

    public String getHolder() {
        return holder; // Strings are already immutable
    }

    public boolean isValid() {
        // All 3 components are set
        return number != null && expiryDate != null && holder != null && expiryDate.after(new Date());
    }

    @Override
    public String toString() {
        return number + " " + holder + " " + expiryDate.toString();
    }

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
