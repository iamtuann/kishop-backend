package kidev.vn.onlineshopping.entity;

import kidev.vn.onlineshopping.common.Priceable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem implements Priceable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    public void setQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be less than 0");
        } else {
            this.quantity = quantity;
        }
    }

    @Override
    public Long getPrice() {
        return productDetail.getPrice();
    }

    @Override
    public Long getOldPrice() {
        return productDetail.getOldPrice();
    }

    @Override
    public Long getTotalPrice() {
        return this.getPrice() * this.quantity;
    }

    @Override
    public Long getTotalOldPrice() {
        return this.getOldPrice() * this.quantity;
    }

    public ProductVariant getProductVariant() {
        return this.productDetail.getProductVariant();
    }

    public Product getProduct() {
        return this.productDetail.getProductVariant().getProduct();
    }
}
