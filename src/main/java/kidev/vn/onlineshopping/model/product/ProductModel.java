package kidev.vn.onlineshopping.model.product;

import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.entity.ProductVariant;
import kidev.vn.onlineshopping.model.brand.BrandModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductModel {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private BrandModel brand;
    private List<ProductVariantModel> productVariants = new ArrayList<>();
    private Date createdDate;
    private Date updatedDate;
    private Long productPreviewId;
    private Integer status;

    public ProductModel(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.slug = p.getSlug();
        this.description = p.getDescription();
        this.brand = new BrandModel(p.getBrand());
        for(ProductVariant pv : p.getProductVariants()) {
            this.productVariants.add(new ProductVariantModel(pv));
        }
        this.createdDate = p.getCreatedDate();
        this.updatedDate = p.getUpdatedDate();
        this.productPreviewId = p.getProductPreview().getId();
        this.status = p.getStatus();
    }
}
