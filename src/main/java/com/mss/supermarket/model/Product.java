package com.mss.supermarket.model;

import com.mss.supermarket.utils.EnumPricingSchemeType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Product Class
 * @author RNF
 */
public class Product {
    private final String productLabel;
    protected BigDecimal productUnitPrice;
    protected EnumPricingSchemeType currentPricingSchemeType;
    protected final Set<EnumPricingSchemeType> pricingSchemeHistory = new HashSet<>();

    public Product(String productLabel, BigDecimal productUnitPrice, EnumPricingSchemeType currPricingSchemeType) {
        this.productLabel = productLabel;
        this.productUnitPrice = productUnitPrice;
        this.currentPricingSchemeType = currPricingSchemeType;
        this.pricingSchemeHistory.add(currPricingSchemeType);// maintaining an audit trail of history of pricing scheme on a product
    }

    public BigDecimal getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(BigDecimal productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public EnumPricingSchemeType getCurrentPricingScheme() {
        return currentPricingSchemeType;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", productLabel= '" + productLabel + '\'' +
                ", productUnitPrice= " + productUnitPrice +
                ", currentPricingSchemeType= " + currentPricingSchemeType +
                ", PricingSchemeHistory= " + pricingSchemeHistory +
                '}';
    }
}
