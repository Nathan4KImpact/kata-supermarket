package com.mss.supermarket.utils;

import com.mss.supermarket.business.*;

public class PricingSchemeFactory {
    private static IPriceCalculationByScheme oPcBs;

    private PricingSchemeFactory(){}

    public synchronized static IPriceCalculationByScheme getInstance(EnumPricingSchemeType pPsType){
            if (pPsType == null)
                pPsType = EnumPricingSchemeType.DEFAULT;
            switch (pPsType){
                case THREE_FOR_ONE_DOLLAR: oPcBs = new PriceCalculation341D();break;
                case FIXED_PRICE_PER_POUND:
                case DEFAULT :
                    oPcBs = new PriceCalculationFixPPP();break;
                case BUY_TWO_GET_ONE_FREE: oPcBs = new PriceCalculationB2G1();break;
            }
        return oPcBs;
    }

}
