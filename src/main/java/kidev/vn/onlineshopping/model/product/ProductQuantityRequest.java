package kidev.vn.onlineshopping.model.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductQuantityRequest {
    private Long productDetailId;
    private List<ProductQuantityRequestModel> models;
}
