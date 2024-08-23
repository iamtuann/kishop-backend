package kidev.vn.onlineshopping.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {
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
    @Min(value = 1, message = "Quantity cannot be less than 0")
    private Integer quantity;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    public Long getPrice() {
        return productDetail.getPrice();
    }

    public Long getOldPrice() {
        return productDetail.getOldPrice();
    }

    public Long getTotalPrice() {
        return this.getPrice() * this.quantity;
    }

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
