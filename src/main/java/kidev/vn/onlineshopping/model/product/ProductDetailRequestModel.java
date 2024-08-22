package kidev.vn.onlineshopping.model.product;

import lombok.Data;

@Data
public class ProductDetailRequestModel {
    private Long id;
    private Long sizeId;
    private Integer quantity;
}
