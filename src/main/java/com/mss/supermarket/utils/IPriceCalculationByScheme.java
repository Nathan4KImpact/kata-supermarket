package com.mss.supermarket.utils;

import com.mss.supermarket.model.Product;

import java.math.BigDecimal;

public interface IPriceCalculationByScheme<T> {
    BigDecimal compute(Product p, T qty);

    default BigDecimal defaultComputation(Product p, Integer qty){
        return  p.getProductUnitPrice().multiply(new BigDecimal(qty));
    }

    default BigDecimal defaultComputation(Product p, Float qty){
        return  p.getProductUnitPrice().multiply(new BigDecimal(qty));
    }
}
