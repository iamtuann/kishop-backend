package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Cart;
import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.model.cart.CartItemDetail;

import java.util.List;

public interface CartService {
    Cart findOne(Long id);

    Cart getCartByUserId(Long userId);

    List<CartItemDetail> getCartItemDetailsByUserId(Long userId);

    List<CartItem> getCartItemsByUserId(Long userId);

    Integer countCartItems(Long userId);

    void create(Cart cart);

    void update(Cart cart);

    void delete(Long id);
}
