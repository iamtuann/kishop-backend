package kidev.vn.onlineshopping.model.product;

import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.entity.ProductVariant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class ProductBasicModel {
    private Long id;
    private String name;
    private String variantName;
    private Long variantId;
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
        this.variantName = p.getProductPreview().getName();
        this.variantId = p.getProductPreview().getId();
        this.slug = p.getSlug();
        this.brand = p.getBrand().getName();
        this.description = p.getDescription();
        this.price = p.getProductPreview().getPrice();
        this.oldPrice = p.getProductPreview().getOldPrice();
        this.thumbnail = p.getProductPreview().getProductImages().get(0).getUrl();
    }
    public ProductBasicModel(ProductVariant pv) {
        this(pv, false);
    }
    public ProductBasicModel(ProductVariant pv, Boolean isFiltering) {
        Product product = pv.getProduct();
        ProductVariant productPreview = product.getProductPreview();
        this.id = product.getId();
        this.name = product.getName();
        this.variantName = isFiltering ? pv.getName() :productPreview.getName();
        this.variantId = isFiltering ? pv.getId() : productPreview.getId();
        this.slug = product.getSlug();
        this.brand = product.getBrand().getName();
        this.description = product.getDescription();
        this.price = isFiltering ? pv.getPrice() : productPreview.getPrice();
        this.oldPrice = isFiltering ? pv.getOldPrice() : productPreview.getOldPrice();
        this.thumbnail = isFiltering ? pv.getProductImages().get(0).getUrl() : productPreview.getProductImages().get(0).getUrl();
        this.color = isFiltering ? pv.getColor().getName() : productPreview.getColor().getName();
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
