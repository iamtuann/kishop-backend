package kidev.vn.onlineshopping.model.productVariant;

import kidev.vn.onlineshopping.entity.ProductDetail;
import kidev.vn.onlineshopping.entity.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantBasic {
    private Long id;
    private String name;
    private Long price;
    private Long oldPrice;
    private Integer status;
    private String previewImage;
    private Integer numSizes;
    private Integer totalQuantity;

    public ProductVariantBasic(ProductVariant pv) {
        this.id = pv.getId();
        this.name = pv.getName();
        this.price = pv.getPrice();
        this.oldPrice = pv.getOldPrice();
        this.status = pv.getStatus();
        this.previewImage = pv.getImagePreview().getUrl();
        this.numSizes = pv.getProductDetails().size();
        this.totalQuantity = pv.getProductDetails().stream()
                .mapToInt(ProductDetail::getQuantity).sum();
    }
}
