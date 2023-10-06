package kidev.vn.onlineshopping.model.product;

import lombok.Data;

@Data
public class ProductQuantityRequestModel {
    private Long id;
    private Long sizeId;
    private Integer quantity;
}
