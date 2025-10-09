package uk.ac.newcastle.paritoshpal.service;



import uk.ac.newcastle.paritoshpal.dto.CustomerStats;
import uk.ac.newcastle.paritoshpal.dto.ModelStats;
import uk.ac.newcastle.paritoshpal.dto.PartsStats;
import uk.ac.newcastle.paritoshpal.model.customer.Customer;
import uk.ac.newcastle.paritoshpal.model.fulfillment.FulfillmentDetails;
import uk.ac.newcastle.paritoshpal.model.order.Order;
import uk.ac.newcastle.paritoshpal.model.order.OrderStatus;
import uk.ac.newcastle.paritoshpal.model.payment.CreditCard;
import uk.ac.newcastle.paritoshpal.model.pc.CustomModel;
import uk.ac.newcastle.paritoshpal.model.pc.PCModel;
import uk.ac.newcastle.paritoshpal.model.pc.PresetModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The concrete implementation of the {@link PCShop} service interface.
 * This class manages the history of all orders and provides the business logic
 * for placing, cancelling, and fulfilling orders, as well as for generating
 * analytics about the order history.
 */
public final class PCShopImpl implements PCShop {

    private final List<Order> orderHistory = new ArrayList<>();

    /**
     * {@inheritDoc}
     */

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

    /**
     * {@inheritDoc}
     */

    @Override
    public void cancelOrder(Order order) {
        if(order == null){
            throw new IllegalArgumentException("Order to be cancelled cannot be null.");
        }
        order.cancel();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public FulfillmentDetails fulfillOrder(Order order) {
        if (order == null){
            throw new IllegalArgumentException("Order to be fulfillment cannot be null.");
        }

        order.fulfill(); // will throw error if order is not PLACED

        Map<String, Map<String,Integer>> presetOrders = new HashMap<>();
        Map<String,Integer> warehouseParts = new HashMap<>();

        // Get all the models
        for (PCModel model : order.getModels()) {
            // Get only preset model
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

                // If we have never seen this model, set its count to 1
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

    /**
     * {@inheritDoc}
     */

    @Override
    public CustomerStats getLargestCustomer() {
        // create a map of customer integer

        Map<Customer, Integer> customerOrderCounts = getCustomerOrderCounts();

        if(customerOrderCounts.isEmpty()){
            return null;
        }

        Customer largestCustomer = null;
        int maxOrders = -1;

        for(Map.Entry<Customer,Integer> entry: customerOrderCounts.entrySet()){
            Customer currentCustomer = entry.getKey();
            int currentOrderCount = entry.getValue();

            if(largestCustomer==null || currentOrderCount > maxOrders){
                maxOrders = currentOrderCount;
                largestCustomer = currentCustomer;
            }
            else if(currentOrderCount == maxOrders){
                if(currentCustomer.toString().compareTo(largestCustomer.toString()) < 0){
                    largestCustomer = currentCustomer;
                }
            }
        }
        return new CustomerStats(largestCustomer,maxOrders);
    }

    private Map<Customer, Integer> getCustomerOrderCounts() {
        Map<Customer,Integer> customerOrderCounts = new HashMap<>();

        // Get all the fulfilled orders
        for(Order order: this.orderHistory){
            if(order.getOrderStatus() == OrderStatus.FULFILLED){
                // get customer
                Customer customer = order.getCustomer();
                // check if we have encountered this customer before
                // Yes increment
                if(customerOrderCounts.containsKey(customer)){
                    customerOrderCounts.put(customer, customerOrderCounts.get(customer) + 1);
                }
                // No add
                else {
                    customerOrderCounts.put(customer,1);
                }
            }
        }
        return customerOrderCounts;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public ModelStats getMostOrderedModel() {
        Map<PresetModel,Integer> presetModelOrderCounts = new HashMap<>();
        for (Order order : this.orderHistory) {
            if(order.getOrderStatus() == OrderStatus.FULFILLED){
                for(PCModel model : order.getModels()){
                    if(model instanceof PresetModel presetModel){
                        if(presetModelOrderCounts.containsKey(presetModel)){
                            presetModelOrderCounts.put(presetModel, presetModelOrderCounts.get(presetModel) + 1);
                        }
                        else  {
                            presetModelOrderCounts.put(presetModel, 1);
                        }
                    }
                }
            }
        }
        if(presetModelOrderCounts.isEmpty()){
            return null;
        }
        PresetModel highestOrdered = null;
        int maxOrders = -1;

        for(Map.Entry<PresetModel,Integer> entry: presetModelOrderCounts.entrySet()){
            PresetModel presetModel = entry.getKey();
            Integer currentOrderCount = entry.getValue();

            if(highestOrdered==null || currentOrderCount > maxOrders){
                highestOrdered = presetModel;
                maxOrders = currentOrderCount;
            }
            else if(currentOrderCount == maxOrders){
                int manufacturerComparison = presetModel.getManufacturer().compareTo(highestOrdered.getManufacturer());

                if(manufacturerComparison < 0){
                    highestOrdered = presetModel;
                }
                else if(manufacturerComparison == 0){
                    // same manufacturer
                    if(presetModel.getName().compareTo(highestOrdered.getName()) < 0){
                        highestOrdered = presetModel;
                    }
                }
            }
        }
        return new ModelStats(highestOrdered,maxOrders);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public PartsStats getMostOrderedPart() {
        Map<String, Integer> partsOrderCounts = getPartsOrderCounts();
        if(partsOrderCounts.isEmpty()){
            return null;
        }
        String highestOrderedPart = null;
        int maxOrders = -1;
        for(Map.Entry<String,Integer> entry: partsOrderCounts.entrySet()){
            String part = entry.getKey();
            Integer currentOrderCount = entry.getValue();

            if(highestOrderedPart==null || currentOrderCount > maxOrders){
                highestOrderedPart = part;
                maxOrders = currentOrderCount;
            }
            else if(currentOrderCount == maxOrders){
                if(part.compareTo(highestOrderedPart) < 0){
                    highestOrderedPart = part;
                }
            }
        }
        return new PartsStats(highestOrderedPart,maxOrders);
    }

    private Map<String, Integer> getPartsOrderCounts() {
        Map<String,Integer> partsOrderCounts = new HashMap<>();
        for(Order order: this.orderHistory){
            if(order.getOrderStatus() == OrderStatus.FULFILLED){
                for(PCModel model : order.getModels()){
                    if(model instanceof CustomModel customModel){
                        for (String part : model.getParts()) {
                            if(partsOrderCounts.containsKey(part)){
                                partsOrderCounts.put(part, partsOrderCounts.get(part) + 1);
                            }
                            else  {
                                partsOrderCounts.put(part, 1);
                            }
                        }
                    }
                }
            }
        }
        return partsOrderCounts;
    }
}
