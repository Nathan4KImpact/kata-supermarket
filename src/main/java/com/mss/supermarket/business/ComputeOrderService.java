package com.mss.supermarket.business;

import com.mss.supermarket.model.Order;
import com.mss.supermarket.utils.IPriceCalculationByScheme;
import com.mss.supermarket.utils.PricingSchemeFactory;

import java.math.BigDecimal;
import java.util.List;

public class ComputeOrderService  {

    public BigDecimal computeOrderPrice(List<Order> ordList) {

        return ordList.stream()
                .map(ord -> {
                    ord.getProductDetails().forEach((p, qty) ->  {
                            IPriceCalculationByScheme priceByScheme = PricingSchemeFactory.getInstance(p.getCurrentPricingScheme());
                            if(p.getCurrentPricingScheme() != null)
                                ord.addPriceToOrder(
                                        priceByScheme.compute(p, qty)
                                );
                            else if (qty instanceof Integer ){
                                ord.addPriceToOrder(
                                        priceByScheme.defaultComputation(p, (Integer)qty)
                                ); //default way of total price calculation in case the measure unit is of integer type
                            } else if (qty instanceof Float ){
                                ord.addPriceToOrder(
                                        priceByScheme.defaultComputation(p, (Float)qty)
                                ); //default way of total price calculation in case the measure unit is of float type
                            } else{
                                System.out.println("Le type d'unité de mesure n'est pas encore prise en charge par l'application...");
                            }
                        });
                    return ord.getTotalOrderPrice();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

}
