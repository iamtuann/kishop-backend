package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.model.cart.CartItemDetail;
import kidev.vn.onlineshopping.model.cart.CartItemRequest;

import java.util.List;

public interface CartItemService {
    CartItem findOne(Long id);

    CartItem create(CartItemRequest item, Long userId);

    CartItem update(CartItemRequest item, Long userId);

    List<CartItemDetail> getDetailsFromCartItemRequest(List<CartItemRequest> cartItemRequests);

    List<CartItem> getCartItemFromRequests(List<CartItemRequest> cartItemRequests);

    void delete(CartItem cartItem);

    void deleteAll(List<CartItem> cartItems);

    void deleteById(Long cartItemId);
}
