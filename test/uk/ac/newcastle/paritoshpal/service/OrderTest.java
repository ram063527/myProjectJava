package uk.ac.newcastle.paritoshpal.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.ac.newcastle.paritoshpal.model.customer.Customer;
import uk.ac.newcastle.paritoshpal.model.customer.CustomerFactory;
import uk.ac.newcastle.paritoshpal.model.payment.CreditCard;
import uk.ac.newcastle.paritoshpal.model.payment.CreditCardFactory;
import uk.ac.newcastle.paritoshpal.model.pc.CustomModel;
import uk.ac.newcastle.paritoshpal.model.pc.CustomModelFactory;
import uk.ac.newcastle.paritoshpal.model.pc.PCModel;
import uk.ac.newcastle.paritoshpal.model.pc.PresetModel;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Order Class Unit Tests")
class OrderTest {

    private Customer customer;
    private CreditCard creditCard;
    private List<PCModel> models;

    @BeforeEach
    void setUp() {
        // Valid customer
        customer = CustomerFactory.getCustomer("Paritosh", "Pal");
        // valid credit card
        Calendar cal = Calendar.getInstance();
        cal.set(2032, Calendar.JANUARY, 25);
        Date futureDate = cal.getTime();
        creditCard = CreditCardFactory.getCreditCard("12345678", futureDate, "Paritosh Pal");
        // list of models
        models = new ArrayList<>();
        // Add a Preset Model
        PresetModel presetModel = new PresetModel("Inspiron", "Dell", Arrays.asList("CPU: Intel i5", "RAM: 16GB"));
        models.add(presetModel);
        // Add a Custom Model
        CustomModel customModel = CustomModelFactory.createCustomModel();
        customModel.addPart("CPU: AMD Ryzen 9");
        customModel.addPart("GPU: Nvidia RTX 4090");
        models.add(customModel);
    }

    @Nested
    @DisplayName("Order creation tests")
    class OrderCreationTests {

        @Test
        @DisplayName("Test valid order creation")
        void testValidCreation() {
            Order order = new Order(creditCard, models, customer);

            assertNotNull(order);
            assertEquals(customer, order.getCustomer());
            assertEquals(models, order.getModels());
            assertEquals(models, order.getModels());
            assertEquals(OrderStatus.PLACED, order.getOrderStatus());
            assertNotNull(order.getOrderDate());
        }


        @Test
        @DisplayName("Test invalid order creation")
        void testInvalidCreation() {
            // Null credit card
            assertThrowsExactly(IllegalArgumentException.class, () -> {
                Order order = new Order(null, models, customer);
            });
            //null models
            assertThrowsExactly(IllegalArgumentException.class, () -> {
                Order order = new Order(creditCard, null, customer);
            });
            // empty models
            assertThrowsExactly(IllegalArgumentException.class, () -> {
                Order order = new Order(creditCard, new ArrayList<>(), customer);
            });
            // null customer
            assertThrowsExactly(IllegalArgumentException.class, () -> {
                Order order = new Order(creditCard, models, null);
            });

        }
    }

    @Nested
    @DisplayName("Order status transitions")
    class StateTransitionTests {
        private Order order;

        @BeforeEach
        void setUp() {
            order = new Order(creditCard, models, customer);
        }

        @Test
        @DisplayName("Test Cancel PLACED order")
        void testCancelPlacedOrder(){
            order.cancel();
            assertEquals(OrderStatus.CANCELLED, order.getOrderStatus());
        }

        @Test
        @DisplayName("Test Fulfill PLACED order")
        void testFulfillPlacedOrder(){
            order.fulfill();
            assertEquals(OrderStatus.FULFILLED, order.getOrderStatus());
        }

        @Test
        @DisplayName("Test Cancel FULFILLED order")
        void testCancelFulfilledOrder(){
            order.fulfill();

            assertThrowsExactly(IllegalStateException.class, () -> {
                order.cancel();
            });
        }

        @Test
        @DisplayName("Test fulfill CANCELLED order")
        void testFulfillCancelledOrder(){
            order.cancel();

            assertThrowsExactly(IllegalStateException.class, () -> {
                order.fulfill();
            });

        }


    }

    @Nested
    @DisplayName("Getter methods tests")
    class GetterMethodsTests{

        @Test
        @DisplayName("Test getCustomer()")
        void testGetCustomer(){
            Order order = new Order(creditCard, models, customer);
            assertEquals(customer,order.getCustomer());
        }

        @Test
        @DisplayName("Test getModels()")
        void testGetModels(){
            Order order = new Order(creditCard, models, customer);
            assertEquals(models,order.getModels());
        }

        @Test
        @DisplayName("Test getOrderDate()")
        void testGetOrderDate(){
            Order order = new Order(creditCard, models, customer);
            assertEquals(order.getOrderDate(),order.getOrderDate());
        }
        @Test
        @DisplayName("Test getOrderStatus()")
        void testGetOrderStatus(){
            Order order = new Order(creditCard, models, customer);
            assertEquals(OrderStatus.PLACED,order.getOrderStatus());
            order.fulfill();
            assertEquals(OrderStatus.FULFILLED,order.getOrderStatus());
        }

        @Test
        @DisplayName("Test getCreditCard()")
        void getCreditCard(){
            Order order = new Order(creditCard, models, customer);
            assertEquals(creditCard,order.getCreditCard());
        }

    }


}