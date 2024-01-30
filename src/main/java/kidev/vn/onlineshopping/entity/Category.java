package kidev.vn.onlineshopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "slug")
    private String slug;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private Integer type;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = { @JoinColumn(name = "category_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") })
    private List<Product> products;
}
