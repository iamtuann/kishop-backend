package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.CartItem;

public interface CartItemService {
    CartItem findOne(Long id);

    void create(CartItem cartItem);

    void update(CartItem cartItem);

    void delete(CartItem cartItem);
}
