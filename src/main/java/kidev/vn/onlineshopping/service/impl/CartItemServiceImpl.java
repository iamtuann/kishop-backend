package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.repository.CartItemRepo;
import kidev.vn.onlineshopping.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartItemRepo cartItemRepo;

    @Override
    public CartItem findOne(Long id) {
        return cartItemRepo.getCartItemById(id);
    }

    @Override
    public void create(CartItem cartItem) {
        cartItemRepo.save(cartItem);
    }

    @Override
    public void update(CartItem cartItem) {
        cartItemRepo.save(cartItem);
    }

    @Override
    public void delete(CartItem cartItem) {
        cartItemRepo.delete(cartItem);
    }
}
