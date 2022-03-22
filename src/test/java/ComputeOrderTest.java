import com.mss.supermarket.business.ComputeOrderService;
import com.mss.supermarket.utils.EnumPricingSchemeType;
import com.mss.supermarket.model.Order;
import com.mss.supermarket.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ComputeOrderTest extends AbstractSupermarketData {
    final static Float POUND_TO_OUNCE_RATE = 1F/16F;

    Order ord;
    Map<Product, Object> currPrdDetails;
    ComputeOrderService computeOrderService ;


    @BeforeEach
    public void initializeContext() {
        ord = new Order();
        currPrdDetails = new HashMap<>();
        computeOrderService = new ComputeOrderService();
    }
    /* Price of 02 with the default pricing : price*qty */
    @Test
    public void testComputeOrderPrice_DefaultPricing() {

        // The price should be the sum of unit price for each item 7*$999
        Product computer = getProduct("Computer", "999", null);
        currPrdDetails.put(computer, 2);
        ord.setProductDetails(currPrdDetails);

        computeOrderService.computeOrderPrice( Arrays.asList( new Order[]{ord}) );
        Assertions.assertEquals(BigDecimal.valueOf(2 * 999), ord.getTotalOrderPrice() );
    }

    /* Adding the price to add of 03 items with the scheme BUY_TWO_GET_ONE_FREE */
    @Test
    public void testComputeOrderPrice_WithSchemeB2G1() {
        // The price  should be equal to 2*$2 for the 3 cups
        Product cup = getProduct("Cup", "2", EnumPricingSchemeType.BUY_TWO_GET_ONE_FREE);
        currPrdDetails.put(cup, 3);
        ord.setProductDetails(currPrdDetails);

        computeOrderService.computeOrderPrice( Arrays.asList( new Order[]{ord}) );
        Assertions.assertEquals(BigDecimal.valueOf(2 * 2), ord.getTotalOrderPrice());
    }
    @Test
    public void testComputeOrderPrice_WithSchemeB2G1_1() {
        // The price should be equal to 4*$2 for the 6 cups
        Product cup = getProduct("Cup", "2", EnumPricingSchemeType.BUY_TWO_GET_ONE_FREE);
        currPrdDetails.put(cup, 6);
        ord.setProductDetails(currPrdDetails);

        computeOrderService.computeOrderPrice( Arrays.asList( new Order[]{ord}) );
        Assertions.assertEquals(BigDecimal.valueOf(4 * 2), ord.getTotalOrderPrice());
    }
    @Test
    public void testComputeOrderPrice_WithSchemeB2G1_2() {
        // The price  should be equal to 5*$2 for the 7 cups
        Product cup = getProduct("Cup", "2", EnumPricingSchemeType.BUY_TWO_GET_ONE_FREE);
        currPrdDetails.put(cup, 7);
        ord.setProductDetails(currPrdDetails);

        computeOrderService.computeOrderPrice( Arrays.asList( new Order[]{ord}) );
        Assertions.assertEquals(BigDecimal.valueOf(5 * 2), ord.getTotalOrderPrice());
    }

    /*Adding the price for 04 items with the scheme THREE_FOR_ONE_DOLLAR */
    @Test
    public void testComputeOrderPrice_WithScheme3for1$() {
        // The price should be equal to  plus 4/3 == $1 plus the normal price of 4%3=1 more item = $1 + $0.5
        Product chocolateBar = getProduct("Chocolate bar", "0.5", EnumPricingSchemeType.THREE_FOR_ONE_DOLLAR);
        currPrdDetails.put(chocolateBar, 4);
        ord.setProductDetails(currPrdDetails);

        computeOrderService.computeOrderPrice( Arrays.asList( new Order[]{ord}) );
        Assertions.assertEquals(
                BigDecimal.valueOf(1 + 0.5).setScale(4, RoundingMode.HALF_UP),
                ord.getTotalOrderPrice()
        );
    }
    /*Adding the price for 08 items with the scheme THREE_FOR_ONE_DOLLAR */
    @Test
    public void testComputeOrderPrice_WithScheme3for1$_1() {
        // The price should be equal to 8/3==2 == $2 plus the normal price of 8%3=2 more item = $2 + $0.5*2
        Product chocolateBar = getProduct("Chocolate bar", "0.5", EnumPricingSchemeType.THREE_FOR_ONE_DOLLAR);
        currPrdDetails.put(chocolateBar, 8);
        ord.setProductDetails(currPrdDetails);

        computeOrderService.computeOrderPrice( Arrays.asList( new Order[]{ord}) );
        Assertions.assertEquals(
                BigDecimal.valueOf(3).setScale(4, RoundingMode.HALF_UP),
                ord.getTotalOrderPrice()
        );
    }

    /*Adding the price for 08 items with the scheme THREE_FOR_ONE_DOLLAR */
    @Test
    public void testComputeOrderPrice_WithScheme3for1$_2() {
        // The price should be equal to 9/3==3 == $3
        Product chocolateBar = getProduct("Chocolate bar", "0.5", EnumPricingSchemeType.THREE_FOR_ONE_DOLLAR);
        currPrdDetails.put(chocolateBar, 9);
        ord.setProductDetails(currPrdDetails);

        computeOrderService.computeOrderPrice( Arrays.asList( new Order[]{ord}) );
        Assertions.assertEquals(
                BigDecimal.valueOf(3).setScale(4, RoundingMode.HALF_UP),
                ord.getTotalOrderPrice()
        );
    }

    /* Adding the price of 04 ounces of Beans with the scheme FIXED_PRICE_PER_POUND */
    @Test
    public void testComputeOrderPrice_WithSchemeFixPPP() {
        // The amount of pounds in 4 ounces is 4*1/16= 0.25 pound, hence the price to add should be equal to $1.99*0.25
        Product flour = getProduct("Flour", "0.65", EnumPricingSchemeType.FIXED_PRICE_PER_POUND);
        currPrdDetails.put(flour, 4 * POUND_TO_OUNCE_RATE);
        ord.setProductDetails(currPrdDetails);

        computeOrderService.computeOrderPrice( Arrays.asList( new Order[]{ord}) );
        Assertions.assertEquals(
                BigDecimal.valueOf(1.99*0.25).setScale(4, RoundingMode.HALF_UP),
                ord.getTotalOrderPrice()
        );
    }

    /* Adding the price of 65 ounces of Beans with the scheme FIXED_PRICE_PER_POUND */
    @Test
    public void testComputeOrderPrice_WithSchemeFixPPP_1() {
        // The amount of pounds in 65 ounces is 65*1/16= 4.0625 pounds, hence the price to add should be equal to $1.99*4.0625
        Product Flour = getProduct("Flour", "0.65", EnumPricingSchemeType.FIXED_PRICE_PER_POUND);
        currPrdDetails.put(Flour, 65 * POUND_TO_OUNCE_RATE);
        ord.setProductDetails(currPrdDetails);

        computeOrderService.computeOrderPrice( Arrays.asList( new Order[]{ord}) );
        Assertions.assertEquals(
                BigDecimal.valueOf(1.99*4.0625).setScale(4, RoundingMode.HALF_UP),
                ord.getTotalOrderPrice()
        );

    }

    /* Adding the price of many products with various pricing schemes */
    @Test
    public void testComputeOrderPrice_WithAllSchemes() {
        BigDecimal expectedPrice;

        // Order for product based on Int quantities
        Product cup = getProduct("Cup", "2", EnumPricingSchemeType.BUY_TWO_GET_ONE_FREE);
        currPrdDetails.put(cup, 7);
        expectedPrice = BigDecimal.valueOf(5 * 2)       // The price  should be equal to 5*$2 for the 7 cups
                .setScale(4, RoundingMode.HALF_UP);

        Product chocolateBar = getProduct("Chocolate bar", "0.5", EnumPricingSchemeType.THREE_FOR_ONE_DOLLAR);
        currPrdDetails.put(chocolateBar, 8);
        expectedPrice = expectedPrice.add(  //  The price should be equal to 8/3 = $2 plus the normal price of 8%3=2 more item = $2 + $0.5*2 = $3
                BigDecimal.valueOf(3).setScale(4, RoundingMode.HALF_UP)
        );

        Order ord1 = new Order();
        ord1.setProductDetails(new HashMap<>(currPrdDetails));

        // Order for product based on float quantities
        Product flour = getProduct("Flour", "0.65", EnumPricingSchemeType.FIXED_PRICE_PER_POUND);
        currPrdDetails.clear();
        currPrdDetails.put(flour, 65 * POUND_TO_OUNCE_RATE);
        Order ord2 = new Order();
        ord2.setProductDetails(new HashMap<>(currPrdDetails));
        expectedPrice = expectedPrice.add( // The amount of pounds in 65 ounces is 65*1/16= 4.0625 pounds, hence the price to add should be equal to $1.99*4.0625
                BigDecimal.valueOf(1.99*4.0625).setScale(4, RoundingMode.HALF_UP)
        );

        // Price computation and Assertion
        Assertions.assertEquals(
                expectedPrice,
                computeOrderService.computeOrderPrice( Arrays.asList( new Order[]{ord1, ord2}) )
        );

    }

}
