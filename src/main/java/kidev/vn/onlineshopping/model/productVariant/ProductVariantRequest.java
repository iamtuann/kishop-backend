package kidev.vn.onlineshopping.model.productVariant;

import lombok.Data;

import java.util.List;

@Data
public class ProductVariantRequest {
    private Long id;
    private String name;
    private Long price;
    private Long oldPrice;
    private Integer status;
    private List<Long> mediaIds;
    private Long previewMediaId;
    private List<Long> colorIds;
}
