package com.mss.supermarket.business;

import com.mss.supermarket.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * PriceCalculation concrete Class
 * Implementation of the 1st business rules on pricing
 * Three for a dollar
 * @author RNF
 */
public class PriceCalculation341D implements IPriceCalculationByScheme<Integer> {
    private String name;
    private final static BigDecimal UNIT_PRICE_FOR_3 = BigDecimal.valueOf(1);

    @Override
    public BigDecimal compute(Product p, Integer qty) {
        int res = qty % 3;
        return  new BigDecimal(qty/3).multiply(UNIT_PRICE_FOR_3)
                    .add(p.getProductUnitPrice()
                            .multiply( new BigDecimal(res) ).setScale(4, RoundingMode.HALF_UP)
                    );

    }
}
