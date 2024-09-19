package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.ProductDetail;
import kidev.vn.onlineshopping.entity.ProductVariant;
import kidev.vn.onlineshopping.model.product.ProductDetailRequestModel;
import kidev.vn.onlineshopping.repository.ProductDetailRepo;
import kidev.vn.onlineshopping.service.ProductDetailService;
import kidev.vn.onlineshopping.service.SizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepo productDetailRepo;

    private final SizeService sizeService;


    @Override
    public ProductDetail findOne(Long id) {
        return productDetailRepo.getProductDetailById(id);
    }

    @Override
    public void create(ProductDetail productDetail) {
        productDetailRepo.save(productDetail);
    }

    @Override
    public ProductDetail update(ProductVariant productVariant, ProductDetailRequestModel detailRequestModel) {
        ProductDetail productDetail;
        if (detailRequestModel.getId() != null) {
            productDetail = productDetailRepo.getProductDetailById(detailRequestModel.getId());
        } else {
            productDetail = new ProductDetail();
            productDetail.setProductVariant(productVariant);
        }
        productDetail.setSize(sizeService.findOne(detailRequestModel.getSizeId()));
        productDetail.setQuantity(detailRequestModel.getQuantity());
        return productDetailRepo.save(productDetail);
    }


    @Override
    public void delete(ProductDetail productDetail) {
        productDetailRepo.delete(productDetail);
    }
}
