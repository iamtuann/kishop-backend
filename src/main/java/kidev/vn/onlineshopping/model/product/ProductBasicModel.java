package kidev.vn.onlineshopping.model.product;

import kidev.vn.onlineshopping.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductBasicModel {
    private Long id;
    private String name;
    private String slug;
    private String brand;
    private String description;
    private Long price;
    private Long offPrice;
    private String thumbnail;

    public ProductBasicModel(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.slug = p.getSlug();
        this.brand = p.getBrand().getName();
        this.description = p.getDescription();
        this.price = p.getProductPreview().getPrice();
        this.offPrice = p.getProductPreview().getOffPrice();
        this.thumbnail = p.getProductPreview().getProductImages().get(0).getUrl();
    }
}
