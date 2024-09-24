package kidev.vn.onlineshopping.model.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductVariantRequest {
    private Long id;
    private String name;
    private Long price;
    private Long oldPrice;
    private Integer status;
    private MultipartFile[] images;
    private MultipartFile previewImage;
    private List<Long> colorIds;
}
