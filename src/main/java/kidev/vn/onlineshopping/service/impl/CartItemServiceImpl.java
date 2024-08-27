package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Cart;
import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.model.cart.CartItemRequest;
import kidev.vn.onlineshopping.repository.CartItemRepo;
import kidev.vn.onlineshopping.service.CartItemService;
import kidev.vn.onlineshopping.service.CartService;
import kidev.vn.onlineshopping.service.ProductDetailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepo cartItemRepo;
    private final ProductDetailService productDetailService;
    private final CartService cartService;

    @Override
    public CartItem findOne(Long id) {
        return cartItemRepo.getCartItemById(id);
    }

    @Override
    public CartItem create(CartItemRequest item, Long userId) {
        if (item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Cart item quantity can not less than 1");
        }
        CartItem cartItem = new CartItem();
        cartItem.setProductDetail(productDetailService.findOne(item.getDetailId()));
        cartItem.setCart(cartService.getCartByUserId(userId));
        cartItem.setQuantity(item.getQuantity());
        cartItem.setCreatedDate(new Date());
        cartItem.setUpdatedDate(new Date());
        return cartItemRepo.save(cartItem);
    }

    @Override
    public CartItem update(CartItemRequest item,  Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        CartItem cartItem = cartItemRepo.getCartItemByCartIdAndProductDetailId(cart.getId(), item.getDetailId());
        if (cartItem == null) {
            throw new IllegalArgumentException("ProductDetail is not in cart");
        }
        if (item.getQuantity() < 0) {
            throw new IllegalArgumentException("Cart item quantity can not less than 0");
        }
        if (item.getDetailId() == 0) {
            this.delete(cartItem);
            return null;
        }
        cartItem.setQuantity(item.getQuantity());
        cartItem.setUpdatedDate(new Date());
        return cartItemRepo.save(cartItem);
    }

    @Override
    public void delete(CartItem cartItem) {
        cartItemRepo.delete(cartItem);
    }

    @Override
    public void deleteById(Long cartItemId) {
        cartItemRepo.deleteById(cartItemId);
    }
}
