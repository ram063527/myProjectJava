package main.java.uk.ac.newcastle.paritoshpal.model.payment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class CreditCardFactory {

    private static final Map<String, CreditCard> creditCards = new HashMap<>();

    public static CreditCard getCreditCard(String number, Date expiryDate, String holder) {

        if(creditCards.containsKey(number)) {
            throw new IllegalArgumentException("Credit card already exists!");
        }
        CreditCard card = new CreditCard(number,expiryDate,holder);
        creditCards.put(number, card);
        return card;
    }
}
