package com.mss.supermarket.business;

import com.mss.supermarket.model.Product;

import java.math.BigDecimal;

/**
 * PriceCalculation concrete Class
 * Where the 1st business rules is  implemented
 * Buy two get one for free
 * @author RNF
 */
public class PriceCalculation3 implements PriceCalculationByScheme<Integer> {
    private String name;
    private PricingSchemeType pricingSchemeType;

    @Override
    public BigDecimal compute(Product p, Integer qty) {
        int res = qty % 2;
        if ( res == 0)
            return new BigDecimal(qty/2).multiply(p.getProductUnitPrice());
        else
            return  new BigDecimal(qty/2).multiply(p.getProductUnitPrice()).add(p.getProductUnitPrice());

    }
}
