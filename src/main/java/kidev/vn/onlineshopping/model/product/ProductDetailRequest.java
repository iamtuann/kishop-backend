package kidev.vn.onlineshopping.model.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetailRequest {
    private Long productId;
    private List<ProductDetailRequestModel> models;
    private List<String> listColor;
}
