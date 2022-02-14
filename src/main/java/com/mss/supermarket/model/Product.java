package com.mss.supermarket.model;

import com.mss.supermarket.business.PriceCalculationByScheme;
import com.mss.supermarket.business.PricingSchemeType;
import com.mss.supermarket.utils.PricingSchemeFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Product Class
 * @author RNF
 */
public class Product {
    private Serializable productId;
    private String productLabel;
    protected BigDecimal productUnitPrice;
    protected PricingSchemeType currentPricingSchemeType;
    private PriceCalculationByScheme priceCalculationByScheme;
    protected final static Set<PricingSchemeType> pricingSchemeHistory = new HashSet<>();

    public Product(String productLabel, BigDecimal productUnitPrice, PricingSchemeType currentPricingSchemeType) {
        this.productLabel = productLabel;
        this.productUnitPrice = productUnitPrice;
        this.currentPricingSchemeType = currentPricingSchemeType;
        this.priceCalculationByScheme = PricingSchemeFactory.factory(currentPricingSchemeType);
        pricingSchemeHistory.add(currentPricingSchemeType);
    }

    public String getProductLabel() {
        return productLabel;
    }

    public BigDecimal getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(BigDecimal productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public PricingSchemeType getCurrentPricingScheme() {
        return currentPricingSchemeType;
    }

    public void setCurrentPricingScheme(PricingSchemeType currentPricingScheme) {
        this.currentPricingSchemeType = currentPricingScheme;
    }

    public PriceCalculationByScheme getPriceCalculationByScheme() {
        return priceCalculationByScheme;
    }

    public void addPricingScheme(PricingSchemeType ps){
        pricingSchemeHistory.add(ps);
    }

}
