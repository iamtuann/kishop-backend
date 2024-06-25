package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "cartItemRepo")
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    CartItem getCartItemById(Long id);
}
