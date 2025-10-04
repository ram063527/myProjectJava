package main.java.uk.ac.newcastle.paritoshpal.service;

import main.java.uk.ac.newcastle.paritoshpal.model.customer.Customer;
import main.java.uk.ac.newcastle.paritoshpal.model.order.Order;
import main.java.uk.ac.newcastle.paritoshpal.model.payment.CreditCard;
import main.java.uk.ac.newcastle.paritoshpal.model.pc.PCModel;

import java.util.ArrayList;
import java.util.List;

public class PCShopImpl implements PCShop {

    private final List<Order> orderHistory = new ArrayList<>();

    @Override
    public Order placeOrder(List<PCModel> models, Customer customer, CreditCard creditCard) {
        return null;
    }

    @Override
    public void cancelOrder(Order order) {

    }
}
