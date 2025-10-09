package main.java.uk.ac.newcastle.paritoshpal.service;

import main.java.uk.ac.newcastle.paritoshpal.dto.CustomerStats;
import main.java.uk.ac.newcastle.paritoshpal.dto.ModelStats;
import main.java.uk.ac.newcastle.paritoshpal.dto.PartsStats;
import main.java.uk.ac.newcastle.paritoshpal.model.customer.Customer;
import main.java.uk.ac.newcastle.paritoshpal.model.fulfillment.FulfillmentDetails;
import main.java.uk.ac.newcastle.paritoshpal.model.order.Order;
import main.java.uk.ac.newcastle.paritoshpal.model.payment.CreditCard;
import main.java.uk.ac.newcastle.paritoshpal.model.pc.PCModel;

import java.util.List;

/**
 * Defines the contract for the PC Shop.
 * This interface provides methods for managing the entire lifecycle of an order,
 * from placement to fulfillment, as well as generating analytics on order history.
 */
public interface PCShop {

    /**
     * Places a new order for a customer.
     *
     * @param models     the list of PC models being ordered; cannot be null or empty.
     * @param customer   the customer placing the order; cannot be null.
     * @param creditCard the credit card used for payment; cannot be null and must be valid.
     * @return the newly created {@code Order} object, which is added to the order history.
     * @throws IllegalArgumentException if any of the parameters are null, the models list is empty,
     * or the credit card is invalid.
     */
    Order placeOrder(List<PCModel> models, Customer customer, CreditCard creditCard);

    /**
     * Cancels an existing order.
     *
     * @param order the order to be cancelled; cannot be null.
     * @throws IllegalArgumentException if the provided {@code order} is null.
     * @throws IllegalStateException    if the order is not in a state that can be cancelled
     */
    void cancelOrder(Order order);

    /**
     * Fulfills an existing order and generates the necessary details for processing.
     *
     * @param order the order to be fulfilled; cannot be null.
     * @return the {@code FulfillmentDetails} required to process the order.
     * @throws IllegalArgumentException if the provided {@code order} is null.
     * @throws IllegalStateException    if the order is not in a state that can be fulfilled
     */
     FulfillmentDetails fulfillOrder(Order order);

    /**
     * Gets the customer with the most fulfilled orders.
     * In case of a tie, the customer whose name comes first alphabetically is returned.
     *
     * @return a {@code CustomerStats} object containing the top customer and their order count,
     * or {@code null} if there are no fulfilled orders.
     */
     CustomerStats getLargestCustomer();

    /**
     * Gets the most frequently ordered preset model across all fulfilled orders.
     * Tie-breaking is done first by manufacturer name, then by model name, alphabetically.
     *
     * @return a {@code ModelStats} object containing the top model and its order count,
     * or {@code null} if no preset models have been ordered.
     */
     ModelStats getMostOrderedModel();

    /**
     * Gets the most frequently used part in custom models across all fulfilled orders.
     *
     * In case of a tie, the part whose name comes first alphabetically is returned.
     *
     * @return a {@code PartsStats} object containing the top part and its usage count,
     * or {@code null} if no custom parts have been ordered.
     */
     PartsStats getMostOrderedPart();
}
