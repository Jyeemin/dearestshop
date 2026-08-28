package dearest.dearestshop.domain.product;

import dearest.dearestshop.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private int salesCount;

    @ElementCollection
    @CollectionTable(name = "product_size", joinColumns = @JoinColumn(name = "product_id"))
    @Enumerated(EnumType.STRING)
    private List<ProductSize> sizes = new ArrayList<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Review> reviews = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    //연관관계 편의 메소드
    public void addProductImage(ProductImage productImage){
        images.add(productImage);
        productImage.addProduct(this);
    }

    public void addProductImages(List<ProductImage> images){
        for (ProductImage image : images) {
            addProductImage(image);
        }
    }

    public void addReview(Review review)
    {
        reviews.add(review);
        review.addProduct(this);
    }

    public void addCategory(Category category){
        this.category = category;
        category.addProduct(this);
    }

    //생성 메소드
    public static Product createProduct(
            String productName,
            String detailDescription,
            int price,
            int stockQuantity,
            int salesCount,
            List<ProductSize> sizes,
            List<ProductImage> images,
            Category category
    ){
        Product product = new Product();
        product.productName = productName;
        product.detailDescription = detailDescription;
        product.price = price;
        product.stockQuantity = stockQuantity;
        product.salesCount = salesCount;
        product.sizes = sizes;

        product.addProductImages(images);
        product.addCategory(category);

        return product;
    }

    //편의 메소드
    public void changeProductName(String productName) {
        this.productName = productName;
    }

    public void changeDetailDescription(String detailDescription) {this.detailDescription = detailDescription;}

    public void changePrice(int price){this.price = price;}

    public void changeStockQuantity(int stockQuantity){this.stockQuantity = stockQuantity;}

    public void changeCategory(Category category) {

        if (this.category != null) {
            this.category.getProducts().remove(this);
        }

        this.category = category;
        category.getProducts().add(this);
    }


}
