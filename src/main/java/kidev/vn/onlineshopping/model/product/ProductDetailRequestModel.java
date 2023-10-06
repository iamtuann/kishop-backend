package kidev.vn.onlineshopping.model.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDetailRequestModel {
    private Long id;
    private Long colorId;
    private Long price;
    private Long offPrice;
    private Integer status;
    private MultipartFile[] images;
}
