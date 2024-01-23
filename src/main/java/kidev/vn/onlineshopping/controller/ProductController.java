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
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/products")
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

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    @PostMapping("/save")
    public CommonResponse<ProductResponse> saveProduct(@RequestBody ProductRequest request) {
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
            product.setSlug(toSlug(request.getName()));
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
            for (String colorName : request.getListColor()) {
                Color color = colorService.getColorByEngName(colorName);
                colors.add(color);
            }
            for (ProductDetailRequestModel model : request.getModels()) {
                ProductDetail pd;
                if (model.getId() != null) {
                    pd = productDetailService.findOne(new Long(model.getId()));
                } else {
                    pd = new ProductDetail();
                }
                pd.setProduct(product);

                pd.setName(model.getName());
                pd.setStatus(model.getStatus());
                pd.setPrice(new Long(model.getPrice()));
                if (model.getOffPrice() != null) {
                    pd.setOffPrice(new Long(model.getOffPrice()));
                }
                pd = productDetailService.saveProductDetail(pd);
                String fileNamePreview = (product.getSlug() + "-preview-" + model.getPreviewImage().getOriginalFilename()).replaceAll(" ", "");
                String uploadDirPreview = storeFolder + fileNamePreview;
                String imageUrl = storeUrl + fileNamePreview;
                File destPreview = new File(uploadDirPreview);
                model.getPreviewImage().transferTo(destPreview);
                pd.setImageUrl(imageUrl);
                pd.setImagePath(uploadDirPreview);

                List<ProductImage> productImages = new ArrayList<>();
                if (model.getImages() != null) {
                    for (MultipartFile image : model.getImages()) {
                        String fileName = (product.getSlug() + "-" + toSlug(pd.getName()) + "-" + image.getOriginalFilename()).replaceAll(" ", "");
                        String uploadDir = storeFolder + fileName;
                        String url = storeUrl + fileName;
                        File dest = new File(uploadDir);
                        image.transferTo(dest);
                        ProductImage productImage = new ProductImage();
                        productImage.setProductDetail(pd);
                        productImage.setUrl(url);
                        productImage.setPathUrl(uploadDir);
                        productImages.add(productImage);
                    }
                }
                productImages = productImageService.saveAll(productImages);
                pd.setProductImages(productImages);
                pd = productDetailService.saveProductDetail(pd);
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
                                           @RequestParam(value = "productDetailId", required = false) Long detailId) {
        CommonResponse<?> response = new CommonResponse<>();
        try {
            Product product = productService.findOne(productId);
            product.setStatus(status);
            ProductDetail productDetail = productDetailService.findOne(detailId);
            if (detailId == null) {
                product.setProductPreview(product.getProductDetails().get(0));
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            } else if (productDetail.getProduct().getId() == productId) {
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
                                           @RequestParam(value = "categoryIds", required = false) List<Long> categoryIds,
                                           @RequestParam(value = "brandId", required = false) String brandId,
                                           @RequestParam(value = "sizeId", required = false) String sizeId,
                                           @RequestParam(value = "colorId", required = false) String colorId,
                                           @RequestParam(value = "sale", required = false) Boolean sale,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "25") int size,
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
            pageable = PageRequest.of(page - 1, size, sort);
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

    @GetMapping("/{slug}")
    public CommonResponse<?> getProductBySlug(@PathVariable("slug") String slug) {
        CommonResponse<ProductModel> response = new CommonResponse<>();
        try {
            ProductModel productModel = productService.getProductSellingtBySlug(slug);
            if (productModel != null) {
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setError("Thành công");
                response.setOutput(productModel);
            } else {
                response.setStatusCode(Constants.RestApiReturnCode.NOT_FOUND);
                response.setMessage(Constants.RestApiReturnCode.NOT_FOUND_TXT);
                response.setError("Không tìm thấy sp");
                response.setOutput(null);
            }
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Có lỗi xảy ra");
            logger.error("Get product detail", e);
        }
        return response;
    }

    @GetMapping("/top-new")
    public CommonResponse<?> getTopNewProduct(@RequestParam(value = "size", required = false, defaultValue = "4") int size) {
        CommonResponse<List<ProductBasicModel>> response = new CommonResponse<>();
        try {
            Pageable pageable;
            pageable = PageRequest.of(0, size, Sort.by("createdDate").descending());
            List<ProductBasicModel> productModels = productService.getTopProductByCreatedDate(pageable);
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setError("Thành công");
            response.setOutput(productModels);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Có lỗi xảy ra");
            logger.error("Get top new product", e);
        }
        return response;
    }
}
