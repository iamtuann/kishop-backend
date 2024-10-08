package kidev.vn.onlineshopping.model.productDetail;

import lombok.Data;

@Data
public class ProductDetailRequest {
    private Long id;
    private Long sizeId;
    private Integer quantity;
}
