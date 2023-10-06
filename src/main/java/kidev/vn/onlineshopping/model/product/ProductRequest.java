package kidev.vn.onlineshopping.model.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
    private Long id;

    private String name;

    private String description;

    private Long brandId;

    private Integer status;

    private List<Long> categoryIds;

    private List<Long> sizeIds;

    private Long productDetailId;
}
