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
import uk.ac.newcastle.paritoshpal.model.pc.PresetModel;
import uk.ac.newcastle.paritoshpal.service.Order;
import uk.ac.newcastle.paritoshpal.service.PCShopImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@DisplayName("PCShopImpl Unit tests")
class PCShopImplTest {

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
        cardB = CreditCardFactory.getCreditCard("222222222",futureDate,"Clay");
        invalidCard =  CreditCardFactory.getCreditCard("99999999",pastDate,"Bryce");

        // PC Models

        preset1 = new PresetModel("inspiron","dell", List.of("i9", "RTX4090"));
        preset2 = new PresetModel("yoga","lenovo",List.of("i5", "Integrated"));
        preset3 = new PresetModel("air-m4","Apple",List.of("M4","OLED Screen","16 GB RAM"));

        custom1 = CustomModelFactory.createCustomModel();
        custom2 = CustomModelFactory.createCustomModel();

        custom1.addPart("Case");
        custom1.addPart("RAM 16 GB");
        custom1.addPart("RAM 16 GB");
        custom1.addPart("RAM 16 GB");
        custom1.addPart("Intel Core Ultra 9");


        custom2.addPart("Case");
        custom2.addPart("RAM 32 GB");
        custom2.addPart("RAM 16 GB");
        custom2.addPart("GPU 16 GB");
        custom2.addPart("Cooling fan");
        custom2.addPart("Intel Core Ultra 9");

    }

    @Nested
    @DisplayName("Test Order lifecycle")
    class OrderLifeCycleTests{

        @Test
        @DisplayName("Test placeOrder()")
        void testPlaceOrder(){

        }
    }

    private Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal.getTime();

    }

}