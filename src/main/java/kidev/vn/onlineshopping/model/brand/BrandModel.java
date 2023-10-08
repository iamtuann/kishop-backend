package kidev.vn.onlineshopping.model.brand;

import kidev.vn.onlineshopping.entity.Brand;
import lombok.Data;

@Data
public class BrandModel {
    private Long id;
    private String name;
    private String origin;
    private String description;

    public BrandModel(Brand b) {
        this.id = b.getId();
        this.name = b.getName();
        this.description = b.getDescription();
        this.origin = b.getOrigin();
    }
}
