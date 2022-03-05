package com.mss.supermarket.business;

import com.mss.supermarket.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * PriceCalculation concrete Class
 * Implementation of the 2nd business rules on pricing
 * $1.99 per pound : one pound is the pricing unit here
 * @author RNF
 */
public class PriceCalculationFixPPP implements IPriceCalculationByScheme<Float> {
    private String name;
    private final static BigDecimal FIXED_PRICE_PER_POUND_VAL = BigDecimal.valueOf(1.99);
    @Override
    public BigDecimal compute(Product p, Float qty) {
        p.setProductUnitPrice(FIXED_PRICE_PER_POUND_VAL);
        return p.getProductUnitPrice().multiply(new BigDecimal(qty)).setScale(4, RoundingMode.HALF_UP);
    }

}
