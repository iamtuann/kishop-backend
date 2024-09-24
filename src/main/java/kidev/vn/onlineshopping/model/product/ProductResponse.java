package kidev.vn.onlineshopping.model.product;

import lombok.Data;

@Data
public class ProductResponse {
    private Long productId;

    public ProductResponse(Long productId) {
        this.productId = productId;
    }
}
