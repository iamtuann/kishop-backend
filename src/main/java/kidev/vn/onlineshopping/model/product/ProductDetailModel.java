package kidev.vn.onlineshopping.model.product;

import kidev.vn.onlineshopping.entity.Color;
import kidev.vn.onlineshopping.entity.ProductDetail;
import kidev.vn.onlineshopping.entity.ProductImage;
import kidev.vn.onlineshopping.entity.ProductQuantity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDetailModel {
    private Long id;
    private Color color;
    private Long price;
    private Long offPrice;
    private Integer status;
    private List<String> imageUrls;
    private List<ProductQuantityModel> productQuantities;

    public ProductDetailModel(ProductDetail p) {
        this.id = p.getId();
        this.color = p.getColor();
        this.price = p.getPrice();
        this.offPrice = p.getOffPrice();
        this.status = p.getStatus();
        this.imageUrls = new ArrayList<>();
        for (ProductImage image : p.getProductImages()) {
            this.imageUrls.add(image.getUrl());
        }
        this.productQuantities = new ArrayList<>();
        for (ProductQuantity pq : p.getProductQuantities()) {
            this.productQuantities.add(new ProductQuantityModel(pq));
        }
    }
}
