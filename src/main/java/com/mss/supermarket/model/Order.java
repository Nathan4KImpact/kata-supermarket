package com.mss.supermarket.model;

import com.mss.supermarket.business.PriceCalculationByScheme;
import com.mss.supermarket.business.PricingSchemeType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Order Class
 * @author RNF
 */
public class Order<T> {

    private BigDecimal totalOrderPrice = BigDecimal.ZERO;
    private Map<Product, T> productDetails = new HashMap<>();

    public Order() {
    }

    public Order(Map<Product, T> pdtDetails ) {
        productDetails.clear();
        productDetails.putAll(pdtDetails);
        totalOrderPrice= BigDecimal.ZERO;
    }

    public BigDecimal getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(BigDecimal totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }


    public void setProductDetails(Map<Product, T> productDetails) {
        this.productDetails = productDetails;
    }

    public void computeOrderPrice() {
        productDetails.forEach((p, qty) ->  {
            if(p.getCurrentPricingScheme() != null)
                setTotalOrderPrice(totalOrderPrice.add(
                        p.getPriceCalculationByScheme().compute(p, qty)
                    )
                );
            else if (qty instanceof Integer ){
                setTotalOrderPrice(totalOrderPrice.add(
                        p.getPriceCalculationByScheme().defaultComputation(p, (Integer)qty))
                ); //default way of total price calculation in case the measure unit is of integer type
            } else if (qty instanceof Float ){
                setTotalOrderPrice(totalOrderPrice.add(
                        p.getPriceCalculationByScheme().defaultComputation(p, (Float)qty)
                )); //default way of total price calculation in case the measure unit is of float type
            } else{
                System.out.println("Le type d'unité de mesure n'est pas encore pris en charge par l'application...");
            }
        });
    }
}
