package kidev.vn.onlineshopping.model.product;

import kidev.vn.onlineshopping.entity.Brand;
import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.entity.ProductDetail;
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
    private List<ProductDetailModel> productDetails = new ArrayList<>();
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
        for(ProductDetail pd : p.getProductDetails()) {
            this.productDetails.add(new ProductDetailModel(pd));
        }
        this.createdDate = p.getCreatedDate();
        this.updatedDate = p.getUpdatedDate();
        this.productPreviewId = p.getProductPreview().getId();
        this.status = p.getStatus();
    }
}
