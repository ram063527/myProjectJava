package main.java.uk.ac.newcastle.paritoshpal.service;

import main.java.uk.ac.newcastle.paritoshpal.model.customer.Customer;
import main.java.uk.ac.newcastle.paritoshpal.model.order.Order;
import main.java.uk.ac.newcastle.paritoshpal.model.payment.CreditCard;
import main.java.uk.ac.newcastle.paritoshpal.model.pc.PCModel;

import java.util.List;

public interface PCShop {

    /**
     * Places a new order for a customer.
     * @param models The list of PC models being ordered.
     * @param customer The customer placing the order.
     * @param creditCard The credit card used for payment.
     * @return The newly created Order object.
     */
    Order placeOrder(List<PCModel> models, Customer customer, CreditCard creditCard);

    /**
     * Cancels an existing order.
     * @param order The order to be cancelled.
     */
    void cancelOrder(Order order);

    /**
     * Fulfills an existing order.
     * @param order The order to be fulfilled.
     * @return Details required for fulfillment (e.g., parts from warehouse, models from manufacturers).
     */
    // FulfillmentDetails fulfillOrder(Order order); // We will create this helper class later

    /**
     * Gets the customer with the most fulfilled orders.
     * @return An object containing the top customer and their order count.
     */
    // CustomerStats getLargestCustomer();

    /**
     * Gets the most frequently ordered preset model across all fulfilled orders.
     * @return An object containing the top model and its order count.
     */
    // ModelStats getMostOrderedModel();

    /**
     * Gets the most frequently used part in custom models across all fulfilled orders.
     * @return An object containing the top part and its usage count.
     */
    // PartStats getMostOrderedPart();
}
