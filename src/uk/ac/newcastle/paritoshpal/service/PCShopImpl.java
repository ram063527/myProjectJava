package uk.ac.newcastle.paritoshpal.service;



import uk.ac.newcastle.paritoshpal.dto.CustomerStats;
import uk.ac.newcastle.paritoshpal.dto.ModelStats;
import uk.ac.newcastle.paritoshpal.dto.PartsStats;
import uk.ac.newcastle.paritoshpal.model.customer.Customer;
import uk.ac.newcastle.paritoshpal.model.fulfillment.FulfillmentDetails;
import uk.ac.newcastle.paritoshpal.model.payment.CreditCard;
import uk.ac.newcastle.paritoshpal.model.pc.CustomModel;
import uk.ac.newcastle.paritoshpal.model.pc.PCModel;
import uk.ac.newcastle.paritoshpal.model.pc.PresetModel;

import java.util.*;
import java.util.stream.Collectors;

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

        // Get all the orders
        Map<Customer,Long> customerOrderCounts = this.orderHistory.stream()
                // get only the fulfilled orders
                .filter(order -> order.getOrderStatus()==OrderStatus.FULFILLED)
                // group by customer and count
                .collect(Collectors.groupingBy(Order::getCustomer,Collectors.counting()));

        if(customerOrderCounts.isEmpty()){
            return null;
        }
        // Define a reversed comparator for the key (the Customer)
        Comparator<Customer> customerNameComparator = Comparator
                .comparing(Customer::getFirstName)
                .thenComparing(Customer::getLastName)
                .reversed();

        // get the customer order counts
        return customerOrderCounts.entrySet().stream()
                // first of all compare by the order count i.e values
                .max(Map.Entry.<Customer,Long>comparingByValue()
                        .thenComparing(Map.Entry::getKey,customerNameComparator)
                ).map(entry -> new CustomerStats(entry.getKey(),entry.getValue().intValue()))
                .orElse(null);
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public ModelStats getMostOrderedModel() {
       // Get all the models
        Map<PresetModel,Long> presetModelCounts = this.orderHistory.stream()
                // filter out only fulfilled
                .filter(order-> order.getOrderStatus()==OrderStatus.FULFILLED)
                // open the List<PCModel> and make a stream
                .flatMap(order -> order.getModels().stream())
                // filter out only Preset Models
                .filter(model -> model instanceof PresetModel)
                // cast the result to PC Model Explicitly -- Safe because we already filtered out Preset Models
                .map(model -> (PresetModel)model)
                // Group by PresetModel and count
                .collect(Collectors.groupingBy(model-> model,Collectors.counting()));

        if(presetModelCounts.isEmpty()){
            return null;
        }

        // Define a reversed comparator for the PresetModel
        Comparator<PresetModel> modelNameComparator = Comparator
                .comparing(PresetModel::getManufacturer)
                .thenComparing(PresetModel::getName)
                .reversed();

        return presetModelCounts.entrySet().stream()
                // Firstly compare by values
                .max(Map.Entry.<PresetModel,Long>comparingByValue()
                        .thenComparing(Map.Entry::getKey,modelNameComparator))
                // We got the winning enty's key and value ; put it into ModelStats
                .map(entry-> new ModelStats(entry.getKey(),entry.getValue().intValue()))
                .orElse(null);

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public PartsStats getMostOrderedPart() {

        Map<String,Long> partsCounts = this.orderHistory.stream()
                .filter(order->order.getOrderStatus()==OrderStatus.FULFILLED)
                .flatMap(order-> order.getModels().stream())
                .filter(model -> model instanceof CustomModel)
                .map(model -> (CustomModel) model)
                .flatMap(model -> model.getParts().stream())
                .collect(Collectors.groupingBy(part-> part,Collectors.counting()));

        if(partsCounts.isEmpty()){
            return null;
        }

        return partsCounts.entrySet().stream()
                .max(Map.Entry.<String,Long>comparingByValue()
                        .thenComparing(Map.Entry::getKey,Comparator.reverseOrder()))
                .map(entry -> new PartsStats(entry.getKey(),entry.getValue().intValue()))
                .orElse(null);
    }

}
