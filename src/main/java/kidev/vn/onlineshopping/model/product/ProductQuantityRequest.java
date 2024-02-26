package kidev.vn.onlineshopping.model.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductQuantityRequest {
    private Long productVariantId;
    private List<ProductQuantityRequestModel> models;
}
