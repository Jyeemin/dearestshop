package dearest.dearestshop.domain.product;

import dearest.dearestshop.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Product extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Lob
    private String detailDescription;

    private int price;

    private int stockQuantity;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(Long id,
                   String productName,
                   String detailDescription,
                   int price, int stockQuantity,
                   List<ProductImage> images,
                   Category category) {
        this.id = id;
        this.productName = productName;
        this.detailDescription = detailDescription;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.images = images;
        this.category = category;
    }
}
