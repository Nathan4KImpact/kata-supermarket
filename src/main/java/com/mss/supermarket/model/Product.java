package com.mss.supermarket.model;

import com.mss.supermarket.business.PriceScheme;

import java.math.BigDecimal;
import java.util.List;

/**
 * Product Class
 * @author RNF
 */
public class Product {
    private String productId;
    private String productLabel;
    protected BigDecimal productPrice;
    protected PriceScheme currentPricingScheme;
    protected List<PriceScheme> pricingSchemeHistory;

    public String getProductLabel() {
        return productLabel;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public PriceScheme getCurrentPricingScheme() {
        return currentPricingScheme;
    }

    public List<PriceScheme> getPricingSchemeHistory() {
        return pricingSchemeHistory;
    }
    public void addPricingScheme( PriceScheme ps){
        pricingSchemeHistory.add(ps);
    }
    public void getPricingSchemeByIndex( PriceScheme ps){
        pricingSchemeHistory.add(ps);
    }

}
