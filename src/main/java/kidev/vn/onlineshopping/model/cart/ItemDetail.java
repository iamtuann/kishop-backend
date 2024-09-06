package kidev.vn.onlineshopping.model.cart;

import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetail extends ItemBasic {
    private Long productId;
    private Long variantId;
    private String name;
    private String variantName;
    private String slug;
    private String color;
    private String size;
    private String brand;
    private String thumbnail;
    
    public ItemDetail(ProductDetail pd, Integer quantity) {
        super(pd, quantity);
        Product product = pd.getProductVariant().getProduct();
        this.productId = product.getId();
        this.variantId = pd.getProductVariant().getId();
        this.name = product.getName();
        this.variantName = pd.getProductVariant().getName();
        this.slug = product.getSlug();
        this.color = pd.getProductVariant().getColor().getName();
        this.size = pd.getSize().getName();
        this.brand = product.getBrand().getName();
        this.thumbnail = pd.getProductVariant().getImageUrl();
    }

    public ItemDetail(CartItem cartItem) {
        super(cartItem);
        Product product = cartItem.getProduct();
        this.productId = product.getId();
        this.variantId = cartItem.getProductVariant().getId();
        this.name = product.getName();
        this.variantName = cartItem.getProductVariant().getName();
        this.slug = product.getSlug();
        this.color = cartItem.getProductVariant().getColor().getName();
        this.size = cartItem.getProductDetail().getSize().getName();
        this.brand = product.getBrand().getName();
        this.thumbnail = cartItem.getProductVariant().getImageUrl();
    }
}
