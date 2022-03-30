import com.mss.supermarket.business.ComputeOrdersPriceService;
import com.mss.supermarket.model.Order;
import com.mss.supermarket.utils.EnumPricingSchemeType;
import com.mss.supermarket.model.Product;

import java.math.BigDecimal;
import java.util.Map;

public abstract class AbstractSupermarketData {

    final static Float POUND_TO_OUNCE_RATE = 1F/16F;

    static Map<Product, Object> currPrdDetails;
    static ComputeOrdersPriceService computeOrderService;

    static Product computer, cup, flour, chocolateBar;
    static Order ord0,ord1,ord2,ord3,ord4,ord5,ord6,ord7,ord8,ord9,ord10;


    public static Product getProduct(String productLabel, String price, EnumPricingSchemeType princingType){
        return new Product(productLabel, new BigDecimal(price), princingType);
    }
}
