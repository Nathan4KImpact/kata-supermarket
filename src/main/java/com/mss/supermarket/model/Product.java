package com.mss.supermarket.model;

import com.mss.supermarket.business.IPriceCalculationByScheme;
import com.mss.supermarket.business.EnumPricingSchemeType;
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
    private final String productLabel;
    protected BigDecimal productUnitPrice;
    protected EnumPricingSchemeType currentPricingSchemeType;
    private final IPriceCalculationByScheme priceCalculationByScheme;
    protected final static Set<EnumPricingSchemeType> pricingSchemeHistory = new HashSet<>();

    public Product(String productLabel, BigDecimal productUnitPrice, EnumPricingSchemeType currPricingSchemeType) {
        this.productLabel = productLabel;
        this.productUnitPrice = productUnitPrice;
        this.currentPricingSchemeType = currPricingSchemeType;
        this.priceCalculationByScheme = PricingSchemeFactory.getInstance(currPricingSchemeType);
        Product.pricingSchemeHistory.add(currPricingSchemeType);// maintaining an audit trail of history of pricing scheme on a product
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

    public EnumPricingSchemeType getCurrentPricingScheme() {
        return currentPricingSchemeType;
    }

    public void setCurrentPricingScheme(EnumPricingSchemeType currentPricingScheme) {
        this.currentPricingSchemeType = currentPricingScheme;
    }

    public IPriceCalculationByScheme getPriceCalculationByScheme() {
        return priceCalculationByScheme;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", productLabel='" + productLabel + '\'' +
                ", productUnitPrice=" + productUnitPrice +
                ", currentPricingSchemeType=" + currentPricingSchemeType +
                '}';
    }
}
