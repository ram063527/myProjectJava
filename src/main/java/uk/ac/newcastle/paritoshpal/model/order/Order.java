package main.java.uk.ac.newcastle.paritoshpal.model.order;

import main.java.uk.ac.newcastle.paritoshpal.model.customer.Customer;
import main.java.uk.ac.newcastle.paritoshpal.model.payment.CreditCard;
import main.java.uk.ac.newcastle.paritoshpal.model.pc.PCModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class Order {

    private final Customer customer;
    private final List<PCModel> models;
    private final CreditCard creditCard;
    private final Date orderDate;
    private OrderStatus status;


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


    public Customer getCustomer() {
        return customer;
    }

    public List<PCModel> getModels() {
        return new ArrayList<>(models);
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public Date getOrderDate() {
        return new Date(orderDate.getTime());
    }

    public OrderStatus getOrderStatus() {
        return status;
    }
    /**
     * Cancels the order if it is currently placed.
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
     * Fulfills the order if it is currently placed.
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
