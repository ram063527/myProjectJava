package uk.ac.newcastle.paritoshpal.model.payment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A factory for creating and managing unique {@link CreditCard} instances.
 * This class ensures that for any given card number, only one {@code CreditCard}
 * object exists within the application.
 */
public final class CreditCardFactory {

    private static final Map<String, CreditCard> creditCards = new HashMap<>();

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private CreditCardFactory() {
        // Empty by design.
    }

    /**
     * Gets the unique {@link CreditCard} instance for the given number.
     * If a card with the specified number already exists, the existing
     * card instance is returned.Any provided {@code expiryDate} or {@code holder} details
     * for an existing card will be ignored.
     * If no card with the specified number exists, a new {@code CreditCard} is
     * created using the provided details, stored for future requests, and then returned.
     *
     * @param number the unique 8-digit card number.
     * @param expiryDate the expiry date, used only if a new card is created.
     * @param holder the cardholder's name, used only if a new card is created.
     * @return the unique, non-null {@code CreditCard} instance for the given number.
     * @throws IllegalArgumentException if the details are used to create a new card
     * and they are invalid,
     */
    public static CreditCard getCreditCard(String number, Date expiryDate, String holder) {

        if(creditCards.containsKey(number)) {
            return creditCards.get(number);
        }
        CreditCard card = new CreditCard(number,expiryDate,holder);
        creditCards.put(number, card);
        return card;
    }
}
