package main.java.uk.ac.newcastle.paritoshpal.service;

import main.java.uk.ac.newcastle.paritoshpal.model.customer.Customer;
import main.java.uk.ac.newcastle.paritoshpal.model.fulfillment.FulfillmentDetails;
import main.java.uk.ac.newcastle.paritoshpal.model.order.Order;
import main.java.uk.ac.newcastle.paritoshpal.model.payment.CreditCard;
import main.java.uk.ac.newcastle.paritoshpal.model.pc.CustomModel;
import main.java.uk.ac.newcastle.paritoshpal.model.pc.PCModel;
import main.java.uk.ac.newcastle.paritoshpal.model.pc.PresetModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PCShopImpl implements PCShop {

    private final List<Order> orderHistory = new ArrayList<>();

    @Override
    public Order placeOrder(List<PCModel> models, Customer customer, CreditCard creditCard) {
        // 1. Validate all inputs first
        if (models == null || models.isEmpty() || customer == null || creditCard == null) {
            throw new IllegalArgumentException("Models, customer, and credit card cannot be null.");
        }

        // 2. card validity
        if (!creditCard.isValid()) {
            throw new IllegalArgumentException("Cannot place order with an invalid or expired credit card.");
        }

        // 3. If everything is valid, create the order
        Order newOrder = new Order(creditCard,models,customer);

        this.orderHistory.add(newOrder);

        return newOrder;
    }

    @Override
    public void cancelOrder(Order order) {
        if(order == null){
            throw new IllegalArgumentException("Order to be cancelled cannot be null.");
        }
        order.cancel();
    }

    @Override
    public FulfillmentDetails fulfillOrder(Order order) {
        if (order == null){
            throw new IllegalArgumentException("Order to be fulfillment cannot be null.");
        }

        order.fulfill(); // will throw error if order is not PLACED

        Map<String, Map<String,Integer>> presetOrders = new HashMap<>();
        Map<String,Integer> warehouseParts = new HashMap<>();

        for (PCModel model : order.getModels()) {
            if(model instanceof PresetModel presetModel){
                String manufacturer = presetModel.getManufacturer();
                String modelName = presetModel.getName();

                // Get map of models for this manufacturer
                Map<String,Integer> modelsForManufacturer = presetOrders.get(manufacturer);

                // If we have never seen this manufacturer, create an entry
                if(modelsForManufacturer == null){
                    modelsForManufacturer = new HashMap<>();
                    presetOrders.put(manufacturer, modelsForManufacturer);
                }

                // Get the count for this specific model from this manufacturer
                Integer currentModelCount = modelsForManufacturer.get(modelName);

                // If we never seent this model, set its count to 1

                if(currentModelCount == null){
                    modelsForManufacturer.put(modelName, 1);
                }
                // else increment counter
                else {
                    modelsForManufacturer.put(modelName, currentModelCount + 1);
                }
            }
            else if(model instanceof CustomModel customModel){
                // Custom Model contains list of parts
                // Loop through the list, get unique parts
                for (String part : customModel.getParts()){
                    // Get Current count for this part
                    Integer currentPartCount = warehouseParts.get(part);

                    // If we have never seen this part, set count to 1
                    if(currentPartCount == null){
                        warehouseParts.put(part,1);
                    }
                    // else increment the counter
                    else {
                        warehouseParts.put(part,currentPartCount+1);
                    }

                }



            }
        }
        return new FulfillmentDetails(presetOrders,warehouseParts);
    }
}
