package kidev.vn.onlineshopping.model.category;

import kidev.vn.onlineshopping.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryModel {
    private Long id;
    private String name;
    private String slug;
    private String description;

    public CategoryModel(Category cate) {
        this.id = cate.getId();
        this.name = cate.getName();
        this.slug = cate.getSlug();
        this.description = cate.getDescription();
    }
}
