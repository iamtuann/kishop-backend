package kidev.vn.onlineshopping.model.product;

import kidev.vn.onlineshopping.entity.Category;
import kidev.vn.onlineshopping.entity.Gender;
import kidev.vn.onlineshopping.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private Long id;

    private String name;

    private String description;

    private Long brandId;

    private Integer status;

    private List<Long> categoryIds;

    private Long productVariantId;

    private List<Long> genderIds;

    public ProductRequest(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.brandId = product.getBrand().getId();
        this.status = product.getStatus();
        this.categoryIds = product.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList());
        this.productVariantId = product.getProductPreview() != null ? product.getProductPreview().getId() : null;
        this.genderIds = product.getGender().stream()
                .map(Gender::getId).collect(Collectors.toList());
    }
}
