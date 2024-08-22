package kidev.vn.onlineshopping.model.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetailRequest {
    private Long productVariantId;
    private List<ProductDetailRequestModel> models;
}
