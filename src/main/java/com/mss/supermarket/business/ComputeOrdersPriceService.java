package com.mss.supermarket.business;

import com.mss.supermarket.model.Order;
import com.mss.supermarket.utils.IPriceCalculationByScheme;
import com.mss.supermarket.utils.PricingSchemeFactory;

import java.math.BigDecimal;
import java.util.List;

public class ComputeOrdersPriceService {

    private IPriceCalculationByScheme pricingUtilByScheme;

    public BigDecimal computeOrdersPrice(List<Order> ordList) {

        return ordList.stream()
                .map(ord -> {
                    ord.getProductDetails().forEach((p, qty) ->  {
                        pricingUtilByScheme = PricingSchemeFactory.getInstance(p.getCurrentPricingScheme());
                            if(p.getCurrentPricingScheme() != null)
                                ord.addPriceToOrder(
                                        pricingUtilByScheme.compute(p, qty)
                                );
                            else if (qty instanceof Integer ){
                                ord.addPriceToOrder(
                                        pricingUtilByScheme.defaultComputation(p, (Integer)qty)
                                ); //default way of total price calculation in case the measure unit is of integer type
                            } else if (qty instanceof Float ){
                                ord.addPriceToOrder(
                                        pricingUtilByScheme.defaultComputation(p, (Float)qty)
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
