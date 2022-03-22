import com.mss.supermarket.utils.EnumPricingSchemeType;
import com.mss.supermarket.model.Product;

import java.math.BigDecimal;

public abstract class AbstractSupermarketData {
    public Product getProduct(String productLabel, String price, EnumPricingSchemeType princingType){
        return new Product(productLabel, new BigDecimal(price), princingType);
    }
}
