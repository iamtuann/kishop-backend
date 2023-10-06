package kidev.vn.onlineshopping.controller;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.entity.*;
import kidev.vn.onlineshopping.model.CommonResponse;
import kidev.vn.onlineshopping.model.product.*;
import kidev.vn.onlineshopping.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;
    @Autowired
    ProductDetailService productDetailService;
    @Autowired
    ProductQuantityService productQuantityService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    BrandService brandService;
    @Autowired
    SizeService sizeService;
    @Autowired
    ColorService colorService;

    @Value("${store-folder}")
    private String storeFolder;
    @Value("${store-url}")
    private String storeUrl;

    @PostMapping("/save")
    public CommonResponse<ProductResponse> savewProduct(@RequestBody ProductRequest request) {
        CommonResponse<ProductResponse> response= new CommonResponse();
        try {
            Product product;
            if (request.getId() != null) {
                product = productService.findOne(request.getId());
            } else {
                product = new Product();
                product.setStatus(Constants.StatusProduct.NOTSELL);
                product.setCreatedDate(new Date());
            }
            if (request.getProductDetailId() != null) {
                product.setProductPreview(productDetailService.findOne(request.getProductDetailId()));
            }
            if (request.getStatus() != null) {
                product.setStatus(request.getStatus());
            }
            product.setUpdatedDate(new Date());
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setBrand(brandService.findOne(request.getBrandId()));
            List<Category> categories = new ArrayList<>();
            for(Long id : request.getCategoryIds()) {
                categories.add(categoryService.findOne(id));
            }
            product.setCategories(categories);
            List<Size> sizes = new ArrayList<>();
            for (Long id : request.getSizeIds()) {
                sizes.add(sizeService.findOne(id));
            }
            product.setSizes(sizes);
            product = productService.saveProduct(product);
            ProductResponse output = new ProductResponse(product.getId(), request.getSizeIds());

            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setOutput(output);
        } catch (Exception e) {
            logger.error("save product", e);
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Lỗi máy chủ");
        }
        return response;
    }

    @PostMapping(value = "/save-detail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse<List<ProductDetailResponse>> saveProductDetail(@ModelAttribute ProductDetailRequest request) {
        CommonResponse<List<ProductDetailResponse>> response= new CommonResponse<>();
        try {
            Product product = productService.findOne(request.getProductId());
            List<ProductDetailResponse> output = new ArrayList<>();
            List<Color> colors = new ArrayList<>();
            for (ProductDetailRequestModel model : request.getModels()) {
                ProductDetail pd;
                if (model.getId() != null) {
                    pd = productDetailService.findOne(new Long(model.getId()));
                } else {
                    pd = new ProductDetail();
                }
                pd.setProduct(product);
                Color color = colorService.findOne(new Long(model.getColorId()));
                pd.setColor(color);
                colors.add(color);
                pd.setStatus(model.getStatus());
                pd.setPrice(new Long(model.getPrice()));
                if (model.getOffPrice() != null) {
                    pd.setOffPrice(new Long(model.getOffPrice()));
                }
                pd = productDetailService.saveProductDetail(pd);
                List<ProductImage> productImages = new ArrayList<>();

                if (model.getImages() != null) {
                    for (MultipartFile image : model.getImages()) {
                        String fileName = (product.getName() + "-" + pd.getColor().getName() + "-" + image.getOriginalFilename()).replaceAll(" ", "");
                        String uploadDir = storeFolder + fileName;
                        String pathUrl = storeUrl + fileName;
                        File dest = new File(uploadDir);
                        image.transferTo(dest);
                        ProductImage productImage = new ProductImage();
                        productImage.setProductDetail(pd);
                        productImage.setUrl(pathUrl);
                        productImage.setPathUrl(uploadDir);
                        productImages.add(productImage);
                    }
                }
                productImageService.saveAll(productImages);
                output.add(new ProductDetailResponse(pd));
            }
            product.setColors(colors);
            productService.update(product);
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setOutput(output);
        } catch (Exception e) {
            logger.error("save product detail", e);
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Lỗi máy chủ");
        }
        return response;
    }

    @PostMapping("save-quantity")
    public CommonResponse<?> saveProductQuantity(@RequestBody List<ProductQuantityRequest> request) {
        CommonResponse<?> response = new CommonResponse<>();
        try {
            for (ProductQuantityRequest detail : request) {
                ProductDetail pd = productDetailService.findOne(detail.getProductDetailId());
                for (ProductQuantityRequestModel model : detail.getModels()) {
                    ProductQuantity productQuantity;
                    if (model.getId() != null) {
                        productQuantity = productQuantityService.findOne(model.getId());
                    } else {
                        productQuantity = new ProductQuantity();
                        productQuantity.setProductDetail(pd);
                    }
                    productQuantity.setSize(sizeService.findOne(model.getSizeId()));
                    productQuantity.setQuantity(model.getQuantity());
                    productQuantityService.create(productQuantity);
                }
            }
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setOutput(null);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Có lỗi xảy ra");
            logger.error("save prod quantity", e);
        }
        return response;
    }

    @PostMapping("create")
    public CommonResponse<?> createProduct(@RequestParam("productId") Long productId,
                                           @RequestParam("status") Integer status,
                                           @RequestParam("productDetailId") Long detailId) {
        CommonResponse<?> response = new CommonResponse<>();
        try {
            Product product = productService.findOne(productId);
            product.setStatus(status);
            ProductDetail productDetail = productDetailService.findOne(detailId);
            if (productDetail.getProduct().getId() == productId) {
                product.setProductPreview(productDetail);
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            } else {
                response.setStatusCode(Constants.RestApiReturnCode.BAD_REQUEST);
                response.setMessage(Constants.RestApiReturnCode.BAD_REQUEST_TXT);
                response.setError("ProductDetail không thuộc product");
            }
            productService.update(product);
            response.setOutput(null);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Có lỗi xảy ra");
            logger.error("create product", e);
        }
        return response;
    }

    @GetMapping("/list")
    public CommonResponse<?> searchProduct(@RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "categoryIds", required = false) List<String> categoryIds,
                                           @RequestParam(value = "brandId", required = false) String brandId,
                                           @RequestParam(value = "sizeId", required = false) String sizeId,
                                           @RequestParam(value = "colorId", required = false) String colorId,
                                           @RequestParam(value = "sale", required = false) Boolean sale,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(value = "key", required = false) String key,
                                           @RequestParam(value = "orderBy", required = false) String orderBy) {
        CommonResponse<Page<ProductBasicModel>> response = new CommonResponse<>();
        try {
            Sort sort = null;
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
            pageable = PageRequest.of(page - 1, 25, sort);
            Page<ProductBasicModel> model = productService.searchProduct(name, categoryIds, brandId, sizeId, colorId, sale, pageable);
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
}
