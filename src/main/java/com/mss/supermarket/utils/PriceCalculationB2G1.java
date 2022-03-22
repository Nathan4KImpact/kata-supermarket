package com.mss.supermarket.utils;

import com.mss.supermarket.model.Product;

import java.math.BigDecimal;

/**
 * PriceCalculation concrete Class
 * Where the 3rd business rules is  implemented
 * Buy two get one for free
 * @author RNF
 */
public class PriceCalculationB2G1 implements IPriceCalculationByScheme<Integer> {
    private String name;
    private EnumPricingSchemeType pricingSchemeType;

    @Override
    public BigDecimal compute(Product p, Integer qty) {
       return new BigDecimal(qty - qty/3).multiply(p.getProductUnitPrice());

    }
}
