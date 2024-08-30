package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Cart;
import kidev.vn.onlineshopping.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "cartRepo")
public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart getCartById(Long id);

    Cart getCartByAuthUserId(Long userId);

    @Query(value = "SELECT ci FROM CartItem ci" +
            " JOIN ci.cart c" +
            " JOIN c.authUser au" +
            " WHERE au.id = :userId")
    List<CartItem> getAllCartItemsByUserId(@Param(value = "userId") Long userId);

    @Query(value = "SELECT SUM(ci.quantity) FROM CartItem ci" +
            " INNER JOIN ci.cart c" +
            " INNER JOIN c.authUser au" +
            " WHERE au.id = :userId")
    Integer countCartItems(Long userId);
}
