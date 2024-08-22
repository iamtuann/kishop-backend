package kidev.vn.onlineshopping.controller;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.entity.ProductDetail;
import kidev.vn.onlineshopping.model.CommonResponse;
import kidev.vn.onlineshopping.model.productOrder.ProductOrderModel;
import kidev.vn.onlineshopping.model.productOrder.ProductOrderRequest;
import kidev.vn.onlineshopping.model.productOrder.ProductOrderResponseV1;
import kidev.vn.onlineshopping.service.ProductDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/order")
public class ProductOrderController {
    private static final Logger logger = LoggerFactory.getLogger(ProductOrderController.class);

    @Autowired
    ProductDetailService productDetailService;

    @PostMapping("/products-info")
    public CommonResponse<List<ProductOrderModel>> getProductsInfo(@RequestBody List<ProductOrderRequest> requests) {
        CommonResponse<List<ProductOrderModel>> response = new CommonResponse<>();
        try {
            List<ProductOrderModel> listOrder = new ArrayList<>();
            if (requests.size() > 0) {
                for (ProductOrderRequest request : requests) {
                    ProductDetail pq = productDetailService.findOne(request.getQuantityId());
                    if (pq != null) {
                        listOrder.add(new ProductOrderModel(pq, request.getQuantityOrder()));
                    }
                }
            }
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setOutput(listOrder);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Có lỗi xảy ra");
            logger.error("Get products order info", e);
        }
        return response;
    }

    @PostMapping("/update-quantity")
    public CommonResponse<ProductOrderResponseV1> calculateProductOrderPrice(@RequestBody ProductOrderRequest request) {
        CommonResponse<ProductOrderResponseV1> response = new CommonResponse<>();
        try {
            ProductDetail pq = productDetailService.findOne(request.getQuantityId());
            ProductOrderResponseV1 output = new ProductOrderResponseV1(pq, request.getQuantityOrder());
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setOutput(output);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Có lỗi xảy ra");
            logger.error("calculate Product Order Price", e);
        }
        return response;
    }
}
