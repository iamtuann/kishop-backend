package kidev.vn.onlineshopping.model.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private Long productId;
    private List<Long> sizeIds;

    public ProductResponse(Long productId, List<Long> sizeIds) {
        this.productId = productId;
        this.sizeIds = sizeIds;
    }
}
