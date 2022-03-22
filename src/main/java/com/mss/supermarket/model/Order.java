package com.mss.supermarket.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Order Class
 * @author RNF
 */
public class Order {

    private BigDecimal totalOrderPrice;

    public Map<Product, ?> getProductDetails() {
        return productDetails;
    }

    private Map<Product, ? > productDetails;

    public Order() {
        productDetails = new HashMap<>();
        totalOrderPrice= BigDecimal.ZERO;
    }

    public BigDecimal getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void  setTotalOrderPrice(BigDecimal price) {
        totalOrderPrice = price;
    }

    public void addPriceToOrder(BigDecimal price) {
        this.setTotalOrderPrice(getTotalOrderPrice().add(price));
    }

    public void setProductDetails(Map<Product, ? > productDetails) {
        this.productDetails = productDetails;
    }
}
