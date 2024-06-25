package kidev.vn.onlineshopping.model.productOrder;

import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.entity.ProductQuantity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderModel {
    private Long productId;
    private Long variantId;
    private Long quantityId;
    private String name;
    private String variantName;
    private String slug;
    private String color;
    private String size;
    private String brand;
    private String thumbnail;
    private Long price;
    private Long oldPrice;
    private Long totalPrice;
    private Long totalOldPrice;
    private Integer quantityOrder;

    public ProductOrderModel(ProductQuantity pq, Integer quantity) {
        Product product = pq.getProductVariant().getProduct();
        this.productId = product.getId();
        this.variantId = pq.getProductVariant().getId();
        this.quantityId = pq.getId();
        this.name = product.getName();
        this.variantName = pq.getProductVariant().getName();
        this.slug = product.getSlug();
        this.color = pq.getProductVariant().getColor().getName();
        this.size = pq.getSize().getName();
        this.brand = product.getBrand().getName();
        this.thumbnail = pq.getProductVariant().getImageUrl();
        this.price = pq.getProductVariant().getPrice();
        this.oldPrice = pq.getProductVariant().getOldPrice();
        this.quantityOrder = quantity;
        this.totalPrice = pq.getProductVariant().getPrice() * this.quantityOrder;
        this.totalOldPrice = pq.getProductVariant().getOldPrice() * this.quantityOrder;
    }

    public ProductOrderModel(ProductQuantity pq) {
        this(pq, 1);
    }
}
