package uk.ac.newcastle.paritoshpal.model.order;



import uk.ac.newcastle.paritoshpal.model.customer.Customer;
import uk.ac.newcastle.paritoshpal.model.payment.CreditCard;
import uk.ac.newcastle.paritoshpal.model.pc.PCModel;

import java.util.Date;
import java.util.List;

/**
 * Represents a customer's order in the system.
 * An order is an object containing the customer, the PC models they
 * ordered, and the payment details. Its state ( placed, cancelled, fulfilled)
 * is managed internally.
 */
public final class Order {

    private final Customer customer;
    private final List<PCModel> models;
    private final CreditCard creditCard;
    private final Date orderDate;
    private OrderStatus status;

    /**
     * Constructs a new {@code Order} instance.
     *
     * Upon creation, the order's date is set to the current time and stats
     * is set to {@code PLACED}.
     * @param creditCard the credit card used for the purchase; cannot be null.
     * @param models the list of pc models intended being ordered; cannot be null or empty.
     * @param customer the information of customer making the purchase; cannot be null or empty.
     * @throws IllegalArgumentException if either of these fields are null or empty.
     */

    public Order(CreditCard creditCard, List<PCModel> models, Customer customer) {

        if (customer == null || creditCard == null || models == null || models.isEmpty()) {
            throw new IllegalArgumentException("Customer, Credit Card, and a non-empty list of models are required.");
        }
        this.customer = customer;
        this.creditCard = creditCard;

        this.models =  List.copyOf(models);
        this.orderDate = new Date();

        this.status = OrderStatus.PLACED;
    }

    /**
     * Gets the customer who placed the order.
     * @return the {@code Customer}.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets the unmodifiable list of PC models in this order.
     * @return the list of {@code PCModel}s.
     */

    public List<PCModel> getModels() {
        // since list.copyof used, this is already immutable.
        return models;
    }

    /**
     * Gets the credit card used for this order.
     * @return the {@code CreditCard}.
     */

    public CreditCard getCreditCard() {
        return creditCard;
    }

    /**
     * Gets the date and time when the order was placed.
     * @return a defensive copy of the order date.
     */
    public Date getOrderDate() {
        return new Date(orderDate.getTime());
    }

    /**
     * Gets the current status of the order.
     * @return the current {@code OrderStatus}.
     */

    public OrderStatus getOrderStatus() {
        return status;
    }
    /**
     * Cancels the order if it is currently in the {@code PLACED} state.
     * @throws IllegalStateException if the order has already been fulfilled or cancelled.
     */
    public void cancel() {
        if (this.status == OrderStatus.PLACED) {
            this.status = OrderStatus.CANCELLED;
        } else {
            throw new IllegalStateException("Cannot cancel an order that is already " + this.status);
        }
    }

    /**
     * Fulfills the order if it is currently in the {@code PLACED}.
     * @throws IllegalStateException if the order has been cancelled or already fulfilled.
     */
    public void fulfill() {
        if (this.status == OrderStatus.PLACED) {
            this.status = OrderStatus.FULFILLED;
        } else {
            throw new IllegalStateException("Cannot fulfill an order that is " + this.status);
        }
    }
}
