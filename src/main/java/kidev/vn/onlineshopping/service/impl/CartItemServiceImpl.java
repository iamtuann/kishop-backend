package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Cart;
import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.entity.ProductDetail;
import kidev.vn.onlineshopping.model.cart.CartItemDetail;
import kidev.vn.onlineshopping.model.cart.CartItemRequest;
import kidev.vn.onlineshopping.repository.CartItemRepo;
import kidev.vn.onlineshopping.service.CartItemService;
import kidev.vn.onlineshopping.service.CartService;
import kidev.vn.onlineshopping.service.ProductDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        // create cart item if not exist
        if (cartItem == null) {
            if (item.getQuantity() == null) {
                item.setQuantity(1);
            }
            cartItem = this.create(item, userId);
            return cartItem;
        }
        // delete cart item
        if (item.getQuantity() != null &&  item.getQuantity() <= 0) {
            this.delete(cartItem);
            return null;
        }
        //update cart item
        if (item.getQuantity() == null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem.setQuantity(item.getQuantity());
        }
        cartItem.setUpdatedDate(new Date());
        return cartItemRepo.save(cartItem);
    }

    @Override
    public List<CartItemDetail> getDetailsFromCartItemRequest(List<CartItemRequest> cartItemRequests) {
        if (cartItemRequests == null || cartItemRequests.isEmpty()) {
            throw new IllegalArgumentException("List product cannot be empty");
        }
        List<CartItemDetail> cartItemDetails = new ArrayList<>();
        for (CartItemRequest item : cartItemRequests) {
            if (item.getQuantity() < 0) {
                throw new IllegalArgumentException("Quantity can not less than 1");
            }
            ProductDetail pd = productDetailService.findOne(item.getDetailId());
            if (pd != null) {
                cartItemDetails.add(new CartItemDetail(pd, item.getQuantity()));
            } else {
                throw new IllegalArgumentException("Product Detail is not exist");
            }
        }
        return cartItemDetails;
    }

    @Override
    public List<CartItem> getCartItemFromRequests(List<CartItemRequest> cartItemRequests) {
        List<CartItem> cartItems = new ArrayList<>();
        for (CartItemRequest item : cartItemRequests) {
            ProductDetail pd = productDetailService.findOne(item.getDetailId());
            CartItem cartItem = new CartItem();
            cartItem.setProductDetail(pd);
            cartItem.setQuantity(item.getQuantity());
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    @Override
    public void delete(CartItem cartItem) {
        cartItemRepo.delete(cartItem);
    }

    @Override
    public void deleteAll(List<CartItem> cartItems) {
        cartItemRepo.deleteAll(cartItems);
    }

    @Override
    public void deleteById(Long cartItemId) {
        cartItemRepo.deleteById(cartItemId);
    }
}
