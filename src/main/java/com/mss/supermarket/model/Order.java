package com.mss.supermarket.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Order Class
 * @author RNF
 */
public class Order {
    private String orderId;
    private String orderLabel;
    private Map<Product, Integer> productList;

    public Order(){
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderLabel() {
        return orderLabel;
    }

    public Map<Product, Integer> getProductList() {
        return productList;
    }
}
