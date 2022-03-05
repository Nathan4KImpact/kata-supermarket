import com.mss.supermarket.business.EnumPricingSchemeType;
import com.mss.supermarket.model.Order;
import com.mss.supermarket.model.Product;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class ComputeOrderTest {
    final static Float POUND_TO_OUNCE_RATE = 1F/16F;

    final Product oPrdDft = new Product("Computer", new BigDecimal(999), null);
    final Product oPrdB2G1 = new Product("Cup", new BigDecimal(2), EnumPricingSchemeType.BUY_TWO_GET_ONE_FREE);
    final Product oPrd3for1$ = new Product("Chocolate bar", new BigDecimal("0.5"), EnumPricingSchemeType.THREE_FOR_ONE_DOLLAR);
    final Product oPrdFixPPP = new Product("Flour", new BigDecimal("0.65"), EnumPricingSchemeType.FIXED_PRICE_PER_POUND);

    final Map<Product, Integer> prdDetailsIntQty = new HashMap<>();
    final Map<Product, Float> prdDetails4FloatQty = new HashMap<>();
    Order ord = new Order();

    /* Price of 02 with the default pricing : price*qty */
    @Test
    public void defaultPricing() {

        // The price should be the sum of unit price for each item 7*$999
        prdDetailsIntQty.put(oPrdDft, 2);
        ord.setProductDetails(prdDetailsIntQty);
        ord.computeOrderPrice();
        Assert.assertEquals(BigDecimal.valueOf(2 * 999), ord.getTotalOrderPrice() );
    }

    /* Adding the price to add of 03 items with the scheme BUY_TWO_GET_ONE_FREE */
    @Test
    public void pricingWithSchemeB2G1() {
        // The price  should be equal to 2*$2 for the 3 cups
        prdDetailsIntQty.put(oPrdB2G1, 3);
        ord.setProductDetails(prdDetailsIntQty);
        ord.computeOrderPrice();

        Assert.assertEquals(BigDecimal.valueOf(2 * 2), ord.getTotalOrderPrice());
    }
    @Test
    public void pricingWithSchemeB2G1_1() {
        // The price should be equal to 4*$2 for the 6 cups
        prdDetailsIntQty.put(oPrdB2G1, 6);
        ord.setProductDetails(prdDetailsIntQty);
        ord.computeOrderPrice();

        Assert.assertEquals(BigDecimal.valueOf(4 * 2), ord.getTotalOrderPrice());
    }
    @Test
    public void pricingWithSchemeB2G1_2() {
        // The price  should be equal to 5*$2 for the 7 cups
        prdDetailsIntQty.put(oPrdB2G1, 7);
        ord.setProductDetails(prdDetailsIntQty);
        ord.computeOrderPrice();

        Assert.assertEquals(BigDecimal.valueOf(5 * 2), ord.getTotalOrderPrice());
    }

    /*Adding the price for 04 items with the scheme THREE_FOR_ONE_DOLLAR */
    @Test
    public void pricingWithScheme3for1$() {
        // The price should be equal to  plus 4/3 == $1 plus the normal price of 4%3=1 more item = $1 + $0.5
        prdDetailsIntQty.put(oPrd3for1$, 4);
        ord.setProductDetails(prdDetailsIntQty);
        ord.computeOrderPrice();
        Assert.assertEquals(
                BigDecimal.valueOf(1 + 0.5).setScale(4, RoundingMode.HALF_UP),
                ord.getTotalOrderPrice()
        );
    }
    /*Adding the price for 08 items with the scheme THREE_FOR_ONE_DOLLAR */
    @Test
    public void pricingWithScheme3for1$_1() {
        // The price should be equal to 8/3==2 == $2 plus the normal price of 8%3=2 more item = $2 + $0.5*2
        prdDetailsIntQty.put(oPrd3for1$, 8);
        ord.setProductDetails(prdDetailsIntQty);
        ord.computeOrderPrice();
        Assert.assertEquals(
                BigDecimal.valueOf(3).setScale(4, RoundingMode.HALF_UP),
                ord.getTotalOrderPrice()
        );
    }

    /*Adding the price for 08 items with the scheme THREE_FOR_ONE_DOLLAR */
    @Test
    public void pricingWithScheme3for1$_2() {
        // The price should be equal to 9/3==3 == $3
        prdDetailsIntQty.put(oPrd3for1$, 9);
        ord.setProductDetails(prdDetailsIntQty);
        ord.computeOrderPrice();
        Assert.assertEquals(
                BigDecimal.valueOf(3).setScale(4, RoundingMode.HALF_UP),
                ord.getTotalOrderPrice()
        );
    }

    /* Adding the price of 04 ounces of Beans with the scheme FIXED_PRICE_PER_POUND */
    @Test
    public void pricingWithSchemeFixPPP() {
        // The amount of pounds in 4 ounces is 4*1/16= 0.25 pound, hence the price to add should be equal to $1.99*0.25
        prdDetails4FloatQty.put(oPrdFixPPP, 4 * POUND_TO_OUNCE_RATE);
        ord.setProductDetails(prdDetails4FloatQty);
        ord.computeOrderPrice();
        Assert.assertEquals(
                BigDecimal.valueOf(1.99*0.25).setScale(4, RoundingMode.HALF_UP),
                ord.getTotalOrderPrice()
        );
    }

    /* Adding the price of 65 ounces of Beans with the scheme FIXED_PRICE_PER_POUND */
    @Test
    public void pricingWithSchemeFixPPP_1() {
        // The amount of pounds in 65 ounces is 65*1/16= 4.0625 pounds, hence the price to add should be equal to $1.99*4.0625
        prdDetails4FloatQty.put(oPrdFixPPP, 65 * POUND_TO_OUNCE_RATE);
        ord.setProductDetails(prdDetails4FloatQty);
        ord.computeOrderPrice();
        Assert.assertEquals(
                BigDecimal.valueOf(1.99*4.0625).setScale(4, RoundingMode.HALF_UP),
                ord.getTotalOrderPrice()
        );

    }

    /* Adding the price of many products with various pricing schemes */
    @Test
    public void pricingWithAllSchemes() {

        prdDetailsIntQty.put(oPrdB2G1, 7); // The price  should be equal to 5*$2 for the 7 cups
        BigDecimal expected1 = BigDecimal.valueOf(5 * 2).setScale(4, RoundingMode.HALF_UP); //expected

        prdDetailsIntQty.put(oPrd3for1$, 8); //  The price should be equal to 8/3==2 == $2 plus the normal price of 8%3=2 more item = $2 + $0.5*2
        BigDecimal expected2 = BigDecimal.valueOf(3).setScale(4, RoundingMode.HALF_UP); //expected

        ord.setProductDetails(prdDetailsIntQty);
        ord.computeOrderPrice();
        BigDecimal resultWithIntQty =  ord.getTotalOrderPrice();

        ord.setTotalOrderPrice(BigDecimal.ZERO);
        prdDetails4FloatQty.put(oPrdFixPPP, 65 * POUND_TO_OUNCE_RATE); // The amount of pounds in 65 ounces is 65*1/16= 4.0625 pounds, hence the price to add should be equal to $1.99*4.0625
        ord.setProductDetails(prdDetails4FloatQty);
        ord.computeOrderPrice();
        BigDecimal expected3 = BigDecimal.valueOf(1.99*4.0625).setScale(4, RoundingMode.HALF_UP); //expected
        BigDecimal resultWithFloatQty =  ord.getTotalOrderPrice();

        Assert.assertEquals(
                expected1.add(expected2).add(expected3),
                resultWithIntQty.add(resultWithFloatQty)
        );

    }

}
