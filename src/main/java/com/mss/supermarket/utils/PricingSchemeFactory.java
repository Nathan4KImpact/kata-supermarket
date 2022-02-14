package com.mss.supermarket.utils;

import com.mss.supermarket.business.*;

public class PricingSchemeFactory {
    
    public static PriceCalculationByScheme factory(PricingSchemeType p){
        PriceCalculationByScheme oPcBs = null;
        if (p == null) p = PricingSchemeType.DEFAULT;
        switch (p){
            case THREE_FOR_ONE_DOLLAR: oPcBs = new PriceCalculation1();break;
            case FIXED_PRICE_PER_POUND: oPcBs = new PriceCalculation2();break;
            case BUY_TWO_GET_ONE_FREE: oPcBs = new PriceCalculation3();break;
            case DEFAULT : oPcBs = new PriceCalculation2();
        }
        return oPcBs;
    }

}
