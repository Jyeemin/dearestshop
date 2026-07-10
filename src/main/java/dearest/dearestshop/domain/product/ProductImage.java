package dearest.dearestshop.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {

    @Id @GeneratedValue
    @Column(name = "product_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    private int sortOrder;

    @Embedded
    private FileInfo fileInfo;

    //생성 메소드
    public static ProductImage createProductImage(
            ImageType imageType,
            int sortOrder,
            FileInfo fileInfo
    ){
        ProductImage productImage = new ProductImage();
        productImage.imageType = imageType;
        productImage.sortOrder = sortOrder;
        productImage.fileInfo = fileInfo;
        return productImage;
    }

    //편의 메소드
    public void addProduct(Product product){
        this.product = product;
    }
}
