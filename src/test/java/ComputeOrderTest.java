import com.mss.supermarket.business.ComputeOrdersPriceService;
import com.mss.supermarket.utils.EnumPricingSchemeType;
import com.mss.supermarket.model.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class ComputeOrderTest extends AbstractSupermarketData {


    /* TestCase 1  : 02 items  with the default pricing : price*qty */
    @Test
    public void testComputeOrderPrice_DefaultPricing() {
        // The price should be the sum of unit price for each item 2*$999
        Assertions.assertEquals(BigDecimal.valueOf(2 * 999), computeOrderService.computeOrdersPrice( Arrays.asList( new Order[]{ord0}) ));
    }

    /*  TestCase 2  :  03 items with the scheme BUY_TWO_GET_ONE_FREE */
    @Test
    public void testComputeOrderPrice_WithSchemeB2G1() {
        // The price  should be equal to 2*$2 for the 3 cups
        Assertions.assertEquals(BigDecimal.valueOf(2 * 2), computeOrderService.computeOrdersPrice( Arrays.asList( new Order[]{ord1}) ));
    }

    /*  TestCase 3  : 06 items with the scheme BUY_TWO_GET_ONE_FREE */
    @Test
    public void testComputeOrderPrice_WithSchemeB2G1_1() {
        // The price should be equal to 4*$2 for the 6 cups
        Assertions.assertEquals(BigDecimal.valueOf(4 * 2), computeOrderService.computeOrdersPrice( Arrays.asList( new Order[]{ord2}) ));
    }

    /*  TestCase 4 :  07 items with the scheme BUY_TWO_GET_ONE_FREE */
    @Test
    public void testComputeOrderPrice_WithSchemeB2G1_2() {
        // The price  should be equal to 5*$2 for the 7 cups
        Assertions.assertEquals(
                BigDecimal.valueOf(5 * 2),
                computeOrderService.computeOrdersPrice( Arrays.asList( new Order[]{ord3}) )
        );
    }

    /*  TestCase 5 : 04 items with the scheme THREE_FOR_ONE_DOLLAR */
    @Test
    public void testComputeOrderPrice_WithScheme3for1$() {
        // The price should be equal to  plus 4/3 == $1 plus the normal price of 4%3=1 more item = $1 + $0.5
        Assertions.assertEquals(
                BigDecimal.valueOf(1 + 0.5).setScale(4, RoundingMode.HALF_UP),
                computeOrderService.computeOrdersPrice( Arrays.asList( new Order[]{ord4}) )
        );
    }

    /*  TestCase 6 : 08 items with the scheme THREE_FOR_ONE_DOLLAR */
    @Test
    public void testComputeOrderPrice_WithScheme3for1$_1() {
        // The price should be equal to 8/3==2 == $2 plus the normal price of 8%3=2 more item = $2 + $0.5*2
        Assertions.assertEquals(
                BigDecimal.valueOf(3).setScale(4, RoundingMode.HALF_UP),
                computeOrderService.computeOrdersPrice( Arrays.asList( new Order[]{ord5}) )
        );
    }

    /*  TestCase 7 : 09 items with the scheme THREE_FOR_ONE_DOLLAR */
    @Test
    public void testComputeOrderPrice_WithScheme3for1$_2() {
        // The price should be equal to 9/3==3 == $3
        Assertions.assertEquals(
                BigDecimal.valueOf(3).setScale(4, RoundingMode.HALF_UP),
                computeOrderService.computeOrdersPrice( Arrays.asList( new Order[]{ord6}) )
        );
    }

    /*  TestCase 8 :  04 ounces of Beans with the scheme FIXED_PRICE_PER_POUND */
    @Test
    public void testComputeOrderPrice_WithSchemeFixPPP() {
        // The amount of pounds in 4 ounces is 4*1/16= 0.25 pound, hence the price to add should be equal to $1.99*0.25
        Assertions.assertEquals(
                BigDecimal.valueOf(1.99*0.25).setScale(4, RoundingMode.HALF_UP),
                computeOrderService.computeOrdersPrice( Arrays.asList( new Order[]{ord7}) )
        );
    }

    /*  TestCase 9 :  65 ounces of Beans with the scheme FIXED_PRICE_PER_POUND */
    @Test
    public void testComputeOrderPrice_WithSchemeFixPPP_1() {
        // The amount of pounds in 65 ounces is 65*1/16= 4.0625 pounds, hence the price to add should be equal to $1.99*4.0625
        Assertions.assertEquals(
                BigDecimal.valueOf(1.99*4.0625).setScale(4, RoundingMode.HALF_UP),
                computeOrderService.computeOrdersPrice( Arrays.asList( new Order[]{ord8}))
        );

    }

    /*  TestCase 10 :  products from various pricing schemes */
    @Test
    public void testComputeOrderPrice_WithAllSchemes() {
        BigDecimal expectedTotalPrice;  // for 7 cups, 8 chocolate Bars and 65 Pounds of flour
        expectedTotalPrice = BigDecimal.valueOf(5 * 2)   // The price  7 cups  should be equal to 5*$2 for the 7 cups
                            .add(  //  The price 8 chocolate Bars should be equal to 8/3 = $2 plus the normal price of 8%3=2 more item = $2 + $0.5*2 = $3
                                        BigDecimal.valueOf(3)
                                ).add( // The amount of pounds in 65 ounces should be equal to 65*1/16 = 4.0625 pounds, hence the price to add should be equal to $1.99*4.0625
                                        BigDecimal.valueOf(1.99*4.0625).setScale(4, RoundingMode.HALF_UP)
        );

        // Price computation and Assertion
        Assertions.assertEquals(
                expectedTotalPrice,
                computeOrderService.computeOrdersPrice( Arrays.asList( new Order[]{ord9, ord10}) )
        );

    }


    @BeforeAll
    public static void setUp() {

        computeOrderService = new ComputeOrdersPriceService();
        currPrdDetails = new HashMap<>();

        computer = getProduct("Computer", "999", null);
        ord0 = new Order();
        ord0.setProductDetails(Collections.singletonMap(computer, 2));

        cup = getProduct("Cup", "2", EnumPricingSchemeType.BUY_TWO_GET_ONE_FREE);
        ord1 = new Order();
        ord1.setProductDetails(Collections.singletonMap(cup, 3));

        ord2 = new Order();
        ord2.setProductDetails(Collections.singletonMap(cup, 6));

        ord3 = new Order();
        ord3.setProductDetails(Collections.singletonMap(cup, 7));

        chocolateBar = getProduct("Chocolate bar", "0.5", EnumPricingSchemeType.THREE_FOR_ONE_DOLLAR);
        ord4 = new Order();
        ord4.setProductDetails(Collections.singletonMap(chocolateBar, 4));

        ord5 = new Order();
        ord5.setProductDetails(Collections.singletonMap(chocolateBar, 8));

        ord6 = new Order();
        ord6.setProductDetails(Collections.singletonMap(chocolateBar,9));


        flour = getProduct("Flour", "0.65", EnumPricingSchemeType.FIXED_PRICE_PER_POUND);
        ord7 = new Order();
        ord7.setProductDetails(Collections.singletonMap(flour,  4 * POUND_TO_OUNCE_RATE));

        ord8 = new Order();
        ord8.setProductDetails(Collections.singletonMap(flour,  65 * POUND_TO_OUNCE_RATE));


        // Order for product based on Int quantities
        ord9 = new Order();
        currPrdDetails.put(cup, 7);
        currPrdDetails.put(chocolateBar, 8);
        ord9.setProductDetails(new HashMap<>(currPrdDetails));

        // Order for product based on float quantities
        ord10 = new Order();
        currPrdDetails.clear();
        currPrdDetails.put(flour, 65 * POUND_TO_OUNCE_RATE);
        ord10.setProductDetails(new HashMap<>(currPrdDetails));


    }

}
