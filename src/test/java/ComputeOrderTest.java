import com.mss.supermarket.business.PricingSchemeType;
import com.mss.supermarket.model.Order;
import com.mss.supermarket.model.Product;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ComputeOrderTest {
    final static float POUND_TO_OUNCE_RATE = new Float(1)/new Float(16);

    final Product prd0 = new Product("Computer", new BigDecimal(999), null);
    final Product prd1 = new Product("Cup", new BigDecimal(2), PricingSchemeType.BUY_TWO_GET_ONE_FREE);
    Product prd2 = new Product("Chocolate bar", new BigDecimal(0.5), PricingSchemeType.THREE_FOR_ONE_DOLLAR);
    //final Product prd5 = (Product) prd2.clone();
    final Product prd3 = new Product("Flour", new BigDecimal(0.65), PricingSchemeType.FIXED_PRICE_PER_POUND);
    //final Product prd4 = new Product("Flour", new BigDecimal(1.5), PricingSchemeType.FIXED_PRICE_PER_POUND);

    final Map<Product, Integer> prdDetails1 = new HashMap<>();
    final Map<Product, Float> prdDetails2 = new HashMap<>();
    Order ord1 = new Order(), ord2 = new Order();

    /* Price of 07 with the default pricing : price*qty */
    @Test
    public void defaultPricing() {
        // the price to add should be the sum of unit price for each item 7*$999
        prdDetails1.put(prd0, Integer.valueOf(7));
        ord1.setProductDetails(prdDetails1);
        ord1.computeOrderPrice();
        assert Objects.equals(ord1.getTotalOrderPrice(), new BigDecimal(7 * 999));
    }

    /* Adding the price to add of 03 items with the scheme BUY_TWO_GET_ONE_FREE */
    @Test
    public void pricingWithScheme1() {
        // the price to add should be equal to the precedent plus 2*$2 for the 3 cups
        prdDetails1.put(prd1, Integer.valueOf(3));
        ord1.setProductDetails(prdDetails1);
        ord1.computeOrderPrice();
        assert ord1.getTotalOrderPrice().equals(
                        new BigDecimal(2 * 2)
        );
    }

    /*Adding the price for 04 items with the scheme THREE_FOR_ONE_DOLLAR */
    @Test
    public void pricingWithScheme2() {
        // the price should be equal to  plus 4/3==1 so the price to add should be $1 plus the normal price of 4%3=1 more item = $1 + $0.5
        prdDetails1.put(prd2, Integer.valueOf(4));
        ord1.setProductDetails(prdDetails1);
        ord1.computeOrderPrice();
        assert ord1.getTotalOrderPrice().equals(
               new BigDecimal(1 + 0.5)
        );
    }

    /* Adding the price of 04 ounces of Beans with the scheme FIXED_PRICE_PER_POUND */
    @Test
    public void pricingWithScheme3() {
        // The amount of pounds in 4 ounces is 4*1/16= 0.25 pound, hence the price to add should be equal to $1.99*0.25
        prdDetails2.put(prd3, Float.valueOf(4 * POUND_TO_OUNCE_RATE));
        ord2.setProductDetails(prdDetails2);
        ord2.computeOrderPrice();
        assert ord2.getTotalOrderPrice().equals( new BigDecimal(1.99).multiply(new BigDecimal(0.25) ));

    }

}
