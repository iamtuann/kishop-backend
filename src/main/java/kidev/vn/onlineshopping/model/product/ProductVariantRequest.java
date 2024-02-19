package kidev.vn.onlineshopping.model.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductVariantRequest {
    private Long productId;
    private List<ProductVariantRequestModel> models;
    private List<String> listColor;
}
