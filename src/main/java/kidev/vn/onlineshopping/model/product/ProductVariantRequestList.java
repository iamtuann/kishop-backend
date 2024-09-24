package kidev.vn.onlineshopping.model.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductVariantRequestList {
    private List<ProductVariantRequest> models;
}
