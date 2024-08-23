package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Cart;
import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.model.cart.CartItemDetail;
import kidev.vn.onlineshopping.repository.CartRepo;
import kidev.vn.onlineshopping.service.AuthUserService;
import kidev.vn.onlineshopping.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;

    @Override
    public Cart findOne(Long id) {
        return cartRepo.getCartById(id);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepo.getCartByAuthUserId(userId);
    }

    @Override
    public List<CartItemDetail> getAllCartItemsByUserId(Long userId) {
        List<CartItem> cartItems = cartRepo.getAllCartItemsByUserId(userId);
        return cartItems.stream()
                .map(CartItemDetail::new)
                .collect(Collectors.toList());
    }

    @Override
    public void create(Cart cart) {
        cartRepo.save(cart);
    }

    @Override
    public void update(Cart cart) {
        cartRepo.save(cart);
    }

    @Override
    public void delete(Long id) {
        cartRepo.deleteById(id);
    }
}
