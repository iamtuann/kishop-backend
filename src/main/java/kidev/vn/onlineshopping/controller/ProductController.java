package kidev.vn.onlineshopping.controller;

import com.github.slugify.Slugify;
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
import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;
    @Autowired
    ProductVariantService productVariantService;
    @Autowired
    ProductDetailService productDetailService;
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

    final Slugify slg = new Slugify();

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = EDGESDHASHES.matcher(slug).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    @PostMapping("/save")
    public CommonResponse<ProductResponse> saveProduct(@RequestBody ProductRequest request) {
        CommonResponse<ProductResponse> response= new CommonResponse<>();
        try {
            Product product;
            if (request.getId() != null) {
                product = productService.findOne(request.getId());
            } else {
                product = new Product();
                product.setStatus(Constants.StatusProduct.NOTSELL);
                product.setCreatedDate(new Date());
            }
            if (request.getProductVariantId() != null) {
                product.setProductPreview(productVariantService.findOne(request.getProductVariantId()));
            }
            if (request.getStatus() != null) {
                product.setStatus(request.getStatus());
            }
            product.setUpdatedDate(new Date());
            product.setName(request.getName());
            product.setSlug(slg.slugify(request.getName()));
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

    @PostMapping(value = "/save-variant", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse<List<ProductVariantResponse>> saveProductVariant(@ModelAttribute ProductVariantRequest request) {
        CommonResponse<List<ProductVariantResponse>> response= new CommonResponse<>();
        try {
            Product product = productService.findOne(request.getProductId());
            List<ProductVariantResponse> output = new ArrayList<>();
            List<Color> colors = new ArrayList<>();
            for (String colorName : request.getListColor()) {
                Color color = colorService.getColorByEngName(colorName);
                colors.add(color);
            }
            for (ProductVariantRequestModel model : request.getModels()) {
                ProductVariant pv;
                if (model.getId() != null) {
                    pv = productVariantService.findOne(model.getId());
                } else {
                    pv = new ProductVariant();
                }
                pv.setProduct(product);

                pv.setName(model.getName());
                pv.setStatus(model.getStatus());
                pv.setPrice(model.getPrice());
                if (model.getOldPrice() != null) {
                    pv.setOldPrice(model.getOldPrice());
                } else {
                    pv.setOldPrice(model.getPrice());
                }
                pv = productVariantService.saveProductVariant(pv);
                String fileNamePreview = (product.getSlug() + "-preview-" + model.getPreviewImage().getOriginalFilename()).replaceAll(" ", "");
                String uploadDirPreview = storeFolder + fileNamePreview;
                String imageUrl = storeUrl + fileNamePreview;
                File destPreview = new File(uploadDirPreview);
                model.getPreviewImage().transferTo(destPreview);
                pv.setImageUrl(imageUrl);
                pv.setImagePath(uploadDirPreview);

                List<ProductImage> productImages = new ArrayList<>();
                if (model.getImages() != null) {
                    for (MultipartFile image : model.getImages()) {
                        String fileName = (product.getSlug() + "-" + toSlug(pv.getName()) + "-" + image.getOriginalFilename()).replaceAll(" ", "");
                        String uploadDir = storeFolder + fileName;
                        String url = storeUrl + fileName;
                        File dest = new File(uploadDir);
                        image.transferTo(dest);
                        ProductImage productImage = new ProductImage();
                        productImage.setProductVariant(pv);
                        productImage.setUrl(url);
                        productImage.setPathUrl(uploadDir);
                        productImages.add(productImage);
                    }
                }
                productImages = productImageService.saveAll(productImages);
                pv.setProductImages(productImages);
                pv = productVariantService.saveProductVariant(pv);
                output.add(new ProductVariantResponse(pv));
            }
            product.setColors(colors);
            productService.update(product);
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setOutput(output);
        } catch (Exception e) {
            logger.error("save product variant", e);
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Lỗi máy chủ");
        }
        return response;
    }

    @PostMapping("save-detail")
    public CommonResponse<?> saveProductDetail(@RequestBody List<ProductDetailRequest> request) {
        CommonResponse<?> response = new CommonResponse<>();
        try {
            for (ProductDetailRequest detail : request) {
                ProductVariant pv = productVariantService.findOne(detail.getProductVariantId());
                for (ProductDetailRequestModel model : detail.getModels()) {
                    ProductDetail productDetail;
                    if (model.getId() != null) {
                        productDetail = productDetailService.findOne(model.getId());
                    } else {
                        productDetail = new ProductDetail();
                        productDetail.setProductVariant(pv);
                    }
                    productDetail.setSize(sizeService.findOne(model.getSizeId()));
                    productDetail.setQuantity(model.getQuantity());
                    productDetailService.create(productDetail);
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
                                           @RequestParam(value = "productVariantId", required = false) Long variantId) {
        CommonResponse<?> response = new CommonResponse<>();
        try {
            Product product = productService.findOne(productId);
            product.setStatus(status);
            ProductVariant productVariant = productVariantService.findOne(variantId);
            if (variantId == null) {
                product.setProductPreview(product.getProductVariants().get(0));
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            } else if (Objects.equals(productVariant.getProduct().getId(), productId)) {
                product.setProductPreview(productVariant);
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            } else {
                response.setStatusCode(Constants.RestApiReturnCode.BAD_REQUEST);
                response.setMessage(Constants.RestApiReturnCode.BAD_REQUEST_TXT);
                response.setError("ProductVariant không thuộc product");
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

    @GetMapping("")
    public CommonResponse<Page<ProductBasicModel>> searchProduct(@RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "categories", required = false) List<String> categories,
                                           @RequestParam(value = "brandNames", required = false) List<String> brandNames,
                                           @RequestParam(value = "sizes", required = false) List<String> sizes,
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
            Page<ProductBasicModel> model = productService.searchProduct(name, categories, brandNames, sizes, colors,genders, sale, pageable);
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
            ProductModel productModel = productService.getProductSellingBySlug(slug);
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
            logger.error("Get product variant", e);
        }
        return response;
    }
}
