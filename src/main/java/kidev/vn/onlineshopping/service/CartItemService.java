package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.model.cart.CartItemRequest;

public interface CartItemService {
    CartItem findOne(Long id);

    CartItem create(CartItemRequest item, Long userId);

    CartItem update(CartItemRequest item, Long userId);

    void delete(CartItem cartItem);

    void deleteById(Long cartItemId);
}
