package com.mss.supermarket.business;

import com.mss.supermarket.model.Product;

import java.math.BigDecimal;

/**
 * PriceCalculation concrete Class
 * Implementation of the 2nd business rules on pricing
 * $1.99 per pound : one pound is the pricing unit here
 * @author RNF
 */
public class PriceCalculation2 implements PriceCalculationByScheme<Float> {
    private String name;
    private final static BigDecimal FIXED_PRICE_PER_POUND_VAL = new BigDecimal(1.99);
    @Override
    public BigDecimal compute(Product p, Float qty) {
        if (p.getCurrentPricingScheme().equals(PricingSchemeType.FIXED_PRICE_PER_POUND))
            p.setProductUnitPrice(FIXED_PRICE_PER_POUND_VAL);
        return p.getProductUnitPrice().multiply(new BigDecimal(qty));
    }

}
