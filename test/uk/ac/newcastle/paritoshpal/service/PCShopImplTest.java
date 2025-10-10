package uk.ac.newcastle.paritoshpal.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.ac.newcastle.paritoshpal.dto.CustomerStats;
import uk.ac.newcastle.paritoshpal.dto.ModelStats;
import uk.ac.newcastle.paritoshpal.dto.PartsStats;
import uk.ac.newcastle.paritoshpal.model.customer.Customer;
import uk.ac.newcastle.paritoshpal.model.customer.CustomerFactory;
import uk.ac.newcastle.paritoshpal.model.fulfillment.FulfillmentDetails;
import uk.ac.newcastle.paritoshpal.model.payment.CreditCard;
import uk.ac.newcastle.paritoshpal.model.payment.CreditCardFactory;
import uk.ac.newcastle.paritoshpal.model.pc.CustomModel;
import uk.ac.newcastle.paritoshpal.model.pc.CustomModelFactory;
import uk.ac.newcastle.paritoshpal.model.pc.PCModel;
import uk.ac.newcastle.paritoshpal.model.pc.PresetModel;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@DisplayName("PCShopImpl Unit tests")
class PCShopImplTest {

    private Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day,0,0,0);
        return cal.getTime();
    }

    private PCShopImpl shop;
    private Customer custA, custB, custC, custD;
    private CreditCard cardA, cardB, invalidCard;
    private PresetModel preset1,preset2,preset3;
    private CustomModel custom1,custom2;

    @BeforeEach
    void setUp() {
        shop = new PCShopImpl();

        //Customer
        custA = CustomerFactory.getCustomer("Hannah","Baker");
        custB = CustomerFactory.getCustomer("Clay","Jensen");
        custC = CustomerFactory.getCustomer("Bryce","Walker");
        custD = CustomerFactory.getCustomer("Jessica","Davis");

        // CreditCard
        Date futureDate = createDate(2032,Calendar.OCTOBER,22);
        Date pastDate = createDate(2022,Calendar.OCTOBER,22);

        cardA = CreditCardFactory.getCreditCard("11111111",futureDate,"Hannah");
        // FIX: Card number must be 8 digits.
        cardB = CreditCardFactory.getCreditCard("22222222",futureDate,"Clay");
        invalidCard =  CreditCardFactory.getCreditCard("99999999",pastDate,"Bryce");

        // PC Models
        preset1 = new PresetModel("inspiron","dell", List.of("i9", "RTX4090"));
        preset2 = new PresetModel("yoga","lenovo",List.of("i5", "Integrated"));
        preset3 = new PresetModel("air-m4","Apple",List.of("M4","OLED Screen","16 GB RAM"));

        custom1 = CustomModelFactory.createCustomModel();
        custom2 = CustomModelFactory.createCustomModel();

        // Parts for custom1
        custom1.addPart("Case");
        custom1.addPart("RAM 16 GB");
        custom1.addPart("RAM 16 GB");
        custom1.addPart("Intel Core Ultra 9");


        // Parts for custom2
        custom2.addPart("Case");
        custom2.addPart("RAM 32 GB");
        custom2.addPart("PSU");
        custom2.addPart("Cooling fan");
        custom2.addPart("Intel Core Ultra 9");
    }

    @Nested
    @DisplayName("Test Order lifecycle")
    class OrderLifeCycleTests{

        @Test
        @DisplayName("Test placeOrder()")
        void testPlaceOrder(){
            Order order = shop.placeOrder(List.of(preset1),custA,cardA);
            assertNotNull(order);
            assertEquals(OrderStatus.PLACED,order.getOrderStatus());
        }

        @Test
        @DisplayName("Test placeOrder() invalid card")
        void testPlaceOrderWithInvalidCard(){
            assertThrowsExactly(IllegalArgumentException.class,()->{
                shop.placeOrder(List.of(preset1),custA,invalidCard);
            });
        }

        @Test
        @DisplayName("Test cancelOrder")
        void testCancelOrder(){
            Order order = shop.placeOrder(List.of(preset1),custA,cardA);
            shop.cancelOrder(order);
            assertEquals(OrderStatus.CANCELLED,order.getOrderStatus());
        }

        @Test
        @DisplayName("Test fulfill() order")
        void testFulfillOrder(){
            List<PCModel> models = List.of(preset1,preset1, preset2, preset3, custom1);
            Order  order = shop.placeOrder(models,custA,cardA);

            FulfillmentDetails details = shop.fulfillOrder(order);

            // Verify preset orders
            assertEquals(3, details.getPresetOrders().size());
            assertEquals(2, details.getPresetOrders().get("dell").get("inspiron"));
            assertEquals(1, details.getPresetOrders().get("lenovo").get("yoga"));
            assertEquals(1, details.getPresetOrders().get("apple").get("air-m4"));


            // verify warehouse parts
            assertEquals(3,details.getWarehouseParts().size());
            assertEquals(2,details.getWarehouseParts().get("RAM 16 GB"));
            assertEquals(1,details.getWarehouseParts().get("Intel Core Ultra 9"));
            assertEquals(1,details.getWarehouseParts().get("Case"));
            // verify status change
            assertEquals(OrderStatus.FULFILLED,order.getOrderStatus());
        }
    }

    @Nested
    @DisplayName("Analytics Tests")
    class AnalyticsTests{

        @Test
        @DisplayName("test getLargestCustomer()")
        void testGetLargestCustomer(){
            shop.fulfillOrder(shop.placeOrder(List.of(preset1), custA, cardA));
            shop.fulfillOrder(shop.placeOrder(List.of(preset2), custA, cardA));
            shop.fulfillOrder(shop.placeOrder(List.of(preset3), custB, cardB));
            // Unfulfilled order for custB, should be ignored
            shop.placeOrder(List.of(preset1), custB, cardB);

            CustomerStats stats = shop.getLargestCustomer();
            assertEquals(custA, stats.customer());
            assertEquals(2, stats.orderCount());
        }

        @Test
        @DisplayName("Test getLargestCustomer() tieBreak")
        void testGetLargestCustomerTieBreak() {
            // custB ("Clay Jensen") and custC ("Bryce Walker") both have 2 orders.
            // "bryce - walker" comes before "clay - jensen" alphabetically.
            shop.fulfillOrder(shop.placeOrder(List.of(preset1), custB, cardB));
            shop.fulfillOrder(shop.placeOrder(List.of(preset1), custB, cardB));
            shop.fulfillOrder(shop.placeOrder(List.of(preset2), custC, cardB));
            shop.fulfillOrder(shop.placeOrder(List.of(preset2), custC, cardB));
            shop.fulfillOrder(shop.placeOrder(List.of(preset3), custA, cardA));

            CustomerStats stats = shop.getLargestCustomer();
            // FIX: Correct winner is custC based on alphabetical order of name.
            assertEquals(custC, stats.customer());
            assertEquals(2, stats.orderCount());
        }

        @Test
        @DisplayName("Test getMostOrderedModel()")
        void testGetMostOrderedModel() {
            // preset1 ordered 3 times, preset2 once
            shop.fulfillOrder(shop.placeOrder(List.of(preset1, preset1, preset2, preset1), custA, cardA));

            ModelStats stats = shop.getMostOrderedModel();
            assertEquals(preset1, stats.model());
            assertEquals(3, stats.modelCount());
        }

        @Test
        @DisplayName("Test getMostOrderedModel() tiebreak")
        void testGetMostOrderedModelTieBreak() {
            // preset1 (dell) and preset3 (Apple) both ordered twice.
            // Tie-break is by manufacturer first. "apple" comes before "dell".
            shop.fulfillOrder(shop.placeOrder(List.of(preset1, preset1), custA, cardA));
            shop.fulfillOrder(shop.placeOrder(List.of(preset3, preset3), custB, cardB));

            ModelStats stats = shop.getMostOrderedModel();
            // FIX: Correct winner is preset3 because "Apple" comes before "Dell".
            assertEquals(preset3, stats.model(), "Apple should win due to alphabetical tie-break on manufacturer");
            assertEquals(2, stats.modelCount());
        }
        @Test
        @DisplayName("Test getMostOrderedParts()")
        void testGetMostOrderedPart() {
            // Order contains custom1 and custom2.
            // "RAM 16 GB" appears twice in custom1. "Intel Core Ultra 9" appears once in each.
            // Total: "RAM 16 GB" x2, "Intel Core Ultra 9" x2, "Case" x2
            shop.fulfillOrder(shop.placeOrder(List.of(custom1), custA, cardA));

            PartsStats stats = shop.getMostOrderedPart();
            assertEquals("RAM 16 GB", stats.parts());
            assertEquals(2, stats.partCount());
        }

        @Test
        @DisplayName("Test getMostOrderedPart() tiebreak")
        void testGetMostOrderedPartTieBreak() {
            // Both "Case" and "Intel Core Ultra 9" will appear twice. "Case" comes first alphabetically.
            shop.fulfillOrder(shop.placeOrder(List.of(custom1, custom2), custA, cardA));

            PartsStats stats = shop.getMostOrderedPart();
            assertEquals("Case", stats.parts(), "'Case' should win due to alphabetical tie-break");
            assertEquals(2, stats.partCount());
        }

        @Test
        @DisplayName("Test analytics for non fulfilled orders")
        void testAnalyticsReturnNullForNoOrders() {
            // Place an order but don't fulfill it
            shop.placeOrder(List.of(preset1), custA, cardA);

            assertNull(shop.getLargestCustomer());
            assertNull(shop.getMostOrderedModel());
            assertNull(shop.getMostOrderedPart());
        }
    }




}