package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.entity.Order;
import kidev.vn.onlineshopping.entity.OrderItem;
import kidev.vn.onlineshopping.enums.OrderStatus;
import kidev.vn.onlineshopping.enums.PaymentStatus;
import kidev.vn.onlineshopping.model.order.OrderDetail;
import kidev.vn.onlineshopping.model.order.OrderModel;
import kidev.vn.onlineshopping.model.order.OrderShippingInfo;
import kidev.vn.onlineshopping.repository.OrderRepo;
import kidev.vn.onlineshopping.service.AuthUserService;
import kidev.vn.onlineshopping.service.CartItemService;
import kidev.vn.onlineshopping.service.OrderHistoryService;
import kidev.vn.onlineshopping.service.OrderService;
import kidev.vn.onlineshopping.utils.CalculatePrice;
import kidev.vn.onlineshopping.utils.GenerateCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;

    private final AuthUserService authUserService;

    private final CartItemService cartItemService;

    private final OrderHistoryService orderHistoryService;

    @Override
    public Order findOne(Long id) {
        return orderRepo.getOrderById(id);
    }

    @Override
    @Transactional
    public Order create(OrderShippingInfo addressInfo, List<CartItem> cartItems, Long userId) {
        Order order = createOrder(addressInfo, cartItems);
        order.setAuthUser(authUserService.findById(userId));
        cartItemService.deleteAll(cartItems);
        return order;
    }

    @Override
    public Order createWithoutAuth(OrderShippingInfo addressInfo, List<CartItem> cartItems) {
        return createOrder(addressInfo, cartItems);
    }

    @Override
    public List<OrderModel> getOrderModelsByUserId(Long userId) {
        List<Order> orders = orderRepo.getAllByAuthUserIdOrderByOrderDateDesc(userId);
        return orders.stream()
                .map(OrderModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetail getOrderDetailByOrderCodeAndUserId(String orderCode, Long userId) {
        Order order = orderRepo.getOrderByOrderCodeAndAuthUserId(orderCode, userId);
        return new OrderDetail(order);
    }

    private Order createOrder(OrderShippingInfo addressInfo, List<CartItem> cartItems) {
        Order order = new Order();
        order.setReceiverName(addressInfo.getReceiverName());
        order.setPhoneNumber(addressInfo.getPhoneNumber());
        order.setOrderDate(new Date());
        order.setProvince(addressInfo.getProvince());
        order.setDistrict(addressInfo.getDistrict());
        order.setWard(addressInfo.getWard());
        order.setDetailAddress(addressInfo.getDetailAddress());
        order.setNote(addressInfo.getNote());
        order.setPaymentType(addressInfo.getPaymentType());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.NOT_PAID.getValue());

        Order savedOrder = orderRepo.save(order);
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> new OrderItem(cartItem, savedOrder))
                .collect(Collectors.toList());
        savedOrder.setOrderItems(orderItems);
        Long subTotalPrice = CalculatePrice.totalPrice(orderItems);
        savedOrder.setSubTotalPrice(subTotalPrice);
        savedOrder.setShippingFee(CalculatePrice.getShippingFee(subTotalPrice));
        savedOrder.setTotalPrice(subTotalPrice + savedOrder.getShippingFee());
        savedOrder.setOrderCode(GenerateCode.generateOrderCode(savedOrder.getId()));
        order = orderRepo.save(savedOrder);
        orderHistoryService.create(order);
        return order;
    }
}
