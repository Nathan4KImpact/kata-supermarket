package com.mss.supermarket.business;

import com.mss.supermarket.model.Product;

import java.math.BigDecimal;

/**
 * PriceCalculation concrete Class
 * Implementation of the 1st business rules on pricing
 * Three for a dollar
 * @author RNF
 */
public class PriceCalculation1 implements PriceCalculationByScheme<Integer> {
    private String name;;

    @Override
    public BigDecimal compute(Product p, Integer qty) {
        int res = qty % 3;
        if ( res == 0)
            return new BigDecimal(1*qty/3);
        else
            return  new BigDecimal(1*qty/3).add(p.getProductUnitPrice().multiply(new BigDecimal(res)));

    }
}
