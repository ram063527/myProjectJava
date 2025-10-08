package main.java.uk.ac.newcastle.paritoshpal.model.fulfillment;

import java.util.Map;
import java.util.Objects;

/**
 * An immutable data class that holds the aggregated details required to
 * fulfill an order.
 * The object is returned by the {@code fullfillOrder} method and contains
 * two main pieces of information: a breakdown of preset models to be ordered
 * from manufacturers, list of individual parts to be collected from the warehouse
 * for custom models.
 */
public final class FulfillmentDetails {

    private final Map<String, Map<String,Integer>> presetOrders;
    private final Map<String,Integer> warehouseParts;

    /**
     * Constructs a new FulfillmentDetails instance.
     * @param presetOrders A map where the key is the manufacturer's name and the value
     * is another map, holding preset model names and their required quantities.
     * @param warehouseParts A map where the key is the name of a part for a custom
     * model and the value is its required quantity.
     */
    public FulfillmentDetails(Map<String, Map<String, Integer>> presetOrders, Map<String, Integer> warehouseParts) {
        this.presetOrders = Map.copyOf(presetOrders);
        this.warehouseParts = Map.copyOf(warehouseParts);
    }

    /**
     * Returns a string representation of the fulfillment details.
     * @return a formatted string showing the preset orders and warehouse parts.
     */
    @Override
    public String toString() {
        return "FulfillmentDetails{" +
                "presetOrders=" + presetOrders +
                ", warehouseParts=" + warehouseParts +
                '}';
    }

    /**
     * Compares this Fulfillment object to another for equality
     * @param o the object to compare against.
     * @return {@code true} if both objects have identical preset order maps
     * and warehouse part maps; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FulfillmentDetails that = (FulfillmentDetails) o;
        return Objects.equals(presetOrders, that.presetOrders) &&
                Objects.equals(warehouseParts, that.warehouseParts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(presetOrders, warehouseParts);
    }

}
