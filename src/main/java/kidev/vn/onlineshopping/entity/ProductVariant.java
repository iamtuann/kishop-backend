package kidev.vn.onlineshopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_variant")
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @EqualsAndHashCode.Exclude
    private Product product;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "productVariant",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "productVariant",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductDetail> productDetails;

    @Column(name = "old_price")
    private Long oldPrice;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_path")
    private String imagePath;

    @ManyToMany
    @JoinTable(
            name = "product_variant_color",
            joinColumns = {@JoinColumn(name = "product_variant_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "color_id", referencedColumnName = "id")})
    private List<Color> colors;

    public List<String> getNameColors() {
        return this.colors.stream().map(Color::getName).collect(Collectors.toList());
    }
}