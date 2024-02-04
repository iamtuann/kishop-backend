package kidev.vn.onlineshopping.model.product;

import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.entity.ProductDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class ProductBasicModel {
    private Long id;
    private String name;
    private String detailName;
    private String slug;
    private String brand;
    private String description;
    private Long price;
    private Long oldPrice;
    private String thumbnail;
    private String color;

    public ProductBasicModel(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.slug = p.getSlug();
        this.brand = p.getBrand().getName();
        this.description = p.getDescription();
        this.price = p.getProductPreview().getPrice();
        this.oldPrice = p.getProductPreview().getOldPrice();
        this.thumbnail = p.getProductPreview().getProductImages().get(0).getUrl();
    }

    public ProductBasicModel(ProductDetail pd) {
        Product product = pd.getProduct();
        this.id = product.getId();
        this.name = product.getName();
        this.detailName = pd.getName();
        this.slug = product.getSlug();
        this.brand = product.getBrand().getName();
        this.description = product.getDescription();
        this.price = pd.getPrice();
        this.oldPrice = pd.getOldPrice();
        this.thumbnail = pd.getProductImages().get(0).getUrl();
        this.color = pd.getColor().getName();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductBasicModel that = (ProductBasicModel) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
