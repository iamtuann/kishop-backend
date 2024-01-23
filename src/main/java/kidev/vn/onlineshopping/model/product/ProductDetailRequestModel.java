package kidev.vn.onlineshopping.model.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDetailRequestModel {
    private Long id;
    private String name;
    private Long price;
    private Long offPrice;
    private Integer status;
    private MultipartFile[] images;
    private MultipartFile previewImage;
}
