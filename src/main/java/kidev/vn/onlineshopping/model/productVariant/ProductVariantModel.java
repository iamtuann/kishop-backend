package kidev.vn.onlineshopping.model.productVariant;

import kidev.vn.onlineshopping.entity.Color;
import kidev.vn.onlineshopping.entity.Media;
import kidev.vn.onlineshopping.entity.ProductVariant;
import kidev.vn.onlineshopping.model.productDetail.ProductDetailModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantModel extends ProductVariantBasic {
    private List<String> colors;
    private List<String> imageUrls;
    private List<ProductDetailModel> productDetails;

    public ProductVariantModel(ProductVariant pv) {
        super(pv);
        this.colors = pv.getColors().stream().map(Color::getEngName).collect(Collectors.toList());
        this.imageUrls = pv.getProductVariantMedia().stream()
                .map(Media::getUrl)
                .collect(Collectors.toList());
        this.productDetails = pv.getProductDetails().stream()
                .map(ProductDetailModel::new)
                .collect(Collectors.toList());
    }
}
