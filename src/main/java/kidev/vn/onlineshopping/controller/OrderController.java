package kidev.vn.onlineshopping.controller;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.config.security.service.UserDetailsImpl;
import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.entity.Order;
import kidev.vn.onlineshopping.model.CommonResponse;
import kidev.vn.onlineshopping.model.cart.CartItemDetail;
import kidev.vn.onlineshopping.model.order.OrderModel;
import kidev.vn.onlineshopping.model.order.OrderPaymentInfo;
import kidev.vn.onlineshopping.model.order.OrderShippingInfo;
import kidev.vn.onlineshopping.service.CartService;
import kidev.vn.onlineshopping.service.OrderService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
@AllArgsConstructor
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    private final CartService cartService;

    @GetMapping("/payment-info")
    public ResponseEntity<CommonResponse<OrderPaymentInfo>> getOrderInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommonResponse<OrderPaymentInfo> response = new CommonResponse<>();
        try {
            if (userDetails != null) {
                List<CartItemDetail> cartItemDetails = cartService.getCartItemDetailsByUserId(userDetails.getId());
                OrderPaymentInfo paymentInfo = new OrderPaymentInfo(cartItemDetails);
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setMessage("Get payment info success");
                response.setOutput(paymentInfo);
            } else {
                response.setStatusCode(Constants.RestApiReturnCode.UNAUTHORIZED);
                response.setError(Constants.RestApiReturnCode.UNAUTHORIZED_TXT);
                response.setMessage("Unauthorized");
            }
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("System error");
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("getOrderInfo", e);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse<OrderModel>> createOrder(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody OrderShippingInfo addressInfo) {
        CommonResponse<OrderModel> response = new CommonResponse<>();
        try {
            if (userDetails != null) {
                List<CartItem> cartItems = cartService.getCartItemsByUserId(userDetails.getId());
                Order order = orderService.create(addressInfo, cartItems, userDetails.getId());
                OrderModel orderModel = new OrderModel(order);
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setMessage("create order success");
                response.setOutput(orderModel);
            } else {
                response.setStatusCode(Constants.RestApiReturnCode.UNAUTHORIZED);
                response.setError(Constants.RestApiReturnCode.UNAUTHORIZED_TXT);
                response.setMessage("Unauthorized");
            }
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("System error");
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("createOrder", e);
        }
        return ResponseEntity.ok(response);
    }
}
