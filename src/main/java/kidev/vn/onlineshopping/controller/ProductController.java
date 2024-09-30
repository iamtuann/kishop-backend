package kidev.vn.onlineshopping.controller;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.entity.ProductVariant;
import kidev.vn.onlineshopping.model.CommonResponse;
import kidev.vn.onlineshopping.model.product.ProductBasicModel;
import kidev.vn.onlineshopping.model.product.ProductModel;
import kidev.vn.onlineshopping.model.product.ProductRequest;
import kidev.vn.onlineshopping.model.productDetail.ProductDetailRequest;
import kidev.vn.onlineshopping.model.productVariant.ProductVariantRequest;
import kidev.vn.onlineshopping.service.ProductDetailService;
import kidev.vn.onlineshopping.service.ProductService;
import kidev.vn.onlineshopping.service.ProductVariantService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/products")
@AllArgsConstructor
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    private final ProductVariantService productVariantService;

    private final ProductDetailService productDetailService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Transactional
    public CommonResponse<?> saveProduct(@RequestBody ProductRequest request) {
        CommonResponse<?> response = new CommonResponse<>();
        try {
            productService.saveProduct(request);

            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
        } catch (Exception e) {
            logger.error("save product", e);
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setMessage("Lỗi máy chủ");
        }
        return response;
    }

    @PostMapping(value = "/save-variant")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Transactional
    public CommonResponse<?> saveProductVariant(
            @RequestParam Long productId,
            @RequestBody ProductVariantRequest request
    ) {
        CommonResponse<?> response = new CommonResponse<>();
        try {
            Product product = productService.findOne(productId);
            productVariantService.saveProductVariant(request, product);
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setMessage("create product variant success");
        } catch (Exception e) {
            logger.error("save product variant", e);
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setMessage("Lỗi máy chủ");
        }
        return response;
    }

    @PostMapping("save-detail")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CommonResponse<?> saveProductDetail(
            @RequestParam Long productVariantId,
            @RequestBody ProductDetailRequest request) {
        CommonResponse<?> response = new CommonResponse<>();
        try {
            ProductVariant productVariant = productVariantService.findOne(productVariantId);
            productDetailService.save(productVariant, request);
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setOutput(null);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setMessage("Có lỗi xảy ra");
            logger.error("save prod quantity", e);
        }
        return response;
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CommonResponse<?> createProduct(@RequestParam("productId") Long productId,
                                           @RequestParam("status") Integer status,
                                           @RequestParam(value = "productVariantId", required = false) Long variantId) {
        CommonResponse<?> response = new CommonResponse<>();
        try {
            Product product = productService.findOne(productId);
            product.setStatus(status);
            ProductVariant productVariant = productVariantService.findOne(variantId);
            if (variantId == null) {
                product.setProductPreview(product.getProductVariants().get(0));
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
            } else if (Objects.equals(productVariant.getProduct().getId(), productId)) {
                product.setProductPreview(productVariant);
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
            } else {
                response.setStatusCode(Constants.RestApiReturnCode.BAD_REQUEST);
                response.setError(Constants.RestApiReturnCode.BAD_REQUEST_TXT);
                response.setMessage("ProductVariant không thuộc product");
            }
            productService.update(product);
            response.setOutput(null);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setMessage("Có lỗi xảy ra");
            logger.error("create product", e);
        }
        return response;
    }

    @GetMapping("")
    public CommonResponse<Page<ProductBasicModel>> searchProduct(@RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "categories", required = false) List<String> categories,
                                           @RequestParam(value = "brandNames", required = false) List<String> brandNames,
                                           @RequestParam(value = "colors", required = false) List<String> colors,
                                           @RequestParam(value = "genders", required = false) List<String> genders,
                                           @RequestParam(value = "sale", required = false) Boolean sale,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "25") int pageSize,
                                           @RequestParam(value = "key", required = false) String key,
                                           @RequestParam(value = "orderBy", required = false) String orderBy) {
        CommonResponse<Page<ProductBasicModel>> response = new CommonResponse<>();
        try {
            Sort sort;
            Pageable pageable;
            if (key != null && !key.isEmpty()) {
                if (orderBy.equals("asc")) {
                    sort = Sort.by(key).ascending();
                } else {
                    sort = Sort.by(key).descending();
                }
            } else {
                sort = Sort.unsorted().ascending();
            }
            pageable = PageRequest.of(page - 1, pageSize, sort);
            Page<ProductBasicModel> model = productService.searchProduct(name, categories, brandNames, colors, genders, sale, pageable);
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setOutput(model);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Có lỗi xảy ra");
            logger.error("search product", e);
        }
        return response;
    }

    @GetMapping("/{slug}")
    public CommonResponse<ProductModel> getProductBySlug(@PathVariable("slug") String slug) {
        CommonResponse<ProductModel> response = new CommonResponse<>();
        try {
            Product product = productService.getProductSellingBySlug(slug);
            if (product != null) {
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setError("Thành công");
                response.setOutput(new ProductModel(product));
            } else {
                response.setStatusCode(Constants.RestApiReturnCode.NOT_FOUND);
                response.setError(Constants.RestApiReturnCode.NOT_FOUND_TXT);
                response.setMessage("Không tìm thấy sp");
            }
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setError("Có lỗi xảy ra");
            logger.error("Get product variant", e);
        }
        return response;
    }
}
