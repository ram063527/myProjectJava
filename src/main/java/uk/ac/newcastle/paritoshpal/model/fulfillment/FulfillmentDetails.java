package main.java.uk.ac.newcastle.paritoshpal.model.fulfillment;

import java.util.Map;
import java.util.Objects;

public final class FulfillmentDetails {

    private final Map<String, Map<String,Integer>> presetOrders;
    private final Map<String,Integer> warehouseParts;

    public FulfillmentDetails(Map<String, Map<String, Integer>> presetOrders, Map<String, Integer> warehouseParts) {
        this.presetOrders = presetOrders;
        this.warehouseParts = warehouseParts;
    }

    @Override
    public String toString() {
        return "FulfillmentDetails{" +
                "presetOrders=" + presetOrders +
                ", warehouseParts=" + warehouseParts +
                '}';
    }

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
